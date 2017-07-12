// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import java.util.ArrayList;
import java.util.Collection;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.memory.Pools;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class Entities {

    // TODO add more duplicate storing for better selection
    // TODO move findX and activateX functions here

    private final ArrayList<Entity> l;

    private final ArrayList<Entity> ltoadd;
    private final ArrayList<Entity> ltoremove;

    private final ArrayList<Entity> players;

    // TODO extension point

    public Entities() {

        l = new ArrayList<>();

        ltoadd = new ArrayList<>();
        ltoremove = new ArrayList<>();

        players = new ArrayList<>();
    }

    public ArrayList<Entity> getAll() {
        return l;
    }

    public void addAll(Collection<? extends Entity> c) {
        l.addAll(c);
    }

    public void addPlayer(Entity i) {
        l.add(i);
        players.add(i);
    }

    public void removeDead() {

        for (final Entity i : l) {
            if (i.dead) Pools.give(i);
        }

        l.removeIf(i -> i.dead);

        players.removeIf(i -> i.dead);
        // TODO extension point
    }

    public void add(Entity i) {
        ltoadd.add(i);
    }

    public void remove(Entity i) {
        ltoremove.add(i);
    }

    public void perform() {

        l.addAll(ltoadd);
        ltoadd.clear();

        l.removeAll(ltoremove);
        ltoremove.clear();
    }

    public void clear() {
        l.clear();
        ltoadd.clear();
        ltoremove.clear();

        players.clear();
        // TODO extension point
    }

    public void clearNonPlayers() {
        l.clear();
        ltoadd.clear();
        ltoremove.clear();

        // TODO extension point

        l.addAll(players);
    }

    public int count() {
        return l.size();
    }
}
