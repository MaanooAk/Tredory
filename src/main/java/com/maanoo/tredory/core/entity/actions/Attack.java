// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Entity;


/**
 * @author MaanooAk
 */
public abstract class Attack extends Action {

    protected boolean affected_by_effects;

    public Attack(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time) {
        super(user, charge_time, recharge_time, end_time, cooldown_time);

        affected_by_effects = true;
    }

    @Override
    public float getStateTime(State state) {

        if (!affected_by_effects) {
            return super.getStateTime(state);
        }

        switch (state) {
        case Charging:
            return charge_time / user.getEffect().attackspeed.apply(1f);
        case Recharging:
            return recharge_time / user.getEffect().attackspeed.apply(1f);
        case Ending:
            return end_time / user.getEffect().attackspeed.apply(1f);
        case Cooling:
            return user.getEffect().cooldown.apply(cooldown_time);
        default:
            return 0;
        }
    }

    @Override
    public void recharge() {}

    @Override
    public void end() {}

    @Override
    public void cooldown() {}

}
