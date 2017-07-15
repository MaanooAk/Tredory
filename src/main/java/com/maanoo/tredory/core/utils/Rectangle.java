// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

import com.maanoo.tredory.core.memory.Poolable;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Rectangle implements Poolable {

    private float x, y, w, h;
    private float cx, cy, ex, ey;

    public Rectangle() {
    }

    public Rectangle init(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        cx = x + w / 2;
        cy = y + h / 2;
        ex = x + w;
        ey = y + h;

        return this;
    }

    /**
     * Checks if a point is inside the rectangle.
     */
    public boolean inside(Point p) {
        return x <= p.x && p.x <= ex && y <= p.y && p.y <= ey;
    }

    /**
     * Checks if a point is inside the rectangle expanded by a given size.
     */
    public boolean inside(Point p, float size) {
        return x - size <= p.x && p.x <= ex + size && y - size <= p.y && p.y <= ey + size;
    }

    // object overrides

    @Override
    public int hashCode() {
        return Float.hashCode(x) ^ Float.hashCode(y) ^ Float.hashCode(w) ^ Float.hashCode(h);
    }

    @Override
    public boolean equals(Object o) {
        Rectangle other = (Rectangle) o;
        return x == other.x && y == other.y && w == other.w && h == other.h;
    }

    @Override
    public String toString() {
        return "Rectangle{x: " + x + ", y: " + y + ", w: " + w + ", h: " + h + '}';
    }

}
