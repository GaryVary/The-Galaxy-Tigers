package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.loading.WingRole;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class galaxytigers_artillery_mode_Stats extends BaseShipSystemScript {

    private static final String ARTILLERY_MODE_EFFECT_ID = "galaxytigers_artillery_mode_effect";
    //private HashMap<FighterWingAPI, WingRole> wingroles = new HashMap<FighterWingAPI, WingRole>();
    private int ammo = 40;
    private boolean firstuse = false;
    private boolean systemactive = false;

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        ShipAPI ship = (ShipAPI) stats.getEntity();
        //Iterator<FighterWingAPI> fighters = ship.getAllWings().iterator();
        //Iterator<ShipHullSpecAPI.ShipTypeHints> hints = ship.getHullSpec().getHints().iterator();

        /*FighterWingAPI wing;
        while (fighters.hasNext()) {
            wing = fighters.next();
            if (!firstuse) {
                wingroles.put(wing, wing.getRole());
            }
            wing.getSpec().setRole(WingRole.SUPPORT);
        }*/

        //ship.getHullSpec().getHints().remove(ShipHullSpecAPI.ShipTypeHints.CARRIER);

        //ship.getMutableStats().getFighterWingRange().modifyMult(ARTILLERY_MODE_EFFECT_ID, 0f);
        if (!ship.isPullBackFighters()) {
            ship.giveCommand(ShipCommand.PULL_BACK_FIGHTERS, ship, -1);
        }

        stats.getMaxSpeed().modifyMult(ARTILLERY_MODE_EFFECT_ID, 0.5f);
        stats.getAcceleration().modifyMult(ARTILLERY_MODE_EFFECT_ID, 0.5f);
        stats.getMaxTurnRate().modifyMult(ARTILLERY_MODE_EFFECT_ID, 0.75f);
        stats.getTurnAcceleration().modifyMult(ARTILLERY_MODE_EFFECT_ID, 0.75f);

        stats.getBallisticWeaponRangeBonus().modifyPercent(ARTILLERY_MODE_EFFECT_ID, 75f);
        stats.getEnergyWeaponRangeBonus().modifyPercent(ARTILLERY_MODE_EFFECT_ID, 75f);

        stats.getBeamPDWeaponRangeBonus().modifyPercent(ARTILLERY_MODE_EFFECT_ID, -40f);
        stats.getNonBeamPDWeaponRangeBonus().modifyPercent(ARTILLERY_MODE_EFFECT_ID, -40f);

        Iterator<WeaponAPI> iter = ship.getAllWeapons().iterator();
        WeaponAPI weapon;

        while (iter.hasNext()) {
            weapon = iter.next();
            if (weapon.getSlot().getId().contains("BLINKER")) {
                //System.out.println(weapon.getSlot().getId() + " was replaced");
                weapon.getAnimation().setAlphaMult(1f);
                weapon.getAnimation().setFrame(0);
                weapon.getAnimation().pause();
            }
            if (weapon.getSlot().getId().contains("MISSILELAUNCHER")) {
                if (!firstuse) {
                    weapon.setAmmo(40);
                    firstuse = true;
                }
                else if (!systemactive) {
                    weapon.setAmmo(ammo);
                    systemactive = true;
                }
            }
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        ShipAPI ship = (ShipAPI) stats.getEntity();

        Iterator<WeaponAPI> iter = ship.getAllWeapons().iterator();
        WeaponAPI weapon;

        while (iter.hasNext()) {
            weapon = iter.next();
            if (weapon.getSlot().getId().contains("BLINKER")) {
                //System.out.println(weapon.getSlot().getId() + " was replaced");
                weapon.getAnimation().pause();
                weapon.getAnimation().setFrame(1);
                weapon.getAnimation().setAlphaMult(0f);
            }
            if (weapon.getSlot().getId().contains("MISSILELAUNCHER")) {
                ammo = weapon.getAmmo();
                weapon.setAmmo(0);
                systemactive = false;
            }
        }
        //ship.getMutableStats().getFighterWingRange().unmodify(ARTILLERY_MODE_EFFECT_ID);

        //ship.getHullSpec().getHints().add(ShipHullSpecAPI.ShipTypeHints.CARRIER);

        stats.getMaxSpeed().unmodify(ARTILLERY_MODE_EFFECT_ID);
        stats.getAcceleration().unmodify(ARTILLERY_MODE_EFFECT_ID);
        stats.getMaxTurnRate().unmodify(ARTILLERY_MODE_EFFECT_ID);
        stats.getTurnAcceleration().unmodify(ARTILLERY_MODE_EFFECT_ID);

        stats.getBallisticWeaponRangeBonus().unmodify(ARTILLERY_MODE_EFFECT_ID);
        stats.getEnergyWeaponRangeBonus().unmodify(ARTILLERY_MODE_EFFECT_ID);

        stats.getNonBeamPDWeaponRangeBonus().unmodify(ARTILLERY_MODE_EFFECT_ID);
        stats.getBeamPDWeaponRangeBonus().unmodify(ARTILLERY_MODE_EFFECT_ID);
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        if (index == 0) {
            return new StatusData("Max speed halved", true);
        }
        if (index == 1) {
            return new StatusData("Agility reduced", true);
        }
        if (index == 2) {
            return new StatusData("Pulling back fighters", true);
        }
        if (index == 3) {
            return new StatusData("Weapon range boosted", false);
        }
        if (index == 4) {
            return new StatusData("Built-in weapon active", false);
        }
        return null;
    }

}
