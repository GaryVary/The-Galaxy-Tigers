package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import data.scripts.util.MagicIncompatibleHullmods;


public class GT_Automated_Carrier extends BaseHullMod {

	private static float refitboostmult = 0.8f;
	private static float speedboostmult = 1.10f;
	private static float agilityboostmult = 1.25f;
	private static final String AUTOMATED_CARRIER_EFFECT_ID = "galaxytigers_automated_carrier_effect";

	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getFighterRefitTimeMult().modifyMult(id, refitboostmult);

		if (stats.getVariant().getHullMods().contains("expanded_deck_crew")){
			MagicIncompatibleHullmods.removeHullmodWithWarning(stats.getVariant(), "expanded_deck_crew", "galaxytigers_automated_carrier");
		}
	}

	@Override
	public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
		//IMPORTANT :
		//The code here was taken from the Sylphon RnD mod (Drone Carrier hullmod) made by Nia Tahl and Nicke535.
		//Many thanks to them for making this mod!

		//Initiate an iterator
		int i = 0;

		//Going through the fighters of the ship
		while (i < ship.getMutableStats().getNumFighterBays().getModifiedValue() && i < 20) {
			//Ignore empty slots
			if (ship.getVariant().getWing(i) == null) {
				i++;
				continue;
			}

			//This wing has crew requirement; remove it, and add a LPC to the player's inventory
			if (ship.getVariant().getWing(i).getVariant().getHullSpec().getMinCrew() > 0) {
				if (Global.getSector() != null) {
					if (Global.getSector().getPlayerFleet() != null) {
						Global.getSector().getPlayerFleet().getCargo().addFighters(ship.getVariant().getWingId(i), 1);
					}
				}
				ship.getVariant().setWingId(i ,null);
			}

			//Finally, increase our iterator
			i++;
		}
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "not allowing any crew to come onboard";
		if (index == 1) return "automated fighters";
		if (index == 2) return (int)((1f - refitboostmult) * 100f) + "%";
		if (index == 3) return (int)((speedboostmult - 1f) * 100f) + "%";
		if (index == 4) return (int)((agilityboostmult - 1f) * 100f) + "%";
		if (index == 5) return "Expanded Deck Crew";
		return null;
	}

	public static float getSpeedboostmult() {
		return speedboostmult;
	}

	public static float getAgilityboostmult() {
		return agilityboostmult;
	}

	public static String getAutomatedCarrierEffectId() {
		return AUTOMATED_CARRIER_EFFECT_ID;
	}
}
