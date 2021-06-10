package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import org.lazywizard.lazylib.MathUtils;
import java.util.Iterator;

public class galaxytigers_bristol_transform_Stats extends BaseShipSystemScript {

    //private CombatEngineAPI engine;
    //private float originalAngle = 90;

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        //System.out.println("apply function");

        /*if (engine == null) {
            engine = Global.getCombatEngine();

        }
        if (engine.isPaused()) {
            return;
        }*/

        ShipAPI ship = (ShipAPI) stats.getEntity();
        /*Iterator<WeaponAPI> mdr = ship.getAllWeapons().iterator();
        while (mdr.hasNext()){
            WeaponAPI w = mdr.next();
            switch (w.getSlot().getId()) {
                case "LEFTWING":
                    originalAngle = w.getCurrAngle();
                    break;
            }
        }*/


        /*if ((stats.getEntity() instanceof ShipAPI)) {
            ship = (ShipAPI) stats.getEntity();
        } else {
            return;
        }*/

        /*stats.getMaxSpeed().modifyPercent(id, -80f * effectLevel);
        stats.getAcceleration().modifyPercent(id, -80f * effectLevel);
        //stats.getDeceleration().modifyPercent(id, -80f * effectLevel);
        stats.getShieldDamageTakenMult().modifyPercent(id,-50f* effectLevel);
        stats.getEnergyWeaponRangeBonus().modifyPercent(id, 50f * effectLevel);
        stats.getEnergyRoFMult().modifyPercent(id, 50f * effectLevel);
        stats.getEnergyWeaponFluxCostMod().modifyPercent(id, -90f * effectLevel);*/

        stats.getAcceleration().modifyFlat(id, 75f);
        stats.getDeceleration().modifyFlat(id,50f);
        stats.getMaxSpeed().modifyFlat(id, 50f);
        stats.getMaxTurnRate().modifyFlat(id, 25f);

        //stats.getEntity().getVelocity().length() > 0.0

        //MathUtils.getDistance(ship, ship.getWing().getSourceShip()) > 100

        if (stats.getEntity().getVelocity().length() > 0.0) {

            //System.out.println("got in the if");

            Iterator<WeaponAPI> iter = ship.getAllWeapons().iterator();
            WeaponAPI weapon;
            float widthS;
            float heightS;
            while (iter.hasNext()) {
                weapon = iter.next();
                switch (weapon.getSlot().getId()) {
                    case "LEFTWING":
                        //System.out.println("switch LEFTWING");
                        widthS = weapon.getSprite().getWidth() / 2;
                        heightS = weapon.getSprite().getHeight() / 2;
                        //System.out.println("LEFTWING current angle : " + weapon.getCurrAngle());
                        weapon.getSprite().setCenter(widthS - (1f * heightS * effectLevel), heightS + (1f * heightS * effectLevel));
                        //weapon.setCurrAngle(originalAngle + 2f * effectLevel);
                        break;
                    case "RIGHTWING":
                        //System.out.println("switch RIGHTWING");
                        widthS = weapon.getSprite().getWidth() / 2;
                        heightS = weapon.getSprite().getHeight() / 2;
                        weapon.getSprite().setCenter(widthS + (1f * heightS * effectLevel), heightS + (1f * heightS * effectLevel));
                        //weapon.setCurrAngle(originalAngle - 2f * effectLevel);
                        break;
                }

            }
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
        /*stats.getEnergyWeaponRangeBonus().unmodify(id);
        stats.getEnergyRoFMult().unmodify(id);
        stats.getEnergyWeaponFluxCostMod().unmodify(id);
        stats.getShieldDamageTakenMult().unmodify(id);
        */
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        return null;
    }

}
