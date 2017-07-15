// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.Sprite;


/**
 * @author MaanooAk
 */
public abstract class SpriteBundle {

    public Animation idle, move, attack, die, special;

    public Animation getAnimation(EntityState state) {
        switch (state) {
        case Idle:
            return idle;
        case Move:
            return move;
        case Attack:
            return attack;
        case Die:
            return die;
        case Special:
            return special;
        }
        return null;
    }

    public Sprite getStaticSprite() {
        return idle.getSprite(0);
    }

    public abstract SpriteBundle copy();
}
