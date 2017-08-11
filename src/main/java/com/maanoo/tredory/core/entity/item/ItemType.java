// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.item;

import static com.maanoo.tredory.core.entity.item.ItemTag.Coin;
import static com.maanoo.tredory.core.entity.item.ItemTag.Crystal;
import static com.maanoo.tredory.core.entity.item.ItemTag.Shield;
import static com.maanoo.tredory.core.entity.item.ItemTag.Unknown;


/**
 * @author MaanooAk
 */
public enum ItemType {

    Nothing(Unknown),

    Copper(Coin), Gold(Coin),

    Shield0(Shield), Shield1(Shield), Shield2(Shield), Shield3(Shield), Shield4(Shield),

    // Crystal of colors red, green, blue and white
    CrystalR(Crystal), CrystalG(Crystal), CrystalB(Crystal), CrystalW(Crystal),

    Stone(ItemTag.Stone),

    Unique(ItemTag.Unique);

    public final ItemTag tag;

    ItemType(ItemTag tag) {
        this.tag = tag;
    }
}
