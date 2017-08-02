// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.effect.Effect;
import com.maanoo.tredory.core.entity.effect.EffectStack.EffectDuration;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class EffectGiver extends Instant {

    private final Effect effect;
    private final float duration;

    private EffectDuration ef = null;

    public EffectGiver(Entity user, Effect effect, float duration) {
        super(user, 0);
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public void perform() {
        if (ef == null || ef.duration <= 0) {
            ef = user.effects.addTemporary(effect, duration);
        } else {
            ef.duration = duration;
        }
    }

}
