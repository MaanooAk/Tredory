// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.ui;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Interface extends Control {

    protected int w;
    protected int h;

    public Interface() {
        super();
        // start at 1 in order to avoid 0 division
        this.w = 1;
        this.h = 1;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

}
