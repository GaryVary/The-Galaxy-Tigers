package data.shipsystems.scripts.ai;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.impl.campaign.ids.Personalities;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class galaxytigers_artillery_mode_AI implements ShipSystemAIScript {

    private CombatEngineAPI engine = Global.getCombatEngine();
    private ShipSystemAPI system;
    private ShipAPI ship;
    private List<ShipAPI> ships = engine.getShips();

    private static Map personnalitiesmod = new HashMap();
    static {
        personnalitiesmod.put(Personalities.RECKLESS, -1);
        personnalitiesmod.put(Personalities.AGGRESSIVE, -1);
        personnalitiesmod.put(Personalities.STEADY, 0);
        personnalitiesmod.put(Personalities.CAUTIOUS, 1);
        personnalitiesmod.put(Personalities.TIMID, 2);
    }

    @Override
    public void init(ShipAPI ship, ShipSystemAPI system, ShipwideAIFlags flags, CombatEngineAPI engine) {
        this.ship = ship;
        this.system = system;
    }

    @Override
    public void advance(float amount, Vector2f missileDangerDir, Vector2f collisionDangerDir, ShipAPI target) {
        int numfrig = 0;
        int numdest = 0;
        int numcrui = 0;
        int numcapi = 0;
        int ripdefense = 0;
        int numretreating = 0;
        boolean stationexists = false;

        //System.out.println("advance function");

        boolean usesystem = false;

        for (ShipAPI aship : ships) {
            if (ship.getSystem().getAmmo() != 0) {
                if (ship.getOwner() == 0) {
                    if (aship.isAlive() && (aship.getOwner() != ship.getOwner() && !aship.isAlly()) && MathUtils.getDistance(ship, aship) < (2200 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId())) && aship.getHullSize() != ShipAPI.HullSize.FIGHTER) {
                        if (aship.isFrigate()) numfrig++;
                        if (aship.isDestroyer()) numdest++;
                        if (aship.isCruiser()) numcrui++;
                        if (aship.isCapital()) numcapi++;
                        if (aship.isDefenseDisabled()
                                || aship.getDisabledWeapons().size()>=2
                                || aship.getFluxTracker().isOverloadedOrVenting()
                                || aship.getCurrFlux() >= aship.getMaxFlux() * 0.90) ripdefense++;
                        if (aship.isDirectRetreat() || aship.isRetreating()) numretreating++;
                        if (aship.isStation()) stationexists = true;
                    }
                } else {
                    //System.out.println("Got in the else");
                    if (aship.isAlive() && aship.getOwner() == 0 && MathUtils.getDistance(ship, aship) < (2200 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId())) && aship.getHullSize() != ShipAPI.HullSize.FIGHTER) {
                        if (aship.isFrigate()) numfrig++;
                        if (aship.isDestroyer()) numdest++;
                        if (aship.isCruiser()) numcrui++;
                        if (aship.isCapital()) numcapi++;
                        if (aship.isDefenseDisabled()
                                || aship.getDisabledWeapons().size()>=2
                                || aship.getFluxTracker().isOverloadedOrVenting()
                                || aship.getCurrFlux() >= aship.getMaxFlux() * 0.90) ripdefense++;
                        if (aship.isDirectRetreat() || aship.isRetreating()) numretreating++;
                        if (aship.isStation()) stationexists = true;
                    }
                }
            }
        }

        if (numfrig >= 4 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId())
        || numdest >= 3 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId())
        || numcrui >= 2 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId())
        || numcapi >= 1
        || ripdefense >= 2 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId())
        || numretreating >= 3 + (Integer) personnalitiesmod.get(ship.getCaptain().getPersonalityAPI().getId()))
            usesystem = true;
        //if (stationexists) usesystem = true;

        if (usesystem) {
            activateSystem();
        }
        else {
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
