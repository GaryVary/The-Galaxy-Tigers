package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.util.HashMap;
import java.util.Map;

public class GT_Upgraded_Weapon_Processing extends BaseHullMod {

    private static Map cadencebonus = new HashMap();
    static {
        cadencebonus.put(HullSize.FRIGATE, 10f);
        cadencebonus.put(HullSize.DESTROYER, 25f);
        cadencebonus.put(HullSize.CRUISER, 50f);
        cadencebonus.put(HullSize.CAPITAL_SHIP, 75f);
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getEnergyRoFMult().modifyPercent(id, (Float) cadencebonus.get(hullSize));
        stats.getBallisticRoFMult().modifyPercent(id, (Float) cadencebonus.get(hullSize));
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "10%";
        if (index == 1) return "25%";
        if (index == 2) return "50%";
        if (index == 3) return "75%";
        return null;
    }
}
