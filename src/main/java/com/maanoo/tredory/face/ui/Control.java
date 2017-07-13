// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.ui;

import org.newdawn.slick.Graphics;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Control {

    public boolean visible;

    public Control() {
        this.visible = true;
    }

    public abstract void draw(Graphics g);

}
