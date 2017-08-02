// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Entity;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Instant extends Action {

    public Instant(Entity user, float cooldown_time) {
        super(user, 0, 0, 0, cooldown_time, true);
    }

    @Override
    public float getStateTime(State state) {
        switch (state) {
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
