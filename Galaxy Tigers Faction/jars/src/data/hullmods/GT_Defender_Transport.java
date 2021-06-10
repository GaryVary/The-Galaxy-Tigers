package data.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.loading.*;

import java.util.*;


public class GT_Defender_Transport extends BaseHullMod {
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		//stats.getVariant().getModuleVariant("HANGAR").setOriginalVariant("galaxytigers_defender_reinforcer_Scout");

	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
	    if (index == 0) return "Defender (GT)";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getHullSpec().getHullId().contains("galaxytigers_reinforcer") &&
								!ship.getVariant().hasHullMod("galaxytigers_picket_transport") &&
                                !ship.getVariant().hasHullMod("galaxytigers_sentry_transport") &&
								!ship.getVariant().hasHullMod("galaxytigers_warden_transport");
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship != null && !ship.getHullSpec().getHullId().contains("galaxytigers_reinforcer")) return "The ship must be a Reinforcer";
		if (ship != null && ship.getVariant().hasHullMod("galaxytigers_picket_transport")) return "A Picket (GT) is currently stocked in the hangar";
        if (ship != null && ship.getVariant().hasHullMod("galaxytigers_sentry_transport")) return "A Sentry (GT) is currently stocked in the hangar";
		if (ship != null && ship.getVariant().hasHullMod("galaxytigers_warden_transport")) return "A Warden (GT) is currently stocked in the hangar";
		return null;
	}
}
