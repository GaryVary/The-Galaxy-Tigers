package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class galaxytigers_drone_deploy_AI implements ShipSystemAIScript {

    private CombatEngineAPI engine = Global.getCombatEngine();
    private ShipSystemAPI system;
    private ShipAPI ship;
    private List<ShipAPI> ships = engine.getShips();

    @Override
    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, com.fs.starfarer.api.combat.CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;
    }

    @Override
    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {

        //System.out.println("advance function");

        boolean usesystem = false;

        if ((ship.isRetreating() || ship.isDirectRetreat() || ship.getHitpoints() <= ship.getMaxHitpoints()/6) && ship.getSystem().getAmmo() != 0)
            usesystem = true;

        for (ShipAPI aship : ships) {
            if (ship.getSystem().getAmmo() != 0) {
                if (ship.getOwner() == 0) {
                    if (aship.isAlive() && (aship.getOwner() != ship.getOwner() && !aship.isAlly()) && MathUtils.getDistance(ship, aship) < 700 && aship.getHullSize() != ShipAPI.HullSize.FIGHTER) {
                        usesystem = true;
                    }
                } else {
                    //System.out.println("Got in the else");
                    if (aship.isAlive() && aship.getOwner() == 0 && MathUtils.getDistance(ship, aship) < 700 && aship.getHullSize() != ShipAPI.HullSize.FIGHTER) {
                        usesystem = true;
                    }
                }
            }
        }

        if (usesystem) {
            activateSystem();
        }
    }

    private void deactivateSystem() {
        if (system.isOn()) {
            ship.useSystem();
        }
    }

    private void activateSystem() {
        if (!system.isOn()) {
            //System.out.println("activate system function");
            ship.useSystem();
        }
    }
}
