// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Entity;


/**
 * @author MaanooAk
 */
public abstract class Spell extends Attack {

    protected boolean active;

    public Spell(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time) {
        super(user, charge_time, recharge_time, end_time, cooldown_time);
        active = false;
    }

    @Override
    public void perform() {
        active = true;
    }

    @Override
    public void recharge() {}

    @Override
    public void end() {
        active = false;
    }

    @Override
    public void cooldown() {}

}
