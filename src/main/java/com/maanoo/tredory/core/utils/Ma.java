// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

/**
 * @author MaanooAk
 */
public final class Ma {

    // TODO replace with TAU
    public static final float PI = (float) Math.PI;

    public static float abs(float x) {
        return x < 0 ? -x : x;
    }

    public static int looparound(int x, int a) {
        return ((x % a) + a) % a;
    }

    public static int looparound(int x, int a, int b) {
        final int len = b - a;
        return ((x - a) % len + len) % len + a;
    }

    public static int limit(int x, int a, int b) {
        return x < a ? a : x > b ? b : x;
    }

    public static float limit(float x, float a, float b) {
        return x < a ? a : x > b ? b : x;
    }

    public static float limit(float x, float b) {
        return x < -b ? -b : x > b ? b : x;
    }

    public static float min(float a, float b) {
        return a < b ? a : b;
    }

    public static float max(float a, float b) {
        return a > b ? a : b;
    }

    public static float zeroer(float a, float b) {
        return abs(a) < abs(b) ? a : b;
    }

    public static final float pow2(float x) {
        return x * x;
    }

    public static final float pow3(float x) {
        return x * x * x;
    }

    public static final float pow4(float x) {
        return pow2(x * x);
    }

    public static final int round(float x) {
        return (int) (x + 0.5f);
    }

    public static final float anglesub(float a, float b) {
        return zeroer(zeroer(a - b, a - 360 - b), a + 360 - b);
    }
}
