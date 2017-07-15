// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

import com.maanoo.tredory.core.memory.Poolable;


/**
 * @author MaanooAk
 */
public class Point implements Cloneable, Poolable {

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
        final float ox = x, oy = y;
        x = v.x;
        y = v.y;
        v.x = ox;
        v.y = oy;
        return this;
    }

    public Point addAngled(float angle, float len) {
        x += len * (float) -Math.sin(Math.toRadians(angle));
        y += len * (float) Math.cos(Math.toRadians(angle));
        return this;
    }

    // complex

    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float distance(Point p) {
        return (float) Math.sqrt(pow2(x - p.x) + pow2(y - p.y));
    }

    public float distanceAngle(Point p) {
        final float x = this.x - p.x;
        final float y = this.y - p.y;
        return (float) Math.toDegrees(Math.atan2(x, -y)) + 180;
    }

    // TODO move inside
    private static float pow2(float x) {
        return x * x;
    }

    public Point norm() {
        final float len = this.len();
        if (len != 0) this.div(len);
        return this;
    }

    public float dot(Point v) {
        return x * v.x + y * v.y;
    }

    public Point rotate(float angle) {
        final float c = (float) Math.cos(Math.toRadians(angle));
        final float s = (float) Math.sin(Math.toRadians(angle));
        final float ox = x, oy = y; // old
        x = c * ox - s * oy;
        y = s * ox + c * oy;
        return this;
    }

    public Point rotate(float angle, Point p) {
        this.sub(p);
        final float c = (float) Math.cos(Math.toRadians(angle));
        final float s = (float) Math.sin(Math.toRadians(angle));
        final float ox = x, oy = y; // old
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

    // object overrides

    @Override
    public Point clone() {
        return new Point(x, y);
    }

    @Override
    public int hashCode() {
        return Float.hashCode(x) ^ Float.hashCode(y);
    }

    @Override
    public boolean equals(Object o) {
        final Point other = (Point) o;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "Point{x: " + x + ", y: " + y + '}';
    }
}
