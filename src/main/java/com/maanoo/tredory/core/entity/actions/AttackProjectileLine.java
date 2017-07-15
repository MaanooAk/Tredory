// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.entities.Projectile;
import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * TODO doc
 * 
 * @author MaanooAk
 */
public class AttackProjectileLine extends AttackProjectile {

    private final float width;

    public AttackProjectileLine(Entity user, float charge_time, float recharge_time, float end_time,
            float cooldown_time, ProjectileType projectile, float speed, float count, float width) {
        super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count);
        this.width = width;
    }

    @Override
    public void perform() {
        final Effect e = user.getEffect();

        final float proj_charge_time = end_time * 0.75f;
        final float projcount = e.projcount.apply(this.count);
        final float projspeed = e.projspeed.apply(this.speed);

        if (projcount == 1) {

            Point start = user.location.clone().add(new Point(user.angle).mul(32));

            Core.addEntity(Pools.obtain(Projectile.class).init(user.team, start, user.angle,
                    new SpriteBundleEntity(projectile.getSpriteSheet()), projspeed, 0, proj_charge_time, 1500));

        } else {

            for (int i = 0; i < projcount; i += 1) {

                float offset = width * i / (projcount - 1) - width / 2;
                Point start = user.location.clone().add(new Point(user.angle).mul(32))
                        .add(new Point(user.angle + 90).mul(offset));

                Core.addEntity(Pools.obtain(Projectile.class).init(user.team, start, user.angle,
                        new SpriteBundleEntity(projectile.getSpriteSheet()), projspeed, 0, proj_charge_time, 1500));

            }
        }
    }

}
