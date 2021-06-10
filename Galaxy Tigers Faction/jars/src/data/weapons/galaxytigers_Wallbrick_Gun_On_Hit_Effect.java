package data.weapons;

import com.fs.starfarer.api.combat.*;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class galaxytigers_Wallbrick_Gun_On_Hit_Effect implements OnHitEffectPlugin {

    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, CombatEngineAPI engine) {
        if (!shieldHit) {
            target.getVelocity().set((projectile.getVelocity().x)/10, (projectile.getVelocity().y)/10);
        }
    }
}
