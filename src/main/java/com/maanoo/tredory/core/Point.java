// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.memory.Poolable;

/**
 * @author MaanooAk
 */
public class Point implements Poolable {

    //public static final Point point = new Point();

    public float x, y;

    public Point() {
        x = y = 0.0f;
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(float angle) {
        this.x = (float) -Math.sin(Math.toRadians(angle));
        this.y = (float) Math.cos(Math.toRadians(angle));
    }

    @Override
    public Point clone() {
        return new Point(x, y);
    }

    // for pooling

    public Point init() {
        x = y = 0.0f;
        return this;
    }

    public Point init(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point init(float angle) {
        this.x = (float) -Math.sin(Math.toRadians(angle));
        this.y = (float) Math.cos(Math.toRadians(angle));
        return this;
    }

    public Point init(Point point) {
        this.x = point.x;
        this.y = point.y;
        return this;
    }

    // simple

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Point set(Point v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Point add(Point v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Point sub(Point v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Point mul(float v) {
        x *= v;
        y *= v;
        return this;
    }

    public Point div(float v) {
        x /= v;
        y /= v;
        return this;
    }

    public Point add(float dx, float dy) {
        x += dx;
        y += dy;
        return this;
    }

    public Point swap(Point v) {
        float ox = x, oy = y;
        x = v.x;
        y = v.y;
        v.x = ox;
        v.y = oy;
        return this;
    }

    // complex

    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float distance(Point p) {
        return (float) Math.sqrt(pow2(x - p.x) + pow2(y - p.y));
    }

    private static float pow2(float x) {
        return x * x;
    }

    public Point norm() {
        float len = this.len();
        if (len != 0) this.div(len);
        return this;
    }

    public float dot(Point v) {
        return x * v.x + y * v.y;
    }

    public Point rotate(float angle) {
        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        float ox = x, oy = y; // old
        x = c * ox - s * oy;
        y = s * ox + c * oy;
        return this;
    }

    public Point rotate(float angle, Point p) {
        this.sub(p);
        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        float ox = x, oy = y; // old
        x = c * ox - s * oy;
        y = s * ox + c * oy;
        this.add(p);
        return this;
    }


    public float angle() {
        return (float) Math.toDegrees(Math.atan2(x, -y)) + 180;
    }

    public Point intify() {
        x = Math.round(x);
        y = Math.round(y);
        return this;
    }

    // realy comlex

    public int dsign(Point v) {
        return y * v.x > x * v.y ? 1 : -1;
    }


}
