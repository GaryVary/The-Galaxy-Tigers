package data.weapons;

import com.fs.starfarer.api.combat.*;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class galaxytigers_Patience_Mine_On_Hit_Effect implements OnHitEffectPlugin {

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, CombatEngineAPI engine) {
        if (target instanceof ShipAPI) {

            float emp = projectile.getEmpAmount();
            float dam = projectile.getDamageAmount();

            engine.spawnEmpArc(projectile.getSource(), point, target, target,
                    DamageType.ENERGY,
                    1,
                    emp, // emp
                    100000f, // max range
                    "tachyon_lance_emp_impact",
                    20f, // thickness
                    new Color(255, 168, 81, 175),
                    new Color(255, 168, 81, 255)
            );
        }
    }
}
