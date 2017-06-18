// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public enum MapType {

    Plain(1), Altar(2);

    // TODO store more things here
    // TODO store parts of the map gen ?

    public final int bigs;

    MapType(int bigs) {
        this.bigs = bigs;
    }
}