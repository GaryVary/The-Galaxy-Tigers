package data.shipsystems.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;
import com.fs.starfarer.api.mission.FleetSide;
import data.shipsystems.scripts.plugins.galaxytigers_drone_deploy_Plugin;

import java.util.Iterator;
import java.util.List;

public class galaxytigers_drone_deploy_Script extends BaseShipSystemScript {

    CombatEngineAPI engine = Global.getCombatEngine();
    private CombatFleetManagerAPI fleetmanager = null;
    private CombatTaskManagerAPI taskmanager = null;

    private BaseEveryFrameCombatPlugin plugin = new galaxytigers_drone_deploy_Plugin();

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        //System.out.println("apply function");
        List<ShipAPI> ships = engine.getShips();

        engine.getFleetManager(FleetSide.PLAYER).setSuppressDeploymentMessages(true);

        for (ShipAPI ship : ships) {
            //System.out.println("found a ship : " + ship.getHullSpec().getHullId());
            if (ship.getParentStation() == stats.getEntity()) {
                int side = ship.getOwner();
                FleetMemberAPI member = null;
                switch (ship.getHullSpec().getHullId()) {
                    case "galaxytigers_bastillon_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_bastillon_CloseFight");
                        break;
                    case "galaxytigers_berserker_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_berserker_Polyvalent");
                        break;
                    case "galaxytigers_keeper_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_keeper_Support");
                        break;
                    case "galaxytigers_defender_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_defender_Scout");
                        break;
                    case "galaxytigers_picket_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_picket_Offensive");
                        break;
                    case "galaxytigers_sentry_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_sentry_Strike");
                        break;
                    case "galaxytigers_warden_reinforcer":
                        member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_warden_Battle");
                        break;
                }
                member.getRepairTracker().setCR(0.7f);
                member.getRepairTracker().setMothballed(false);
                member.getRepairTracker().setCrashMothballed(false);
                member.getCrewComposition().setCrew(member.getMinCrew());
                member.setShipName(member.getCaptain().getFaction().pickRandomShipName());
                ShipAPI newship = engine.getFleetManager(side).spawnFleetMember(member, ship.getLocation(), ship.getFacing(), 1f);
                newship.setCollisionClass(CollisionClass.FIGHTER);
                newship.getLocation().set(ship.getLocation());
                newship.setOriginalOwner(side);
                newship.setOwner(side);
                newship.setHitpoints(ship.getHitpoints());

                if (ship.getParentStation().isAlive() && ship.isAlive()) {
                    //System.out.println("coucou");
                    CombatFleetManagerAPI.AssignmentInfo assignment = null;
                    if (side == 1) {
                        fleetmanager = engine.getFleetManager(FleetSide.ENEMY);
                        taskmanager = fleetmanager.getTaskManager(false);
                        assignment = taskmanager.createAssignment(CombatAssignmentType.LIGHT_ESCORT, fleetmanager.getDeployedFleetMember(ship.getParentStation()), false);
                        taskmanager.giveAssignment(
                                fleetmanager.getDeployedFleetMember(newship),
                                assignment,
                                false);
                    } else {
                        fleetmanager = engine.getFleetManager(FleetSide.PLAYER);
                        taskmanager = fleetmanager.getTaskManager(false);
                        assignment = taskmanager.createAssignment(CombatAssignmentType.LIGHT_ESCORT, fleetmanager.getDeployedFleetMember(ship.getParentStation()), false);
                        taskmanager.giveAssignment(
                                fleetmanager.getDeployedFleetMember(newship),
                                assignment,
                                false);
                    }
                }

                engine.removeEntity(ship);
                engine.addPlugin(plugin);
            }
        }

        engine.getFleetManager(FleetSide.PLAYER).setSuppressDeploymentMessages(false);
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {

    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        return null;
    }

}
