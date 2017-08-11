// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.item;

import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.SpriteBundleEntityStatic;
import com.maanoo.tredory.face.assets.SpriteBundleEntityUnique;


/**
 * @author MaanooAk
 */
public class Item extends Entity implements Comparable<Item> {

    public final ItemType type;
    public final ItemTag tag;

    private int unpicable;

    public Item(ItemType type) {
        this(type, new Point());
    }

    public Item(ItemType type, Point location) {
        this(type, location, null);
    }

    public Item(ItemType type, Point location, String name) {
        super(Team.Neutral, location, 180, getSpriteBundleEntity(type, name));

        this.type = type;
        this.speed = 0;

        tag = type.tag;

        undead = true;
        pickable = true;
        size = 16;
        sizecol = 16;

        unpicable = 0;

        spriteRotate = false;
    }

    private static SpriteBundleEntity getSpriteBundleEntity(ItemType type, String name) {
        if (type.tag == ItemTag.Unique) {
            return new SpriteBundleEntityUnique(Assets.getUniqueItem(name).get());
        } else {
            return new SpriteBundleEntityStatic(Assets.getItem(type).get());
        }
    }

    public void push(float angle, float speed) {

        this.speed = speed;
        this.angle = angle % 360;
    }

    /**
     * Make the item un pickable for a given duration.
     */
    public void unpicablify(int duration) {
        pickable = false;
        unpicable = duration;
    }

    @Override
    public void update(int d) {
        super.update(d);

        if (speed > 0) {
            move.init(angle).mul(speed * d);
            location.add(move);
            speed -= d / 500f;
        }

        if (unpicable > 0) {
            unpicable -= d;
            if (unpicable <= 0) pickable = true;
        }
    }

    @Override
    public int compareTo(Item t) {
        return type.ordinal() - t.type.ordinal();
    }

}
