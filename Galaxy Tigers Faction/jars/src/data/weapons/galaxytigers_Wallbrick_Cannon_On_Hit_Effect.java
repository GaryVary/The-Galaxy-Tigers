package data.weapons;

import com.fs.starfarer.api.combat.CombatEngineAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.DamagingProjectileAPI;
import com.fs.starfarer.api.combat.OnHitEffectPlugin;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lwjgl.util.vector.Vector2f;

public class galaxytigers_Wallbrick_Cannon_On_Hit_Effect implements OnHitEffectPlugin {

    @Override
     public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {
        if (!shieldHit) {
            target.getVelocity().set((projectile.getVelocity().x)/9, (projectile.getVelocity().y)/9);
        }
    }
}
