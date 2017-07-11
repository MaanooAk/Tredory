// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.entities.Projectile;
import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.SpriteBundleEntity;

/**
 * @author MaanooAk
 */
public class AttackProjectileArc extends AttackProjectile {

	private final float angle;
	
	public AttackProjectileArc(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time,
			ProjectileType projectile, float speed, float count, float angle) {
		super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count);
		this.angle = angle;
	}

	@Override
	public void perform() {
		
		final Effect e = user.getEffect();
		
		final float attackspeed = end_time * 0.75f; // TODO ??
        final float projcount = e.projcount.apply(this.count);
        float projangle = e.projangle.apply(this.angle);
        final float projspeed = e.projspeed.apply(this.speed);

        if (projcount == 1) {

            Point start = user.location.clone().add(new Point(user.angle).mul(32));

            Core.addEntity(Pools.obtain(Projectile.class)
                    .init(user.team, start, user.angle, new SpriteBundleEntity(projectile.getSpriteSheet()),
                    projspeed, 0, attackspeed, 1500));

        } else {

            if (projangle == 360) projangle -= projangle / projcount;

            float toxo = projangle;
            for (int i = 0; i < projcount; i += 1) {

                float iangle = user.angle + (i / (projcount - 1)) * toxo - toxo / 2;
                Point start = user.location.clone().add(new Point(iangle).mul(32));

                Core.addEntity(Pools.obtain(Projectile.class)
                        .init(user.team, start, iangle, new SpriteBundleEntity(projectile.getSpriteSheet()),
                        projspeed, 0, attackspeed, 1500));

            }
        }

	}

}
