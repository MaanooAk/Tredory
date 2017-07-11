// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

/**
 * @author MaanooAk
 */
public abstract class Spell extends Attack {

	public Spell(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time) {
		super(user, charge_time, recharge_time, end_time, cooldown_time);
	}

	@Override
	public void end() {
		// nothing
	}

	@Override
	public void cooldown() {
		// nothing
	}

}
