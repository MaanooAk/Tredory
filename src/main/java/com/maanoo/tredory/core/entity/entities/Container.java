// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundleEntity;

/**
 * @author MaanooAk
 */
public class Container extends Entity {

    public int tier;

    private static final int shieldsSumPerTier[] = new int[]{0, 1, 3, 8};

    public Container(Team team, int tier, Point location, float angle, SpriteBundleEntity sprites) {
        super(team, location, angle, sprites);

        this.tier = tier;

        shieldsSum = shieldsSumPerTier[tier];

    }

    @Override
    public void drops() {
        super.drops();

        Core.c.addItem(this, tier);
    }


}
