package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.mission.FleetSide;
import data.shipsystems.scripts.plugins.galaxytigers_drone_deploy_Plugin;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class galaxytigers_drone_call_Script extends BaseShipSystemScript {

    CombatEngineAPI engine = Global.getCombatEngine();
    private CombatFleetManagerAPI fleetmanager = null;
    private CombatTaskManagerAPI taskmanager = null;
    private float battlefieldY;

    private static List<String> variants = new ArrayList<>();
    static {
        variants.add("bastillon_Standard");
        variants.add("berserker_Assault");
        variants.add("defender_PD");
        variants.add("picket_Assault");
        variants.add("sentry_FS");
        variants.add("warden_Defense");
        variants.add("rampart_Standard");
    }

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        battlefieldY = engine.getMapHeight();
        Vector2f location = new Vector2f();
        int side = stats.getEntity().getOwner();
        int rnd = new Random().nextInt(variants.size());
        int randomx = new Random().nextInt(1000);
        boolean ispositive = new Random().nextBoolean();

        //System.out.println("apply function");
        List<ShipAPI> ships = engine.getShips();

        engine.getFleetManager(FleetSide.PLAYER).setSuppressDeploymentMessages(true);

        FleetMemberAPI member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, variants.get(rnd));
        member.getRepairTracker().setCR(0.7f);
        member.getRepairTracker().setMothballed(false);
        member.getRepairTracker().setCrashMothballed(false);
        member.getCrewComposition().setCrew(member.getMinCrew());
        ShipAPI newship = null;
        if (side == 0) {
            if (ispositive) location.set(randomx, 0 - (battlefieldY / 2) - 1000);
            else location.set(-randomx, 0 - (battlefieldY / 2) - 1000);
            newship = engine.getFleetManager(side).spawnFleetMember(member, location, 90, 5f);
        }
        else {
            if (ispositive) location.set(randomx, 0 + (battlefieldY / 2) + 1000);
            else location.set(-randomx, 0 + (battlefieldY / 2) + 1000);
            newship = engine.getFleetManager(side).spawnFleetMember(member, location, 270, 5f);
        }

        /*switch (side) {
            case 0:

            case 1:

        }*/

        newship.setCollisionClass(CollisionClass.SHIP);
        newship.setOriginalOwner(side);
        newship.setOwner(side);
        System.out.println("Spawned a " +
                newship.getVariant().getHullVariantId() +
                " at location X : " +
                newship.getLocation().getX() +
                "; Y : " +
                newship.getLocation().getY());

        for (ShipAPI ship : ships) {
            if (ship == stats.getEntity() && ship.isAlive()) {
                CombatFleetManagerAPI.AssignmentInfo assignment = null;
                if (side == 1) {
                    fleetmanager = engine.getFleetManager(FleetSide.ENEMY);
                    taskmanager = fleetmanager.getTaskManager(false);
                    assignment = taskmanager.createAssignment(CombatAssignmentType.HEAVY_ESCORT, fleetmanager.getDeployedFleetMember(ship), false);
                    taskmanager.giveAssignment(
                            fleetmanager.getDeployedFleetMember(newship),
                            assignment,
                            false);
                } else {
                    fleetmanager = engine.getFleetManager(FleetSide.PLAYER);
                    taskmanager = fleetmanager.getTaskManager(false);
                    assignment = taskmanager.createAssignment(CombatAssignmentType.HEAVY_ESCORT, fleetmanager.getDeployedFleetMember(ship), false);
                    taskmanager.giveAssignment(
                            fleetmanager.getDeployedFleetMember(newship),
                            assignment,
                            false);
                }
                switch (newship.getHullSize()) {
                    case FRIGATE:
                        ship.getSystem().setCooldown(10f);
                        break;
                    case DESTROYER:
                        ship.getSystem().setCooldown(20f);
                        break;
                    case CRUISER:
                        ship.getSystem().setCooldown(35f);
                        break;
                    case CAPITAL_SHIP:
                        ship.getSystem().setCooldown(60f);
                        break;
                }
            }
        }

        engine.getFleetManager(FleetSide.PLAYER).setSuppressDeploymentMessages(false);
        engine.addFloatingText(stats.getEntity().getLocation(), "Calling a drone", 40f, Color.RED, stats.getEntity(), 0f, 0f);
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {

    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        return null;
    }

}
