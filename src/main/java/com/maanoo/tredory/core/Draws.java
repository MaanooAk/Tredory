// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

import com.maanoo.tredory.core.utils.Rectangle;


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
    public static int texture_binds = 0;

    public static void set(Graphics g, float x, float y, float w, float h) {
        Draws.g = g;
        Draws.view.init(x, y, w, h);

        draws_performed = 0;
        texture_binds = 0;
    }

    public static void push(IDraw drawable) {

        if (drawable.needDraw(Draws.view)) {

            draws.add(drawable);
            drawable.draw(g, -1);
        }
    }

    public static void drawAll() {
        Texture lastBind = null;

        for (int layer = 0; layer < 10; layer++) {
            for (final IDraw i : draws) {
                i.draw(g, layer);

                draws_performed += 1;
                final Texture newBind = TextureImpl.getLastBind();
                if (newBind != lastBind) {
                    texture_binds += 1;
                    lastBind = newBind;
                }
            }
        }

        draws.clear();
    }
}
