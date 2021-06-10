package data.weapons;

import com.fs.starfarer.api.combat.*;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class galaxytigers_Deactite_On_Hit_Effect implements OnHitEffectPlugin {

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, CombatEngineAPI engine) {
        if ((float) Math.random() > 0.50f && !shieldHit && target instanceof ShipAPI) {

            float emp = projectile.getEmpAmount();
            float dam = projectile.getDamageAmount();

            engine.spawnEmpArc(projectile.getSource(), point, target, target,
                    DamageType.ENERGY,
                    dam,
                    emp, // emp
                    100000f, // max range
                    "tachyon_lance_emp_impact",
                    20f, // thickness
                    new Color(25, 100, 155, 255),
                    new Color(255, 255, 255, 255)
            );
        }
    }
}
