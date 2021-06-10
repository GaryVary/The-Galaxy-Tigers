package data.scripts.plugins;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.mission.FleetSide;
import data.hullmods.GT_Automated;
import data.hullmods.GT_Automated_Carrier;
import data.hullmods.GT_Automated_Machinery;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.combat.AIUtils;
import org.lazywizard.lazylib.opengl.*;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.*;
import java.util.List;


public class galaxytigers_combatPlugin extends BaseEveryFrameCombatPlugin {

    private static final String OPTIMIZED_AI_EFFECT_ID = "galaxytigers_optimized_ai_effect";
    private float sanctummoduleanglenormal = 90f;
    private float sanctummoduleangleopposite = 270f;
    private CombatEngineAPI engine = null;
    private CombatFleetManagerAPI fleetmanager = null;
    private CombatTaskManagerAPI taskmanager = null;
    private Random randomint = new Random();
    private boolean musicokay = false;
    private CombatFleetManagerAPI combatfleet = null;
    //private List<CombatEntityAPI> teleportedMIRVs = new ArrayList<>();
    private HashMap<CombatEntityAPI, ShipAPI> teleportedMIRVs = new HashMap<CombatEntityAPI, ShipAPI>();

    /*private HashMap<ShipAPI, Integer> shipswdroneescort = new HashMap<ShipAPI, Integer>();
    private static Map sizetimer = new HashMap();
    static {
        sizetimer.put(HullSize.FRIGATE, -1);
        sizetimer.put(HullSize.DESTROYER, 1200);
        sizetimer.put(HullSize.CRUISER, 600);
        sizetimer.put(HullSize.CAPITAL_SHIP, 600);
    }*/

