package data.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.EveryFrameWeaponEffectPlugin;
import com.fs.starfarer.api.combat.WeaponAPI;

public class galaxytigers_Blinker_Effect implements EveryFrameWeaponEffectPlugin {

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        weapon.getSprite().setAdditiveBlend();
        if (weapon.getShip() != null && weapon.getShip().isAlive())
        {
            //Ship is alive, keep that blinker going
            weapon.getAnimation().setAlphaMult(1f);
        }
        else
        {
            //Shep ded, blinker ded
            weapon.getAnimation().setAlphaMult(0f);
        }
    }
}
