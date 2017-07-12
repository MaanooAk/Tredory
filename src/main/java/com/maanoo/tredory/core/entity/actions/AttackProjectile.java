// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;

/**
 * @author MaanooAk
 */
public abstract class AttackProjectile extends Attack {

	protected final ProjectileType projectile;
	
	protected float count; // TODO do final
    protected final float speed;
	
	public AttackProjectile(Entity user, float charge_time, float recharge_time, float end_time,
			float cooldown_time, ProjectileType projectile, float speed, float count) {
		super(user, charge_time, recharge_time, end_time, cooldown_time);
		this.projectile = projectile;
		this.speed = speed;
		this.count = count;
	}

}
