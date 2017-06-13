package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entities.ItemType;
import java.util.ArrayList;

/**
 *
 * @author Akritas
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
            for(ItemType i : items) this.items.add(i);
        }
    }
    
    
    public final float rarity;
    public final Bundle in, out;

    public Recipe(float rarity, Bundle in, Bundle out) {
        this.rarity = rarity;
        this.in = in;
        this.out = out;
    }
    
}
