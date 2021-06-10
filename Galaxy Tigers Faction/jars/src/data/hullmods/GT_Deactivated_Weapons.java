package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class GT_Deactivated_Weapons extends BaseHullMod {

    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        stats.getBeamWeaponRangeBonus().modifyMult(id, 0f);
        stats.getMissileWeaponRangeBonus().modifyMult(id, 0f);
        stats.getEnergyWeaponRangeBonus().modifyMult(id, 0f);
        stats.getBallisticWeaponRangeBonus().modifyMult(id, 0f);
        stats.getBeamPDWeaponRangeBonus().modifyMult(id, 0f);
        stats.getNonBeamPDWeaponRangeBonus().modifyMult(id, 0f);
    }

    public String getDescriptionParam(int index, HullSize hullSize) {
        return null;
    }
}
