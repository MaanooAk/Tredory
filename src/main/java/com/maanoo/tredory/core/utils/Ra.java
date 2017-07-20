// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

import java.util.Random;


/**
 * @author MaanooAk
 */
public final class Ra {

    public static Ra global = new Ra();

    private Random r;

    /**
     * Used only for the global instance
     */
    private Ra() {
        r = new Random();
    }

    public Ra(long seed) {
        r = new Random(seed);
    }

    public int range(int max) {
        return r.nextInt(max);
    }

    public int range(int min, int max) {
        return min + r.nextInt(max - min);
    }

    public float range(float max) {
        return r.nextFloat() * max;
    }

    public float unit() {
        return r.nextFloat();
    }

    public float angle() {
        return r.nextFloat() * 360;
    }

    public float range(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }

    public float rangeTriangle(float min, float max) {
        min /= 2;
        max /= 2;
        return range(min, max) + range(min, max);
    }

    public float rangeNormal(float min, float max) {
        min /= 3;
        max /= 3;
        return range(min, max) + range(min, max) + range(min, max);
    }

    public boolean bool() {
        return r.nextBoolean();
    }

    public float sign() {
        return r.nextBoolean() ? -1 : 1;
    }

    public boolean chance(float value) {
        return r.nextFloat() < value;
    }

    public int list(int[] array) {
        return array[r.nextInt(array.length)];
    }

    public <T> T list(T[] array) {
        return array[r.nextInt(array.length)];
    }

    public long seed() {
        return r.nextLong();
    }
}
