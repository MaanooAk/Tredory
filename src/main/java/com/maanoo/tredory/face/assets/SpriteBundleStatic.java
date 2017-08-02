// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.Sprite;


/**
 * @author MaanooAk
 */
public class SpriteBundleStatic extends SpriteBundle {

    public final Sprite sprite;

    public SpriteBundleStatic(Sprite sprite) {

        this.sprite = sprite;

        this.idle = new Animation(new Sprite[] { sprite }, new float[] { 1000 }, true);

    }

    @Override
    public SpriteBundle copy() {
        return new SpriteBundleStatic(sprite);
    }

}
