// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.entities.HomingProjectile;
import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class AttackProjectileHoming extends AttackProjectile {

    private float angleSpeed;

    public AttackProjectileHoming(Entity user, float charge_time, float recharge_time, float end_time,
            float cooldown_time, ProjectileType projectile, float speed, float count, float angleSpeed) {
        super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count);
        this.angleSpeed = angleSpeed;
    }

    @Override
    public void perform() {
        final Effect e = user.getEffect();

        final float proj_charge_time = end_time * 0.75f;
        final float projcount = e.projcount.apply(this.count);
        final float projspeed = e.projspeed.apply(this.speed);

        if (projcount == 1) {

            final Point start = user.location.clone().add(new Point(user.angle).mul(32));

            Core.addEntity(Pools.obtain(HomingProjectile.class).init(user.team, start, user.angle,
                    new SpriteBundleEntity(projectile.getSpriteSheet()), projspeed, 0, proj_charge_time, 1500,
                    angleSpeed, null));

        } else {

            float projangle = 360;
            projangle -= projangle / projcount;

            final float toxo = projangle;
            for (int i = 0; i < projcount; i += 1) {

                final float iangle = user.angle + (i / (projcount - 1)) * toxo - toxo / 2;
                final Point start = user.location.clone().add(new Point(iangle).mul(32));

                Core.addEntity(Pools.obtain(HomingProjectile.class).init(user.team, start, iangle,
                        new SpriteBundleEntity(projectile.getSpriteSheet()), projspeed, 0, proj_charge_time, 1500,
                        angleSpeed, null));

            }
        }
    }

}
