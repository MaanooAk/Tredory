// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Logger {

    public static void log(String where, String text) {
        System.out.println(System.nanoTime() + " [LOG] " + "[" + where + "] " + text);
    }

}
