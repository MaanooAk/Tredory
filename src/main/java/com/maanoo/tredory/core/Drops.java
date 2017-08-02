// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.item.Item;
import com.maanoo.tredory.core.entity.item.ItemType;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;


/**
 * @author MaanooAk
 */
public class Drops {

    // TODO move drops in the map (move loot generation to the map generation)
    // TODO move from ra.global to map.ra

    public static void dropCoins(Core c, Entity ent, float angle, float cone, float speedmin, float speedmax,
            int value) {
        Item item;
        final Point p = ent.location.clone();

        while (value % 10 > 0) {
            item = new Item(ItemType.Copper, p.clone().add(new Point(Ra.global.range(-8, 8), Ra.global.range(-8, 8))));
            item.push(angle + Ra.global.range(0, cone), Ra.global.range(speedmin, speedmax));
            Core.addEntity(item);

            value -= 1;
        }
        while (value >= 10) {
            item = new Item(ItemType.Gold, p.clone().add(new Point(Ra.global.range(-4, 4), Ra.global.range(-4, 4))));
            item.push(angle + Ra.global.range(0, cone), Ra.global.range(speedmin, speedmax));
            Core.addEntity(item);

            value -= 10;
        }

    }

    public static void dropItem(Core c, Entity ent, float angle, float cone, float speedmin, float speedmax,
            ItemType type) {
        final Point p = ent.location.clone();

        final Item item = new Item(type, p);
        item.push(angle + Ra.global.range(-cone / 2, cone / 2), Ra.global.range(speedmin, speedmax));
        Core.addEntity(item);
    }
}
