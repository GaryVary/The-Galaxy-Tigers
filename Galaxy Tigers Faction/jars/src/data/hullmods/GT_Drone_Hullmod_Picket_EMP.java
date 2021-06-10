package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GT_Drone_Hullmod_Picket_EMP extends BaseHullMod {

	private static Map sizetimer = new HashMap();
	static {
		sizetimer.put(HullSize.FRIGATE, 100000f);
		sizetimer.put(HullSize.DESTROYER, 40f);
		sizetimer.put(HullSize.CRUISER, 20f);
		sizetimer.put(HullSize.CAPITAL_SHIP, 10f);
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		stats.getNumFighterBays().setBaseValue(stats.getNumFighterBays().getBaseValue()+1);

		List<String> dawings = stats.getVariant().getWings();

		if (stats.getNumFighterBays().getBaseValue() == 1) {
			if (!stats.getVariant().getWings().isEmpty() && !stats.getVariant().getWings().contains("gt_picket_support_emp_wing")) {
				stats.getVariant().getWings().set(0, "gt_picket_support_emp_wing");
				return;
			}
			else if (stats.getVariant().getWings().isEmpty()) {
				stats.getVariant().getWings().add(0, "gt_picket_support_emp_wing");
				return;
			}
		}
		else {
			if (!stats.getVariant().getWings().isEmpty() && !stats.getVariant().getWings().contains("gt_picket_support_emp_wing")) {
				stats.getVariant().getWings().add((int)stats.getNumFighterBays().getBaseValue()-1,"gt_picket_support_emp_wing");
				//stats.getVariant().getWings().add("gt_defender_PD_wing");
				return;
			}
		}

		for (String awing : dawings) {
			if (awing.contains("gt_picket_support_emp_wing")) {
				stats.getVariant().getWing(dawings.indexOf(awing)).setRefitTime((Float) sizetimer.get(hullSize));
				/*if (stats.getVariant().getHullSize() == HullSize.CAPITAL_SHIP)
					stats.getVariant().getWing(dawings.indexOf(awing)).setNumFighters(2);*/
			}
		}
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
	    if (index == 0) return "integrated Picket (GT) support drone";
		if (index == 1) return "attack a nearby hostile ship";
		if (index == 2) return "stop incoming fighters";
	    if (index == 3) {
            switch(hullSize) {
                case FRIGATE:
                    return "frigate";
                case DESTROYER:
                    return "destroyer";
                case CRUISER:
                    return "cruiser";
                case CAPITAL_SHIP:
                    return "capital ship";
            }
        }
	    if (index == 4) {
	        switch(hullSize) {
                case FRIGATE:
                    return "too small to mount any equipment to rebuild drones";
                case DESTROYER:
                    return "able to mount crude equipments to rebuild drones";
                case CRUISER:
                    return "able to board decent equipments to rebuild drones";
                case CAPITAL_SHIP:
                    return "able to board excellent equipments to rebuild drones";
            }
        }
	    if (index == 5) {
	        switch(hullSize) {
                case FRIGATE:
                    return "nonexistent";
                case DESTROYER:
                    return "crude";
                case CRUISER:
                    return "decent";
                case CAPITAL_SHIP:
                    return "excellent";
            }
        }
	    if (index == 6) {
	        switch(hullSize) {
                case FRIGATE:
                    return "is impossible";
                case DESTROYER:
                    return "will take 40 seconds (with a replacement rate of 100%)";
                case CRUISER:
                    return "will take 20 seconds (with a replacement rate of 100%)";
                case CAPITAL_SHIP:
                    return "will take 10 seconds (with a replacement rate of 100%)";
            }
        }
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && !ship.getVariant().hasHullMod(HullMods.PHASE_FIELD) &&
								!ship.getVariant().hasHullMod(HullMods.CONVERTED_HANGAR) &&
                                !ship.getVariant().hasHullMod("defenderdronepd") &&
                                !ship.getVariant().hasHullMod("galaxytigers_defender_support_strike_hullmod") &&
                                !ship.getVariant().hasHullMod("galaxytigers_sentry_support_hullmod") &&
                                !ship.getVariant().hasHullMod("galaxytigers_sentry_support_escort_hullmod") &&
                                !ship.getVariant().hasHullMod("galaxytigers_warden_support_hullmod") &&
                                !ship.getVariant().hasHullMod("galaxytigers_warden_support_antiarmor_hullmod") &&
                                !ship.getVariant().hasHullMod("galaxytigers_picket_support_escort_hullmod") &&
                                !ship.getVariant().hasHullMod("galaxytigers_picket_support_brawling_hullmod") &&
								!ship.getVariant().hasHullMod("TSC_Converted_Hanger") &&
								ship.getVariant().getWings().size() >= ship.getNumFighterBays();
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
	    if (ship != null) {
            for (String hullmod : ship.getVariant().getNonBuiltInHullmods()) {
                if ((hullmod.contains("galaxytigers_warden_support") ||
                        hullmod.contains("galaxytigers_defender_support") ||
                        hullmod.contains("galaxytigers_picket_support") ||
                        hullmod.contains("galaxytigers_sentry_support") ||
                        hullmod.contains("defenderdronepd")) &&
                        !hullmod.contains("galaxytigers_picket_support_emp_hullmod")
                ) {
                    return "Ship already has a Drone Support hullmod";
                }
            }
        }
		if (ship != null && ship.getVariant().hasHullMod(HullMods.CONVERTED_HANGAR)) return "Not compatible with Converted hangar";
		//if (ship != null && ship.getVariant().hasHullMod("galaxytigers_warden_support_hullmod")) return "Ship has a Warden (GT) support drone";
        //if (ship != null && ship.getVariant().hasHullMod("galaxytigers_sentry_support_hullmod")) return "Ship has a Sentry (GT) support drone";
		if (ship != null && ship.getVariant().hasHullMod("TSC_Converted_Hanger")) return "Not compatible with Dedicated Hangar Bays";
		if (ship != null && ship.getVariant().getWings().size() < ship.getNumFighterBays()) return "You must set your fighters first";
		return "Can not be installed on a phase ship";
	}
}
