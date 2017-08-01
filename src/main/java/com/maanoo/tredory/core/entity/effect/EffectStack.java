// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.effect;

import java.util.ArrayList;

import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.entity.Entity;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class EffectStack implements IUpdate {

    public static final class EffectDuration {

        public final Effect effect;
        public float duration;

        public EffectDuration(Effect effect, float duration) {
            super();
            this.effect = effect;
            this.duration = duration;
        }

    }

    private Entity entity;

    private final ArrayList<Effect> permanent;

    private final ArrayList<EffectDuration> temporary;

    private Effect total_effect;

    public EffectStack() {

        permanent = new ArrayList<>();
        temporary = new ArrayList<>();
        total_effect = new Effect();
    }

    public void init(Entity entity) {
        this.entity = entity;

        permanent.clear();
        temporary.clear();
        total_effect.init();
    }

    public void addPermanent(Effect e) {
        permanent.add(e);

        total_effect.add(e);
        entity.updateEffectsEffects();
    }

    public EffectDuration addTemporary(Effect e, float duration) {
        final EffectDuration ef = new EffectDuration(e, duration);
        temporary.add(ef);

        total_effect.add(e);
        entity.updateEffectsEffects();

        return ef;
    }

    public void updateEffects() {
        total_effect.init();
        for (final Effect i : permanent) {
            total_effect.add(i);
        }
        for (final EffectDuration i : temporary) {
            total_effect.add(i.effect);
        }
        entity.updateEffectsEffects();
    }

    public Effect getEffect() {
        return total_effect;
    }

    @Override
    public void update(int d) {

        for (final EffectDuration i : temporary) {
            i.duration -= d;
        }

        if (temporary.removeIf((i) -> i.duration <= 0)) {
            updateEffects();
        }

    }

}
