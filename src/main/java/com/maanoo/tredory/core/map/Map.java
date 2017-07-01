// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.utils.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author MaanooAk
 */
public class Map implements IUpdate, IDraw {

    // TODO store map type

    public final Point size;

    public MapType type;

    public final Point spawn;
    public final ArrayList<TerrainThing> things;
    public final ArrayList<Entity> l;

    public int bigs; // TODO move into map type
    public final ArrayList<Spot> spots;
    public int spots_index = 0; // TODO no need to store ?

    public final ArrayList<Entity> hotspots;

    public Map(Point size) {
        this.size = size;

        spawn = new Point();
        things = new ArrayList<>();
        l = new ArrayList<>();
        hotspots = new ArrayList<>();
        spots = new ArrayList<>();

    }

    @Override
    public void update(int d) {

        for (Iterator<Entity> it = hotspots.iterator(); it.hasNext(); ) {
            Entity i = it.next();

            if (i.dead) it.remove();
        }

    }

    @Override
    public void draw(Graphics g, int layer) {

        if (layer == -1) {
            for (TerrainThing i : things) {
                i.pushDraw();
            }
        }

        if (layer == 1) {
            g.setColor(Colors.c101010);
            g.drawRect(0, 0, size.x, size.y);
        }

        if (Op.debug && layer == 9) {
            g.setColor(Color.red);
            for (Spot i : spots) {
                g.setLineWidth(i.r == 250 ? 1f : 20f);
                g.drawOval(i.x - i.r, i.y - i.r, i.r * 2, i.r * 2);
            }
        }
    }

    /**
     * Draws the minimap on the corner
     */
    public void drawMini(Graphics g, Point p, float radius) {

        // TODO simlifiy ?

        for (int ii = 1; ii < 2 + bigs; ii++) {
            Spot i = spots.get(ii);
            if (i.distance(p) > radius) continue;
            g.setColor(Color.lightGray);
            g.fillOval(i.x - i.r / 2, i.y - i.r / 2, i.r, i.r);
        }

        g.setColor(Color.darkGray);
        for (int ii = 2 + bigs; ii < spots_index; ii++) {
            Spot i = spots.get(ii);
            if (i.distance(p) > radius) continue;
            g.setColor(i.color);
            g.fillOval(i.x - i.r / 2, i.y - i.r / 2, i.r, i.r);
        }
    }

}
