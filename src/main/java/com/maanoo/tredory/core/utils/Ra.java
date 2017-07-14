// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

import java.util.Random;


/**
 * @author MaanooAk
 */
public final class Ra {

    private static Random r = new Random();

    // TODO split in deterministic and not

    public static int range(int max) {
        return r.nextInt(max);
    }

    public static int range(int min, int max) {
        return min + r.nextInt(max - min);
    }

    public static float range(float max) {
        return r.nextFloat() * max;
    }

    public static float angle() {
        return r.nextFloat() * 360;
    }

    public static float range(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }

    public static float rangeTriangle(float min, float max) {
        min /= 2;
        max /= 2;
        return range(min, max) + range(min, max);
    }

    public static float rangeNormal(float min, float max) {
        min /= 3;
        max /= 3;
        return range(min, max) + range(min, max) + range(min, max);
    }

    public static boolean bool() {
        return r.nextBoolean();
    }

    public static float sign() {
        return r.nextBoolean() ? -1 : 1;
    }

    public static boolean chance(float value) {
        return r.nextFloat() < value;
    }

    public static int list(int[] array) {
        return array[r.nextInt(array.length)];
    }

    public static <T> T list(T[] array) {
        return array[r.nextInt(array.length)];
    }
}
