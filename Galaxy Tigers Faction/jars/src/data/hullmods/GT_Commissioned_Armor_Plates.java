package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.util.Misc;

import java.util.HashMap;
import java.util.Map;

public class GT_Commissioned_Armor_Plates extends BaseHullMod {

    private static Map mag = new HashMap();
    static {
        mag.put(HullSize.FRIGATE, 1.05f);
        mag.put(HullSize.DESTROYER, 1.05f);
        mag.put(HullSize.CRUISER, 1.04f);
        mag.put(HullSize.CAPITAL_SHIP, 1.03f);
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getArmorBonus().modifyMult(id, (Float) mag.get(hullSize));
        //stats.getArmorBonus().modifyPercent(id, (Float) mag.get(hullSize));
    }

    @Override //All you need is this to be honest. The framework will do everything on its own.
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship.getVariant().hasHullMod("CHM_commission")) {
            ship.getVariant().removeMod("CHM_commission");
        }
        // This is to remove the unnecessary dummy hull mod. Unless the player want it... but nah!
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        if (index == 0) return "5%";
        if (index == 1) return "5%";
        if (index == 2) return "4%";
        if (index == 3) return "3%";
        return null;
    }
}
