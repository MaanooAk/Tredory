// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

import com.maanoo.tredory.core.memory.Pool;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Points {

    private static final Pool<Point> pool = new Pool<>(Point.class);

    public static Point create() {
        return pool.obtain().init();
    }

    public static Point create(float x, float y) {
        return pool.obtain().init(x, y);
    }

    public static Point create(float angle) {
        return pool.obtain().init(angle);
    }

    public static Point create(Point point) {
        return pool.obtain().init(point);
    }

    public static void dispose(Point point) {
        pool.give(point);
    }

    public static void dispose(Point point1, Point point2) {
        pool.give(point1);
        pool.give(point2);
    }

    public static void dispose(Point point1, Point point2, Point point3) {
        pool.give(point1);
        pool.give(point2);
        pool.give(point3);
    }

}