    @Override
    public void advance (float amount, List<InputEventAPI> events) {
        this.engine = Global.getCombatEngine();
        List<ShipAPI> ships = engine.getShips();
        List<DamagingProjectileAPI> projectiles = engine.getProjectiles();
        int sanctummoduleincrement = 1;

        if (engine == null) {
            return;
        }
        else if (engine.isPaused()) {
            if (engine.isInCampaign()) {
                // Checking if GT is in the opposing side :
                combatfleet = engine.getFleetManager(FleetSide.ENEMY);
                List<FleetMemberAPI> opposingreserves = combatfleet.getReservesCopy();
                List<FleetMemberAPI> opposingdeployed = combatfleet.getDeployedCopy();
                for (FleetMemberAPI oppmember : opposingreserves) {
                    if (!Global.getSoundPlayer().getCurrentMusicId().contains("bonnot_tonight_we_riot") && oppmember.getCaptain().getFaction().getId().contains("galaxytigers") && oppmember.getCaptain().getFaction().getRelationshipLevel(Factions.PLAYER).isAtBest(RepLevel.HOSTILE) && !musicokay) {
                        //System.out.println("Music ID : " + Global.getSoundPlayer().getCurrentMusicId());
                        Global.getSoundPlayer().playCustomMusic(0, 0, "music_combat_galaxytigers", true);
                        musicokay = true;
                    }
                }
                for (FleetMemberAPI oppmember : opposingdeployed) {
                    if (!Global.getSoundPlayer().getCurrentMusicId().contains("bonnot_tonight_we_riot") && oppmember.getCaptain().getFaction().getId().contains("galaxytigers") && oppmember.getCaptain().getFaction().getRelationshipLevel(Factions.PLAYER).isAtBest(RepLevel.HOSTILE) && !musicokay) {
                        //System.out.println("Music ID : " + Global.getSoundPlayer().getCurrentMusicId());
                        Global.getSoundPlayer().playCustomMusic(0, 0, "music_combat_galaxytigers", true);
                        musicokay = true;
                    }
                }
            }
            return;
        }

        /*if (engine.isPaused() || engine == null) {
            // Checking if GT is in the opposing side :
            combatfleet = engine.getFleetManager(FleetSide.ENEMY);
            List<FleetMemberAPI> opposingreserves = combatfleet.getReservesCopy();
            for (FleetMemberAPI oppmember : opposingreserves) {
                if (!Global.getSoundPlayer().getCurrentMusicId().contains("huatau_tonight_we_riot") && oppmember.getCaptain().getFaction().getId().contains("galaxytigers") && !musicokay) {
                    //System.out.println("Music ID : " + Global.getSoundPlayer().getCurrentMusicId());
                    Global.getSoundPlayer().playCustomMusic(0, 0, "music_combat_galaxytigers", true);
                    musicokay = true;
                }
            }
            return;
        }*/

        List<DamagingProjectileAPI> gtMIRVs = new ArrayList<>();
        for (DamagingProjectileAPI proj : projectiles) {
            if (proj != null) {
                if (proj.getProjectileSpecId() != null) {
                    //Stuff to add every GT MIRVs missiles to a list
                    if (proj.getProjectileSpecId().contains("galaxytigers_tiger_mirv")) {
                        gtMIRVs.add(proj);
                        System.out.println("projectile added to the gtMIRVs list");
                    /*if (MathUtils.getDistance(proj, proj.getDamageTarget()) < 500f) {
                        //proj.getProjectileSpecId().contains("galaxytigers_type_1_mirv") &&
                        System.out.println("Galaxy Tigers MIRV teleported");
                        engine.spawnProjectile(proj.getSource(), proj.getWeapon(), proj.getWeapon().getId(), MathUtils.getPoint(proj.getLocation(), 300f, proj.getFacing()), proj.getFacing(), proj.getVelocity());
                    }*/
                    }
                    if (proj.getProjectileSpecId().contains("galaxytigers_patience_minelayer_proj")) {
                        proj.getVelocity().set(0f, 0f);
                        /*if (MathUtils.isWithinRange(proj, AIUtils.getNearestEnemy(proj), 150f)) {
                            engine.spawnEmpArc(proj.getSource(), proj.getLocation(), AIUtils.getNearestEnemy(proj), AIUtils.getNearestEnemy(proj),
                                    DamageType.ENERGY,
                                    0,
                                    100, // emp
                                    150f, // max range
                                    "tachyon_lance_emp_impact",
                                    5f, // thickness
                                    new Color(255, 168, 81, 175),
                                    new Color(255, 168, 81, 255)
                            );
                            Global.getSoundPlayer().playSound("mine_explosion", 1f, 1f, proj.getLocation(), proj.getVelocity());
                            engine.spawnExplosion(proj.getLocation(), proj.getVelocity(), new Color(255, 168, 81, 175), 100f, 1f);
                            engine.removeEntity(proj);
                        }*/
                    }
                }
            }
        }

        List<ShipAPI> automatedShips = new ArrayList<>();
        List<Integer> sidesWithBoosters = new ArrayList<>();
        for (ShipAPI ship : ships) {
            if (ship.getVariant().hasHullMod("galaxytigers_optimized_ai_processing")) {
                //System.out.println("detected an optimized ai processing for fleetside " + ship.getOwner());
                sidesWithBoosters.add(ship.getOwner());
            }

            if (ship.getVariant().hasHullMod("automated") || ship.getVariant().hasHullMod("galaxytigers_automated")) {
                //System.out.println("found an automated ship for fleetside : " + ship.getOwner());
                //System.out.println("found an automated ship : " + ship.getHullSpec().getHullId());
                automatedShips.add(ship);
            }

            //If the ship is one from GT mod and is a module, make it "accelerate" if the parent ship is accelerating
            if (ship.getHullSpec().getHullId().contains("galaxytigers") && ship.getParentStation() != null){
                if (ship.getParentStation().getEngineController().isAccelerating()) {
                    ship.giveCommand(ShipCommand.ACCELERATE, ship, -1);
                }
            }

            //The stuff to make the shield modules of the Sanctum spinning (will need to rework this to make a universal version)
            if (ship.getHullSpec().getHullId().contains("galaxytigers_sanctum") && ship.isCapital()) {
                sanctummoduleanglenormal += 0.05f;
                sanctummoduleangleopposite += 0.05f;
                Iterator<ShipAPI> iter = ship.getChildModulesCopy().iterator();
                ShipAPI shipbis;
                while (iter.hasNext()) {
                    shipbis = iter.next();
                    //shipbis.setFacing(ship.getFacing() + 0.006f);
                    switch (sanctummoduleincrement) {
                        case 1:
                            shipbis.setFacing(sanctummoduleanglenormal);
                            sanctummoduleincrement++;
                            break;
                        case 2:
                            shipbis.setFacing(sanctummoduleangleopposite);
                            break;
                    }

                }
            }

            //Here, checking the fighters that are mounted on a carrier with the Automated Carrier hullmod or Automated Machinery hullmod
            //If it's one, then apply the bonuses described in the hullmod
            if (ship.isFighter()) {
                if (ship.getWing() != null) {
                    if (ship.getWing().getSourceShip() != null) {
                        if (ship.getWing().getSourceShip().getVariant().hasHullMod("galaxytigers_automated_carrier")) {
                            ship.getMutableStats().getMaxSpeed().modifyMult(GT_Automated_Carrier.getAutomatedCarrierEffectId(), GT_Automated_Carrier.getSpeedboostmult());
                            ship.getMutableStats().getAcceleration().modifyMult(GT_Automated_Carrier.getAutomatedCarrierEffectId(), GT_Automated_Carrier.getSpeedboostmult());
                            ship.getMutableStats().getDeceleration().modifyMult(GT_Automated_Carrier.getAutomatedCarrierEffectId(), GT_Automated_Carrier.getSpeedboostmult());
                            ship.getMutableStats().getMaxTurnRate().modifyMult(GT_Automated_Carrier.getAutomatedCarrierEffectId(), GT_Automated_Carrier.getAgilityboostmult());
                            ship.getMutableStats().getTurnAcceleration().modifyMult(GT_Automated_Carrier.getAutomatedCarrierEffectId(), GT_Automated_Carrier.getAgilityboostmult());
                        }
                        if (ship.getWing().getSourceShip().getVariant().hasHullMod("galaxytigers_automated_machinery")) {
                            ship.getMutableStats().getMaxSpeed().modifyMult(GT_Automated_Machinery.getAutomatedMachineryEffectId(), GT_Automated_Machinery.getSpeedboostmult());
                            ship.getMutableStats().getAcceleration().modifyMult(GT_Automated_Machinery.getAutomatedMachineryEffectId(), GT_Automated_Machinery.getSpeedboostmult());
                            ship.getMutableStats().getDeceleration().modifyMult(GT_Automated_Machinery.getAutomatedMachineryEffectId(), GT_Automated_Machinery.getSpeedboostmult());
                            ship.getMutableStats().getMaxTurnRate().modifyMult(GT_Automated_Machinery.getAutomatedMachineryEffectId(), GT_Automated_Machinery.getAgilityboostmult());
                            ship.getMutableStats().getTurnAcceleration().modifyMult(GT_Automated_Machinery.getAutomatedMachineryEffectId(), GT_Automated_Machinery.getAgilityboostmult());
                        }
                    }
                }
            }

            //Commented for now. Stuff related to make the drones from the Drone Support hullmods spawn as if they're entering
            //combat instead of just being wings. First tested with the Defender Drone Escort
            /*if (ship.getVariant().hasHullMod("defenderdronepd")) {
                int side = ship.getOwner();
                FleetMemberAPI member = null;
                if (!shipswdroneescort.containsKey(ship)) {

                    shipswdroneescort.put(ship, (Integer) sizetimer.get(ship.getHullSize()));
                    member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_defender_system_PD");
                    member.getRepairTracker().setCR(0.7f);
                    member.getRepairTracker().setMothballed(false);
                    member.getRepairTracker().setCrashMothballed(false);
                    member.getCrewComposition().setCrew(member.getMinCrew());

                    ShipAPI newship = engine.getFleetManager(side).spawnFleetMember(member, MathUtils.getPoint(ship.getLocation(), 10000f, 270f), 90f, 3f);

                    newship.setCollisionClass(CollisionClass.FIGHTER);
                    newship.getLocation().set(ship.getLocation());
                    newship.setOriginalOwner(side);
                    newship.setOwner(side);
                    CombatFleetManagerAPI.AssignmentInfo assignment = null;
                    if (side == 1) {
                        fleetmanager = engine.getFleetManager(FleetSide.ENEMY);
                        taskmanager = fleetmanager.getTaskManager(false);
                        assignment = taskmanager.createAssignment(CombatAssignmentType.LIGHT_ESCORT, fleetmanager.getDeployedFleetMember(ship), false);
                        taskmanager.giveAssignment(
                                fleetmanager.getDeployedFleetMember(newship),
                                assignment,
                                false);
                    }
                    else {
                        fleetmanager = engine.getFleetManager(FleetSide.PLAYER);
                        taskmanager = fleetmanager.getTaskManager(false);
                        assignment = taskmanager.createAssignment(CombatAssignmentType.LIGHT_ESCORT, fleetmanager.getDeployedFleetMember(ship), false);
                        taskmanager.giveAssignment(
                                fleetmanager.getDeployedFleetMember(newship),
                                assignment,
                                false);
                    }
                }
            }*/

            //Checking every GT MIRV missiles
            for (DamagingProjectileAPI proj : gtMIRVs) {
                //Part to manage if Tiger MIRVs missed their target after teleporting
                if (teleportedMIRVs.containsKey(proj)) {
                    if (teleportedMIRVs.get(proj).isAlive() && !MathUtils.isWithinRange(proj.getLocation(), teleportedMIRVs.get(proj).getLocation(), teleportedMIRVs.get(proj).getCollisionRadius() * 3)) {
                        CombatEntityAPI newproj = engine.spawnProjectile(proj.getSource(), proj.getWeapon(), proj.getWeapon().getId(), MathUtils.getPoint(ship.getLocation(), 1000000f, proj.getFacing()), proj.getFacing(), proj.getVelocity());
                        newproj.getVelocity().set(0f, 0f);

                        Global.getSoundPlayer().playSound("mining_blaster_fire", 1f, 1f, proj.getLocation(), newproj.getVelocity());

                        engine.spawnExplosion(proj.getLocation(), newproj.getVelocity(), new Color(255, 168, 81, 175), 100f, 1f);
                        engine.spawnEmpArc(proj.getSource(), proj.getLocation(), proj, teleportedMIRVs.get(proj), DamageType.ENERGY, 10f, 100f, 100000f, "hit_shield_beam_loop", 1f, new Color(255, 168, 81, 175), new Color(255, 168, 81, 255));
                        engine.removeEntity(proj);
                        engine.removeEntity(newproj);
                    }
                }

                //Is the missile close to an enemy ship, is that ship anything else than a fighter, is the ship a module and has the missile not been teleported yet?
                if (ship.isAlive() && ship.getOwner() != proj.getOwner() && ship.getParentStation() != null && MathUtils.getDistance(proj, ship.getParentStation()) <= 500f && !ship.isFighter() && !teleportedMIRVs.containsKey(proj)) {
                    if (MathUtils.getDistance(proj, ship) <= 175f) {
                        //System.out.println("Hey, that's a module here");
                        CombatEntityAPI newproj = engine.spawnProjectile(proj.getSource(), proj.getWeapon(), proj.getWeapon().getId(), MathUtils.getPoint(ship.getLocation(), 1000000f, proj.getFacing()), proj.getFacing(), proj.getVelocity());
                        newproj.getVelocity().set(0f, 0f);

                        Global.getSoundPlayer().playSound("mine_ping", 1.5f, 1f, proj.getLocation(), newproj.getVelocity());

                        engine.spawnExplosion(proj.getLocation(), newproj.getVelocity(), new Color(255, 168, 81, 175), 100f, 1f);

                        proj.getLocation().set(MathUtils.getPoint(ship.getParentStation().getLocation(), ship.getParentStation().getCollisionRadius(), proj.getFacing()));

                        engine.spawnExplosion(proj.getLocation(), newproj.getVelocity(), new Color(255, 168, 81, 175), 100f, 1f);

                        engine.removeEntity(newproj);
                        //engine.removeEntity(proj);
                        teleportedMIRVs.put(proj, ship.getParentStation());
                    }
                }
                //Else is the missile close to a foe that is not a fighter and has the missile not been teleported yet?
                else if (ship.isAlive() && ship.getOwner() != proj.getOwner() && MathUtils.getDistance(proj, ship) <= 175f && !ship.isFighter() && !teleportedMIRVs.containsKey(proj)) {
                    //System.out.println("Galaxy Tigers MIRV teleported");
                    //System.out.println("Hey, that's a normal ship here");

                    CombatEntityAPI newproj = engine.spawnProjectile(proj.getSource(), proj.getWeapon(), proj.getWeapon().getId(), MathUtils.getPoint(ship.getLocation(), 1000000f, proj.getFacing()), proj.getFacing(), proj.getVelocity());
                    newproj.getVelocity().set(0f, 0f);

                    Global.getSoundPlayer().playSound("mine_ping", 1.5f, 1f, proj.getLocation(), newproj.getVelocity());
                    engine.spawnExplosion(proj.getLocation(), newproj.getVelocity(), new Color(255, 168, 81, 175), 100f, 1f);
                    //engine.addSmoothParticle(proj.getLocation(), projvelo, 100, 0.5f, 1, new Color(255, 168, 81, 175));

                    proj.getLocation().set(MathUtils.getPoint(ship.getLocation(), ship.getCollisionRadius(), proj.getFacing()));

                    engine.spawnExplosion(proj.getLocation(), newproj.getVelocity(), new Color(255, 168, 81, 175), 100f, 1f);
                    //engine.addSmoothParticle(proj.getLocation(), projvelo, 100, 0.5f, 1, new Color(255, 168, 81, 175));

                    engine.removeEntity(newproj);
                    teleportedMIRVs.put(proj, ship);
                }
            }

            /*if (ship.getParentStation() != null) {
                if (ship.getParentStation().getVariant().hasHullMod("galaxytigers_picket_transport") && !ship.getHullSpec().getHullId().contains("galaxytigers_picket_reinforcer") && ship.getHullSize() == HullSize.FRIGATE) {
                    FleetMemberAPI member = null;
                    member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, "galaxytigers_picket_reinforcer_Offensive");
                    member.getRepairTracker().setCR(0.7f);
                    member.getRepairTracker().setMothballed(false);
                    member.getRepairTracker().setCrashMothballed(false);
                    member.getCrewComposition().setCrew(member.getMinCrew());

                    ShipAPI newship = engine.getFleetManager(ship.getOwner()).spawnFleetMember(member, ship.getLocation(), ship.getFacing(), 0f);
                    newship.setCollisionClass(CollisionClass.FIGHTER);
                    newship.getLocation().set(ship.getLocation());
                    newship.setOriginalOwner(ship.getOwner());
                    newship.setOwner(ship.getOwner());

                    newship.setParentStation(ship.getParentStation());

                    engine.removeEntity(ship);
                }
            }*/

            //Forcing the True Baguette to change its target if it's a fighter
            while (ship.getHullSpec().getHullId() == "galaxytigers_true_baguette" && ship.getShipTarget().isFighter()) {
                ship.setShipTarget(ships.get(randomint.nextInt(ships.size()-1)));
            }

            /*if (engine.isInCampaign()) {
                if (!Global.getSoundPlayer().getCurrentMusicId().contains("huatau_tonight_we_riot") && (ship.getCaptain().getFaction().getId().contains("galaxytigers") && ship.getCaptain().getFaction().getRelationshipLevel(Factions.PLAYER).isAtBest(RepLevel.HOSTILE)) && !musicokay) {
                    //System.out.println("Music ID : " + Global.getSoundPlayer().getCurrentMusicId());
                    Global.getSoundPlayer().playCustomMusic(0, 0, "music_combat_galaxytigers", true);
                    musicokay = true;
                }
            }*/
        }
        //Applying bonus effects to ships that has Ethrom(s) on their side
        for (ShipAPI ship : automatedShips) {
            if (sidesWithBoosters.contains(ship.getOwner())) {
                ship.getMutableStats().getZeroFluxSpeedBoost().modifyFlat(OPTIMIZED_AI_EFFECT_ID, 15f);
                ship.getMutableStats().getBallisticWeaponRangeBonus().modifyMult(OPTIMIZED_AI_EFFECT_ID, 1.2f);
                ship.getMutableStats().getEnergyWeaponRangeBonus().modifyMult(OPTIMIZED_AI_EFFECT_ID, 1.2f);
                ship.getMutableStats().getMissileWeaponRangeBonus().modifyMult(OPTIMIZED_AI_EFFECT_ID, 1.2f);
                ship.getMutableStats().getBeamWeaponRangeBonus().modifyMult(OPTIMIZED_AI_EFFECT_ID, 1.2f);
            } else {
                ship.getMutableStats().getZeroFluxSpeedBoost().unmodify(OPTIMIZED_AI_EFFECT_ID);
                ship.getMutableStats().getBallisticWeaponRangeBonus().unmodify(OPTIMIZED_AI_EFFECT_ID);
                ship.getMutableStats().getEnergyWeaponRangeBonus().unmodify(OPTIMIZED_AI_EFFECT_ID);
                ship.getMutableStats().getMissileWeaponRangeBonus().unmodify(OPTIMIZED_AI_EFFECT_ID);
                ship.getMutableStats().getBeamWeaponRangeBonus().unmodify(OPTIMIZED_AI_EFFECT_ID);
            }
        }

        /*for (DamagingProjectileAPI proj : projectiles) {
            if (proj.getProjectileSpecId() == "galaxytigers_deactite_shot") {
                //proj.setAngularVelocity(proj.getAngularVelocity() + 100f);
            }
        }*/
    }
}
