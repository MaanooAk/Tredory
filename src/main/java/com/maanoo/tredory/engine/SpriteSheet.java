// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class SpriteSheet {

    public abstract Sprite getSprite(int x, int y);

    public Sprite getSprite(int index) {
        return getSprite(index % getHCount(), index / getHCount());
    }

    public abstract int getHCount();

    public abstract int getVCount();

    public int getCount() {
        return getHCount() * getVCount();
    }

}
