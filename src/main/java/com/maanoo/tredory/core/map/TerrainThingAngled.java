// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.core.utils.Point;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class TerrainThingAngled extends TerrainThing {

    public final float angle;

    public TerrainThingAngled(Point p, float angle, Image img) {
        super(p, img);
        this.angle = angle;
    }

    @Override
    public void draw(Graphics g, int layer) {
        if (layer != 3) return;

        g.pushTransform();
        g.translate(p.x, p.y);
        g.rotate(0, 0, angle);
        img.draw(-w, -h, 2);
        g.popTransform();
    }
}
