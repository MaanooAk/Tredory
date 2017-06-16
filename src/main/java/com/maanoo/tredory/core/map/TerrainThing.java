// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.core.utils.Point;
import org.newdawn.slick.Image;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class TerrainThing {

    public final Point p;
    public final float angle;
    public final Image img;
    public final int w;
    public final int h;

    public TerrainThing(Point p, float angle, Image img) {
        this.p = p;
        this.angle = angle;
        this.img = img;

        w = img.getWidth();
        h = img.getHeight();

        p.intify();
    }

}
