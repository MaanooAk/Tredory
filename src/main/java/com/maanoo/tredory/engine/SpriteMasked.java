// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteMasked extends Sprite {

    private final Sprite base;
    private final Sprite mask;
    private final Color color;

    public SpriteMasked(Sprite base, Sprite mask, Color color) {
        this.base = base;
        this.mask = mask;
        this.color = color;
    }

    @Override
    public Image getImage() {
        return base.getImage();
    }

    @Override
    public void draw(float x, float y, float width, float height) {
        draw(x, y, width, height, color);
    }

    @Override
    public void draw(float x, float y, float width, float height, Color color) {
        base.draw(x, y, width, height);
        mask.draw(x, y, width, height, color);
    }

}
