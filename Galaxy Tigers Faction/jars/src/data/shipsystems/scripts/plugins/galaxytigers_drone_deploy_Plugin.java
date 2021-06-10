package data.shipsystems.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.input.InputEventAPI;
import org.lazywizard.lazylib.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Plugin to set the correct collision class for the drones deployed by a Reinforcer
public class galaxytigers_drone_deploy_Plugin extends BaseEveryFrameCombatPlugin {

    private CombatEngineAPI engine = null;

    @Override
    public void advance (float amount, List<InputEventAPI> events) {
        this.engine = Global.getCombatEngine();

        if (engine.isPaused() || engine == null) {
            return;
        }

        List<ShipAPI> ships = engine.getShips();
        List<ShipAPI> deployedrones = new ArrayList<>();
        List<ShipAPI> okaylist = new ArrayList<>();
        List<ShipAPI> reinforcerlist = new ArrayList<>();
        int count = 0;

        for (ShipAPI ship : ships) {
            System.out.println("ship is a " + ship.getHullSpec().getHullId() + " from fleetside : " + ship.getOwner());
            if (ship.getCollisionClass() == CollisionClass.FIGHTER
                    && ship.getVariant().getHullVariantId().contains("galaxytigers")
                    && !ship.getVariant().isFighter()) {
                deployedrones.add(ship);
                System.out.println("added ship to the deployed drone list : " + ship.getHullSpec().getHullId() + " for fleetside : " + ship.getOwner());
            }
            if (ship.getHullSpec().getHullId().contains("reinforcer")) {
                reinforcerlist.add(ship);
                System.out.println("added ship to the reinforcers list : " + ship.getHullSpec().getHullId() + " for fleetside : " + ship.getOwner());
            }
        }

        for (ShipAPI drone : deployedrones) {
            for (ShipAPI ship : reinforcerlist) {
                count += 1;
                if (!MathUtils.isWithinRange(drone, ship, -100)) {
                    okaylist.add(ship);
                }
            }
            if (count == okaylist.size()) {
                System.out.println(drone.getHullSpec().getHullId() + " from fleetside : " + drone.getOwner() + " gets its collision class set to SHIP");
                drone.setCollisionClass(CollisionClass.SHIP);
            }
            count = 0;
            okaylist.clear();
        }

        /*for (ShipAPI drone : deployedrones) {
            for (ShipAPI ship : ships) {
                System.out.println("ship is a " + ship.getHullSpec().getHullId() + " from fleetside : " + ship.getOwner());
                if (ship != drone && deployedrones.contains(ship)) {
                    count += 1;
                    if (MathUtils.getDistance(drone, ship) > 500) {
                        okaylist.add(ship);
                    }
                }
            }
            if (count == okaylist.size()) {
                System.out.println(drone.getHullSpec().getHullId() + " from fleetside : " + drone.getOwner() + " gets its collision class set to SHIP");
                drone.setCollisionClass(CollisionClass.SHIP);
            }
            count = 0;
            okaylist.clear();
        }*/
    }
}
