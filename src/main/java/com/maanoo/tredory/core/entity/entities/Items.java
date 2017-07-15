// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import java.util.ArrayList;


/**
 * @author MaanooAk
 */
public final class Items extends ArrayList<Item> {

    public int max;
    public int max_base;

    public Items(int max) {
        this.max = max;
        this.max_base = max;
    }

    public boolean isMax() {
        return size() >= max;
    }
}
