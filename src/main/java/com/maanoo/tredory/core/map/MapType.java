// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public enum MapType {

    Plain(70, 1, 1f),

    Altar(20, 2, 1f),

    Overpop(10, 1, 1.7f);

    // TODO store more things here
    // TODO store parts of the map gen ?

    public final int commones;
    public final int bigs;
    public final float population;

    MapType(int commones, int bigs, float population) {
        this.commones = commones;
        this.bigs = bigs;
        this.population = population;
    }
}
