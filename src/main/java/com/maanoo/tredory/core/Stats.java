// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import org.newdawn.slick.Image;

import java.util.ArrayList;

/**
 * @author MaanooAk
 */
public class Stats {

    public static int kills;
    public static int maps;

    public static ArrayList<Image> killed;

    public static void reset() {
        kills = 0;
        maps = 0;

        killed = new ArrayList<>();
    }

    public static void addKilled(Entity ent) {
        kills += 1;
        killed.add(ent.sprites.getStaticImage());
    }

    public static void exitMap(Map m) {
        maps += 1;
    }
}
