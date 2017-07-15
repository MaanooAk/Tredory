// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Color;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteSheetMasked extends SpriteSheet {

    private final SpriteSheet base;
    private final SpriteSheet mask;

    private final Sprite[][] sprites;

    public SpriteSheetMasked(SpriteSheet base, SpriteSheet mask, Color color) {
        this.base = base;
        this.mask = mask;

        final int h_count = base.getHCount();
        final int v_count = base.getHCount();

        sprites = new Sprite[h_count][v_count];
        for (int x = 0; x < h_count; x++) {
            for (int y = 0; y < v_count; y++) {
                sprites[x][y] = new SpriteMasked(base.getSprite(x, y), mask.getSprite(x, y), color);
            }
        }

    }

    @Override
    public Sprite getSprite(int x, int y) {
        return sprites[x][y];
    }

    @Override
    public int getHCount() {
        return base.getHCount();
    }

    @Override
    public int getVCount() {
        return base.getVCount();
    }

}
