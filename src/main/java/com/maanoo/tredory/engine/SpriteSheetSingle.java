// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Image;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteSheetSingle extends SpriteSheet {

    private final Sprite[][] sprites;
    private final int h_count;
    private final int v_count;

    public SpriteSheetSingle(Image image, int tw, int th) {
        this(image, tw, th, 0);
    }

    public SpriteSheetSingle(Image image, int tw, int th, int spacing) {

        final int w = image.getWidth();
        final int h = image.getHeight();

        h_count = ((w - tw) / (tw + spacing)) + 1;
        v_count = ((h - th) / (th + spacing)) + 1 + (((h - th) % (th + spacing) != 0) ? 1 : 0);

        sprites = new Sprite[h_count][v_count];
        for (int x = 0; x < h_count; x++) {
            for (int y = 0; y < v_count; y++) {

                sprites[x][y] = new SpriteSingle(image.getSubImage(x * (tw + spacing), y * (th + spacing), tw, th));
            }
        }
    }

    @Override
    public Sprite getSprite(int x, int y) {
        return sprites[x][y];
    }

    @Deprecated
    public Image getImage(int x, int y) {
        return sprites[x][y].getImage();
    }

    @Override
    public int getHCount() {
        return h_count;
    }

    @Override
    public int getVCount() {
        return v_count;
    }
}
