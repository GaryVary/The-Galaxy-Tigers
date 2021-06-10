package data.shipsystems.scripts;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipCommand;
import com.fs.starfarer.api.combat.WeaponAPI;
import com.fs.starfarer.api.impl.combat.BaseShipSystemScript;

import java.util.Iterator;

public class galaxytigers_ethrom_hammering_Stats extends BaseShipSystemScript {

    //private float actualAngle = 0;
    private ShipCommand cm = ShipCommand.TURN_LEFT;

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {

        //System.out.println("that's the apply function");

        ShipAPI ship = (ShipAPI) stats.getEntity();

        /*Iterator<WeaponAPI> mdr = ship.getAllWeapons().iterator();
        while (mdr.hasNext()){
            WeaponAPI w = mdr.next();
            switch (w.getSlot().getId()) {
                case "HAMMER":
                    actualAngle = w.getCurrAngle();
                    break;
            }
        }*/

        /*Iterator<WeaponAPI> iter = ship.getAllWeapons().iterator();
        WeaponAPI weapon;
        while (iter.hasNext()) {
            System.out.println("that's the while");
            weapon = iter.next();
            System.out.println(weapon.getSlot().getId());
            /*switch (weapon.getSlot().getId()) {
                case "HAMMER":
                    System.out.println("that's the HAMMER case");
                    weapon.setCurrAngle(weapon.getCurrAngle() + 2f * effectLevel);
                    break;
            }
        }*/

        stats.getMaxTurnRate().modifyFlat(id, 120f);
        stats.getTurnAcceleration().modifyFlat(id, 120f);
        stats.getAcceleration().modifyFlat(id, 0f);
        stats.getMaxSpeed().modifyFlat(id, 0f);

        ship.giveCommand(ShipCommand.TURN_LEFT, ship, -1);
        ship.blockCommandForOneFrame(ShipCommand.TURN_RIGHT);
        ship.blockCommandForOneFrame(ShipCommand.ACCELERATE);
        ship.blockCommandForOneFrame(ShipCommand.ACCELERATE_BACKWARDS);
        ship.blockCommandForOneFrame(ShipCommand.TOGGLE_SHIELD_OR_PHASE_CLOAK);
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        stats.getMaxTurnRate().unmodify(id);
    }

    @Override
    public StatusData getStatusData(int index, State state, float effectLevel) {
        return null;
    }

}
