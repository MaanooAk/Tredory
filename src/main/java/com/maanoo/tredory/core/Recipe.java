// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import java.util.ArrayList;

import com.maanoo.tredory.core.entity.entities.ItemType;


/**
 * @author MaanooAk
 */
public final class Recipe {

    public static final class Bundle {

        public int coins;
        public ArrayList<ItemType> items;

        public Bundle(ItemType... items) {
            this(0, items);
        }

        public Bundle(int coins, ItemType... items) {
            this.coins = coins;
            this.items = new ArrayList<>(items.length);
            for (final ItemType i : items)
                this.items.add(i);
        }
    }

    public final Bundle in, out;

    public Recipe(Bundle in, Bundle out) {
        this.in = in;
        this.out = out;
    }

}
