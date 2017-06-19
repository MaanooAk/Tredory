// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.entities.Item;
import com.maanoo.tredory.core.entity.entities.ItemType;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;

/**
 * @author MaanooAk
 */
public class CoreDrops {

    public static void dropCoins(Core c, Entity ent, float angle, float cone, float speedmin, float speedmax, int value) {
        Item item;
        Point p = ent.location.clone();

        while (value % 10 > 0) {
            item = new Item(ItemType.Copper, p.clone().add(new Point(Ra.range(-8, 8), Ra.range(-8, 8))));
            item.push(angle + Ra.range(0, cone), Ra.range(speedmin, speedmax));
            c.ltoadd.add(item);

            value -= 1;
        }
        while (value >= 10) {
            item = new Item(ItemType.Gold, p.clone().add(new Point(Ra.range(-4, 4), Ra.range(-4, 4))));
            item.push(angle + Ra.range(0, cone), Ra.range(speedmin, speedmax));
            c.ltoadd.add(item);

            value -= 10;
        }

    }

    public static void dropItem(Core c, Entity ent, float angle, float cone, float speedmin, float speedmax, ItemType type) {
        Point p = ent.location.clone();

        Item item = new Item(type, p);
        item.push(angle + Ra.range(-cone / 2, cone / 2), Ra.range(speedmin, speedmax));
        c.ltoadd.add(item);
    }
}
