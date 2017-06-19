// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Str {

    public static String create(String... l) {
        StringBuilder sb = new java.lang.StringBuilder();
        for (String i : l) {
            sb.append(i);
        }
        return sb.toString();
    }

}
