// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.assets.Assets;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author MaanooAk
 */
public class Map implements IUpdate, IDraw {

    public final Point size;

    public final Point spawn;
    public final ArrayList<TerrainThing> things;
    public final ArrayList<Entity> l;

    public int bigs;
    public final ArrayList<Spot> spots;
    public int spots_index = 0;

    public final ArrayList<Entity> hotspots;

    @SuppressWarnings("LeakingThisInConstructor")
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
        /*
        tmp -= d;
        if(tmp > 0) return ;
        tmp = 1;
        //while(fitSpots()) {
        tmp2 += 1;
        //}
        if(!fitSpots()) {
            System.out.println(tmp2);
            tmp = 2000;
            tmp2 = 0;
            createSpots(0.5f);
        }
        //*/
    }

    @Override
    public void draw(Graphics g) {

        if (Op.debug) {
            g.setColor(Color.red);
            for (Spot i : spots) {
                g.setLineWidth(i.r == 250 ? 1f : 20f);
                g.drawOval(i.x - i.r, i.y - i.r, i.r * 2, i.r * 2);
            }
        }
        for (TerrainThing i : things) {
            g.pushTransform();
            g.translate(i.p.x, i.p.y);
            g.rotate(0, 0, i.angle);

            i.img.draw(-i.w, -i.h, 2);

            g.popTransform();
        }

        g.setColor(new Color(0.1f, 0.1f, 0.1f));
        g.drawRect(0, 0, size.x, size.y);
    }

    public void drawMini(Graphics g, Point p, float radius) {

        for (int ii = 1; ii < 2 + bigs; ii++) {
            Spot i = spots.get(ii);
            if (Points.distance(i, p) > radius) continue;
            g.setColor(Color.lightGray);
            g.fillOval(i.x - i.r / 2, i.y - i.r / 2, i.r, i.r);
        }

        g.setColor(Color.darkGray);
        for (int ii = 2 + bigs; ii < spots_index; ii++) {
            Spot i = spots.get(ii);
            if (Points.distance(i, p) > radius) continue;
            g.setColor(i.color);
            g.fillOval(i.x - i.r / 2, i.y - i.r / 2, i.r, i.r);
        }

        //g.setColor(new Color(0.1f,0.1f,0.1f));
        //g.drawRect(0, 0, size.x, size.y);
    }

}
