// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteSingle extends Sprite {

    private final Image image;

    public SpriteSingle(Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void draw(float x, float y, float width, float height, Color color) {
        image.draw(x, y, width, height, color);
    }
}
