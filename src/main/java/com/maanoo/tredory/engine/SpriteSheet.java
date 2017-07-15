// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Image;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteSheet {

    private Image[][] images;

    public SpriteSheet(Image image, int tw, int th) {
        this(image, tw, th, 0, 0);
    }

    public SpriteSheet(Image image, int tw, int th, int spacing, int margin) {

        final int tilesAcross = ((image.getWidth() - (margin * 2) - tw) / (tw + spacing)) + 1;
        int tilesDown = ((image.getHeight() - (margin * 2) - th) / (th + spacing)) + 1;
        if ((image.getHeight() - th) % (th + spacing) != 0) {
            tilesDown++;
        }

        images = new Image[tilesAcross][tilesDown];
        for (int x = 0; x < tilesAcross; x++) {
            for (int y = 0; y < tilesDown; y++) {
                images[x][y] = image.getSubImage(x * (tw + spacing) + margin, y * (th + spacing) + margin, tw, th);
            }
        }
    }

    public Image getImage(int x, int y) {

        return images[x][y];
    }

}
