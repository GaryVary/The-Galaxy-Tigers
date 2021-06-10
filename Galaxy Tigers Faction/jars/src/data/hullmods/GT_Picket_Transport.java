package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;


public class GT_Picket_Transport extends BaseHullMod {
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		/*FleetMemberAPI member = null;
		member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_picket_reinforcer_Offensive");
		member.getRepairTracker().setCR(0.7f);
		member.getRepairTracker().setMothballed(false);
		member.getRepairTracker().setCrashMothballed(false);
		member.getCrewComposition().setCrew(member.getMinCrew());

		stats.getVariant().setModuleVariant("HANGAR", member.getVariant());*/

		//stats.getVariant().getModuleVariant("HANGAR").setOriginalVariant("galaxytigers_picket_reinforcer_Offensive");

	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
	    if (index == 0) return "Picket (GT)";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getHullSpec().getHullId().contains("galaxytigers_reinforcer") &&
								!ship.getVariant().hasHullMod("galaxytigers_defender_transport") &&
                                !ship.getVariant().hasHullMod("galaxytigers_sentry_transport") &&
								!ship.getVariant().hasHullMod("galaxytigers_warden_transport");
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship != null && !ship.getHullSpec().getHullId().contains("galaxytigers_reinforcer")) return "The ship must be a Reinforcer";
		if (ship != null && ship.getVariant().hasHullMod("galaxytigers_defender_transport")) return "A Defender (GT) is currently stocked in the hangar";
        if (ship != null && ship.getVariant().hasHullMod("galaxytigers_sentry_transport")) return "A Sentry (GT) is currently stocked in the hangar";
		if (ship != null && ship.getVariant().hasHullMod("galaxytigers_warden_transport")) return "A Warden (GT) is currently stocked in the hangar";
		return null;
	}
}
