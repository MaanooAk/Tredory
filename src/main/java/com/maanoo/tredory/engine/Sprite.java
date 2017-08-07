// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Sprite {

    public abstract Image getImage();

    public void draw(float x, float y, float width, float height) {
        draw(x, y, width, height, Color.white);
    }

    public void draw(float x, float y, int scale) {
        draw(x, y, getImage().getWidth() * scale, getImage().getHeight() * scale);
    }

    public abstract void draw(float x, float y, float width, float height, Color color);

}
