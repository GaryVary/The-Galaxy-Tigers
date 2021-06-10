package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAIScript;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.ShipwideAIFlags;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

public class galaxytigers_bristol_transform_AI implements ShipSystemAIScript {

    private ShipSystemAPI system;
    private ShipAPI ship;
    private final float grange = 1000;

    @Override
    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, com.fs.starfarer.api.combat.CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;
    }

    @Override
    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {

        //System.out.println("advance function");

        boolean usesystem = false;

        // Stocking current target into a variable
        ShipAPI target2 = target;
        // If wing has no target, try to get mothership's target
        if (target == null) {
            if (ship.getWing() != null && ship.getWing().getSourceShip() != null) {
                target2 = ship.getWing().getSourceShip().getShipTarget();
            }
        }

        // If there's a target, check if it's in range. If it is, use the ship system.
        if (target2 != null) {
            float range = grange;
            float distance = MathUtils.getDistance(ship, target2);
            if (distance < range && MathUtils.getDistance(ship, ship.getWing().getSourceShip()) > 150) {
                usesystem = true;
            }
        }
        else if (MathUtils.getDistance(ship, ship.getWing().getSourceShip()) > 150) {
            usesystem = true;
        }

        if (usesystem) {
            activateSystem();
        } else {
            deactivateSystem();
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
