// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.face.assets.SpriteBundleStatic;


/**
 * @author MaanooAk
 */
public class Item extends Entity implements Comparable<Item> {

    public final ItemType type;
    public final ItemTag tag;

    // TODO change into time diff based not absolute values
    private int unpicable;

    public Item(ItemType type, Point location) {
        super(Team.Neutral, location, 180, new SpriteBundleStatic(Assets.getItem(type).get()));

        this.type = type;
        this.speed = 0;

        tag = ItemType.getTag(type);

        undead = true;
        pickable = true;
        size = 16;
        sizecol = 16;

        unpicable = 0;

        spriteRotate = false;
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
