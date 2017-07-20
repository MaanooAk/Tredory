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

    public int kills;
    public int maps;
    public int coins;

    // TODO add life, networth, kills per sec, fame

    public final ArrayList<Sprite> killed;

    public Stats() {
        kills = 0;
        maps = 0;
        coins = 0;

        killed = new ArrayList<>();
    }

    public void addKilled(Entity ent) {
        kills += 1;
        killed.add(ent.sprites.getStaticSprite());
    }

    public void exitMap(Map m) {
        maps += 1;
    }

    // TODO use
    public void getCoins(int count) {
        coins += count;
    }
}
