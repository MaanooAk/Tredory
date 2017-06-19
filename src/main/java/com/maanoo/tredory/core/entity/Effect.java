// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.utils.Ma;

/**
 * @author MaanooAk
 */
public final class Effect {

    public static final Effect empty = new Effect();

    public static final class Item {
        public float add = 0;
        public float mul = 0;
        public float min;
        public float max;

        public Item() {
            min = Float.MIN_VALUE;
            max = Float.MAX_VALUE;
        }

        public Item(float min) {
            this.min = min;
            max = Float.MAX_VALUE;
        }

        public Item(float min, float max) {
            this.min = min;
            this.max = max;
        }

        public float apply(float v) {
            return Ma.limit((v + add) * (1 + mul), min, max);
        }
    }

    public final Item shields = new Item(0);
    public final Item crystals = new Item(0);
    public final Item speed = new Item(0);
    public final Item attackspeed = new Item(0);
    public final Item projcount = new Item(1);
    public final Item projangle = new Item(0, 360);
    public final Item projspeed = new Item();

    public final Item[] all = new Item[]{
            shields, speed, attackspeed, projcount, projangle, projspeed
    };

    public final Effect combine(Effect e) {
        Effect ne = new Effect();

        for (int i = 0; i < all.length; i++) {
            ne.all[i].add = all[i].add + e.all[i].add;
            ne.all[i].mul = all[i].mul + e.all[i].mul;
            ne.all[i].min = Ma.max(all[i].min, e.all[i].min);
            ne.all[i].max = Ma.min(all[i].max, e.all[i].max);
        }

        return ne;
    }
}
