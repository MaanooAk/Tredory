// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.Sprite;


/**
 * @author MaanooAk
 */
public class SpriteBundleEntityStatic extends SpriteBundleEntity {

    public final Sprite sprite;

    public SpriteBundleEntityStatic(Sprite sprite) {
        this.sprite = sprite;

        idle = new Animation(new Sprite[] { sprite }, new float[] { 1000 }, true);
    }

    @Override
    public Animation getAnimation(EntityState state) {
        return idle;
    }

    @Override
    public Sprite getStaticSprite() {
        return sprite;
    }

    @Override
    public SpriteBundleEntity copy() {
        return new SpriteBundleEntityStatic(sprite);
    }

}
