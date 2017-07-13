// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Str {

    public static String create(String... l) {
        final StringBuilder sb = new StringBuilder();
        for (final String i : l) {
            sb.append(i);
        }
        return sb.toString();
    }

    public static String create(Object... l) {
        final StringBuilder sb = new StringBuilder();
        for (final Object i : l) {
            sb.append(i.toString());
        }
        return sb.toString();
    }

    public static String repeat(String text, int repeat) {
        final StringBuilder sb = new StringBuilder();
        while (repeat-- > 0) {
            sb.append(text);
        }
        return sb.toString();
    }
}
