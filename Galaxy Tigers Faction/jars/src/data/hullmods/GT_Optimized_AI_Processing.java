package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;
import com.fs.starfarer.campaign.fleet.FleetData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GT_Optimized_AI_Processing extends BaseHullMod {

    /*public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

    }*/

    /*public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        List<FleetMemberAPI> lst = ship.getFleetMember().getFleetData().getMembersListCopy();
        for (FleetMemberAPI mem : lst) {
            if (mem.getVariant().hasHullMod("automated")) {
                mem.getStats().getZeroFluxSpeedBoost().modifyFlat(mem.getId(), 600);
                mem.getStats().getAcceleration().modifyFlat(mem.getId(), 600);
            }
        }
    }*/

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "15";
        if (index == 1) return "20%";
        if (index == 2) return "Bonuses can stack when multiple ships with this hullmod are deployed";
        return null;
    }
}
