// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.utils.Ma;

/**
 * TODO doc
 * 
 * @author MaanooAk
 */
public class AttackProjectileBlow extends AttackProjectileArc {

	private final float start_count;
	
	public AttackProjectileBlow(Entity user, float charge_time, float recharge_time, float end_time,
			float cooldown_time, ProjectileType projectile, float speed, float count, float angle) {
		super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count, angle);
		this.start_count = count;
	}

	@Override
	public float getStateTime(State state) {
		switch(state) {
		case Charging:
			return charge_time / user.getEffect().attackspeed.apply(1f);
		case Recharging:
			return Ma.max(recharge_time / user.getEffect().attackspeed.apply(1f) - perform_count * 10, 150);
		case Ending:
			return end_time / user.getEffect().attackspeed.apply(1f);
		case Cooling:
			return user.getEffect().cooldown.apply(cooldown_time);
		default:
			return 0;
		}
	}

	@Override
	public void perform() {
		
		this.count = Ma.limit(start_count + perform_count / 3, 1, 10);
		
		super.perform();
	}

	@Override
	public void end() {
		super.end();
		
		this.count = start_count;
	}
	
	

}
