// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.item;

import java.util.ArrayList;


/**
 * @author MaanooAk
 */
public final class Items extends ArrayList<Item> {

    // TODO use ArrayList instead of extending it

    /** The max number of items without effects. */
    public final int max_base;

    /** The max number of items with effects. */
    public int max;

    public Items(int max) {
        this.max_base = max;
        this.max = max;
    }

    // TODO add function to apply effects

    public boolean isMax() {
        return size() >= max;
    }

    public int count(ItemType type) {
        int count = 0;
        for (final Item i : this) {
            if (i.type == type) {
                count += 1;
            }
        }
        return count;
    }
}
