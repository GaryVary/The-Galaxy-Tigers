package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import java.util.HashMap;
import java.util.Map;

public class GT_First_Gen extends BaseHullMod {

    private static Map lessarmor = new HashMap();
    static {
        lessarmor.put(HullSize.FRIGATE, 0.75f);
        lessarmor.put(HullSize.DESTROYER, 0.80f);
        lessarmor.put(HullSize.CRUISER, 0.85f);
        lessarmor.put(HullSize.CAPITAL_SHIP, 0.90f);
    }

    private static Map spd = new HashMap();
    static {
        spd.put(HullSize.FRIGATE, 15f);
        spd.put(HullSize.DESTROYER, 12f);
        spd.put(HullSize.CRUISER, 10f);
        spd.put(HullSize.CAPITAL_SHIP, 7f);
    }

    private static Map turn = new HashMap();
    static {
        turn.put(HullSize.FRIGATE, 1.1f);
        turn.put(HullSize.DESTROYER, 1.1f);
        turn.put(HullSize.CRUISER, 1.08f);
        turn.put(HullSize.CAPITAL_SHIP, 1.08f);
    }

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getArmorBonus().modifyMult(id, (Float) lessarmor.get(hullSize));
        stats.getMaxSpeed().modifyFlat(id, (Float) spd.get(hullSize));
        stats.getAcceleration().modifyFlat(id, (Float) spd.get(hullSize));
        stats.getMaxTurnRate().modifyMult(id, (Float) turn.get(hullSize));
        stats.getTurnAcceleration().modifyMult(id, (Float) turn.get(hullSize));
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        switch (hullSize) {
            case FRIGATE:
                if (index == 0) return "-25%";
                if (index == 1) return "15";
                if (index == 2) return "10%";
                break;
            case DESTROYER:
                if (index == 0) return "-20%";
                if (index == 1) return "12";
                if (index == 2) return "10%";
                break;
            case CRUISER:
                if (index == 0) return "-15%";
                if (index == 1) return "10";
                if (index == 2) return "8%";
                break;
            case CAPITAL_SHIP:
                if (index == 0) return "-10%";
                if (index == 1) return "7";
                if (index == 2) return "8%";
                break;
        }
        return null;
    }
}
