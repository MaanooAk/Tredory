package com.maanoo.tredory.face;

import com.maanoo.tredory.core.EntityState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 *
 * @author Akritas
 */
public abstract class SpriteBundle {

    public Animation idle, move, attack, die, special;
    
    public Animation getAnimation(EntityState state) {
        switch (state) {
            case Idle: return idle;
            case Move: return move;
            case Attack: return attack;
            case Die: return die;
            case Special: return special;
        }
        return null;
    }

    public Image getStaticImage() {
        return idle.getImage(0);
    }
    
    public abstract SpriteBundle copy();
}
