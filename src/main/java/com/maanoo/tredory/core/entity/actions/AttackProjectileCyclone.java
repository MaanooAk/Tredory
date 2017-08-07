// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.effect.Effect;
import com.maanoo.tredory.core.entity.entities.Projectile;
import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.assets.SpriteBundleEntityBasic;


/**
 * TODO rename variables
 *
 * @author MaanooAk
 */
public class AttackProjectileCyclone extends AttackProjectile {

    private final float param1;
    private final float param2;
    private final float param3;

    public AttackProjectileCyclone(Entity user, float charge_time, float recharge_time, float end_time,
            float cooldown_time, ProjectileType projectile, float speed, float count, float param1, float param2,
            float param3) {
        super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count);
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    @Override
    public void perform() {

        final float angle = Ra.global.angle();

        final Effect e = user.getEffect();

        final float proj_charge_time = param2;
        final float projcount = e.projcount.apply(this.count);
        final float projspeed = e.projspeed.apply(this.speed);

        if (projcount == 1) {

            final Point start = user.location.clone().add(new Point(angle).mul(32));

            Core.addEntity(Pools.obtain(Projectile.class).init(user.team, start, angle + 90,
                    new SpriteBundleEntityBasic(projectile.getSpriteSheet()), projspeed, 0.6f, proj_charge_time, 500));

        } else {

            float projangle = 360;
            if (projangle == 360) projangle -= projangle / projcount;

            final float toxo = projangle;
            for (int i = 0; i < projcount; i += 1) {

                if (Ra.global.chance(param3)) continue;

                final float iangle = angle + (i / (projcount - 1)) * toxo - toxo / 2;
                final Point start = user.location.clone().add(new Point(iangle).mul(32));

                if (param1 == 0.0f) {

                    Core.addEntity(Pools.obtain(Projectile.class).init(user.team, start, iangle + 90,
                            new SpriteBundleEntityBasic(projectile.getSpriteSheet()), projspeed, 0.6f, proj_charge_time,
                            500));

                } else if (param1 == 1.0f) {

                    final float sign = Ra.global.sign();

                    Core.addEntity(Pools.obtain(Projectile.class).init(user.team, start, iangle + sign * 45,
                            new SpriteBundleEntityBasic(projectile.getSpriteSheet()), projspeed,
                            sign * Ra.global.range(0.1f, 0.2f), proj_charge_time, 20 + Ra.global.range(200)));
                }

            }
        }

    }

}
