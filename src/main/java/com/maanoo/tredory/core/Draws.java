// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.utils.Rectangle;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class Draws {

    private Draws() {
    }

    public static Graphics g;

    public static final Rectangle view = new Rectangle();

    private static final ArrayList<IDraw> draws = new ArrayList<>();

    public static int draws_performed = 0;

    public static void set(Graphics g, float x, float y, float w, float h) {
        Draws.g = g;
        Draws.view.init(x, y, w, h);

        draws_performed = 0;
    }

    public static void push(IDraw drawable) {

        if (drawable.needDraw(Draws.view)) {

            draws.add(drawable);
            drawable.draw(g, -1);
        }
    }

    public static void drawAll() {

        for(int layer = 0; layer < 10; layer++) {
            for (IDraw i : draws) {
                i.draw(g, layer);
                draws_performed += 1;
            }
        }

        draws.clear();
    }
}