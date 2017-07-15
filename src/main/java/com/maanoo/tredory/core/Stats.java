// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import java.util.ArrayList;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.map.Map;
import com.maanoo.tredory.engine.Sprite;


/**
 * @author MaanooAk
 */
public class Stats {

    public static int kills;
    public static int maps;

    // TODO add life, networth, kills per sec, fame

    public static ArrayList<Sprite> killed;

    public static void reset() {
        kills = 0;
        maps = 0;

        killed = new ArrayList<>();
    }

    public static void addKilled(Entity ent) {
        kills += 1;
        killed.add(ent.sprites.getStaticSprite());
    }

    public static void exitMap(Map m) {
        maps += 1;
    }
}
