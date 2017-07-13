// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets.atlas;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Atlas {

    protected final HashMap<String, Image> images;

    protected Image atlas;
    protected int atlasImageCount;

    public Atlas() {
        images = new HashMap<>();

        atlas = null;
        atlasImageCount = 0;
    }

    public void add(String name) {
        try {

            final Image image = Assets.loadImage(name);
            images.put(name, image);

        } catch (final SlickException e) {
            e.printStackTrace();
        }
    }

    public Image getAtlas() throws SlickException {
        if (atlas == null || atlasImageCount != images.size()) {
            generate();
        }
        return atlas;
    }

    public void generate() throws SlickException {

        // TODO improve

        int w = 0;
        int h = 0;

        final int max = AtlasUtils.getMaxTextureSize() / 2;

        int px = 0;
        int py = 0;
        int npy = 0;

        final HashMap<String, int[]> locations = new HashMap<>();

        for (final Entry<String, Image> entry : images.entrySet()) {
            final int iw = entry.getValue().getWidth();
            final int ih = entry.getValue().getHeight();

            if (px + iw > max) {
                px = 0;
                py = npy;
            }

            locations.put(entry.getKey(), new int[] { px, py });

            px += iw;
            if (npy < py + ih) {
                npy = py + ih;
            }

            w = px;
            h = npy;
        }

        if (atlas != null) atlas.destroy();

        atlas = new Image(w, h);

        final Graphics graphics = atlas.getGraphics();
        graphics.setAntiAlias(false);

        for (final Entry<String, int[]> entry : locations.entrySet()) {
            final String name = entry.getKey();
            final Image image = images.get(name);
            final int location[] = entry.getValue();

            final int x = location[0];
            final int y = location[1];
            final int imagew = image.getWidth();
            final int imageh = image.getHeight();

            graphics.drawImage(image, x, y);

            image.destroy();

            images.put(name, atlas.getSubImage(location[0], location[1], imagew, imageh));
        }

        graphics.flush();

    }

    public Image get(String name) {
        return images.get(name);
    }
}
