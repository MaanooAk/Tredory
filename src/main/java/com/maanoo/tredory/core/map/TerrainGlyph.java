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
public class TerrainGlyph extends TerrainThingAngled {


    public TerrainGlyph(Point p, float angle, Image img) {
        super(p, angle, img);
        img.setAlpha(0.05f);
    }

    @Override
    public void draw(Graphics g, int layer) {
        if (layer != 3) return;

        // TODO remove transformations (split to angled and not ?)

        g.pushTransform();
        g.translate(p.x, p.y);
        g.rotate(0, 0, angle);
        img.draw(-w*10, -h*10, 20);
        g.popTransform();
    }

}
