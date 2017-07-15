// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Rectangle;
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
    public void draw(Graphics g, int layer) {
        if (layer != 3) return;

        img.draw(p.x - w, p.y - h, 2);
    }

    @Override
    public boolean needDraw(Rectangle view) {

        return view.inside(p, w + h);
    }
}
