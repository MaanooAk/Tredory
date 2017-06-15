// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 * @author MaanooAk
 */
public class SpriteBundleStatic extends SpriteBundle {

    public final Image image;

    public SpriteBundleStatic(Image image) {

        this.image = image;

        this.idle = new Animation(new Image[]{image}, 1000);

    }

    @Override
    public SpriteBundle copy() {
        return new SpriteBundleStatic(image);
    }

}
