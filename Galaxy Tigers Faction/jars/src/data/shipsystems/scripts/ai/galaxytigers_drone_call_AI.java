package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class galaxytigers_drone_call_AI implements ShipSystemAIScript {

    private CombatEngineAPI engine = Global.getCombatEngine();
    private ShipSystemAPI system;
    private ShipAPI ship;
    private List<ShipAPI> ships = engine.getShips();

    @Override
    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;
    }

    @Override
    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {

        //System.out.println("advance function");

        ShipAPI enemy = AIUtils.getNearestEnemy(ship);
        boolean usesystem = false;

        if (enemy == null) return;

        if (ship.isRetreating() || ship.isDirectRetreat() || MathUtils.isWithinRange(ship, enemy, 3000))
            usesystem = true;

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
