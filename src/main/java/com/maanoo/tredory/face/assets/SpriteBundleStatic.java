// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import org.newdawn.slick.Image;

import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.Sprite;
import com.maanoo.tredory.engine.SpriteSingle;


/**
 * @author MaanooAk
 */
public class SpriteBundleStatic extends SpriteBundle {

    public final Image image;

    public SpriteBundleStatic(Image image) {

        this.image = image;

        this.idle = new Animation(new Sprite[] { new SpriteSingle(image) }, new float[] { 1000 }, true);

    }

    @Override
    public SpriteBundle copy() {
        return new SpriteBundleStatic(image);
    }

}
