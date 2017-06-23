// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public enum MapType {

    Plain(1, 1f),
    Altar(2, 1f),
    Overpop(1, 2f);

    // TODO store more things here
    // TODO store parts of the map gen ?

    public final int bigs;
    public final float population;

    MapType(int bigs, float population) {
        this.bigs = bigs;
        this.population = population;
    }
}
