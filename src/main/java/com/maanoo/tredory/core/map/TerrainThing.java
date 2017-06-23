// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.utils.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class TerrainThing implements IDraw {

    // TODO remove the finals

    public final Point p;
    public final Image img;
    public final int w;
    public final int h;

    public TerrainThing(Point p, Image img) {
        this.p = p;
        this.img = img;

        w = img.getWidth();
        h = img.getHeight();

        p.intify();
    }

    @Override
    public void draw(Graphics g) {

        img.draw(p.x - w, p.y -h, 2);
    }

}
