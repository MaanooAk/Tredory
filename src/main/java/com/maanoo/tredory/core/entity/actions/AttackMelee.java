// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Entity;

/**
 * @author MaanooAk
 */
public class AttackMelee extends Attack {
	
	public AttackMelee(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time,
			float range, float rangeMax) {
		super(user, charge_time, recharge_time, end_time, cooldown_time);
		this.range = range;
		this.rangeMax = rangeMax;
	}

	public final float range;
    public final float rangeMax;
    
	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}


}
