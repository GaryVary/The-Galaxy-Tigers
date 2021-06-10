package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;


public class GT_Sentry_Transport extends BaseHullMod {
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		//stats.getVariant().getModuleVariant("HANGAR").setOriginalVariant("galaxytigers_sentry_reinforcer_Strike");

	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
	    if (index == 0) return "Sentry (GT)";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getHullSpec().getHullId().contains("galaxytigers_reinforcer") &&
								!ship.getVariant().hasHullMod("galaxytigers_defender_transport") &&
                                !ship.getVariant().hasHullMod("galaxytigers_picket_transport") &&
								!ship.getVariant().hasHullMod("galaxytigers_warden_transport");
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship != null && !ship.getHullSpec().getHullId().contains("galaxytigers_reinforcer")) return "The ship must be a Reinforcer";
		if (ship != null && ship.getVariant().hasHullMod("galaxytigers_defender_transport")) return "A Defender (GT) is currently stocked in the hangar";
        if (ship != null && ship.getVariant().hasHullMod("galaxytigers_picket_transport")) return "A Picket (GT) is currently stocked in the hangar";
		if (ship != null && ship.getVariant().hasHullMod("galaxytigers_warden_transport")) return "A Warden (GT) is currently stocked in the hangar";
		return null;
	}
}
