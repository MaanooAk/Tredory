// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * @author MaanooAk
 */
public class Portal extends Entity {

    public Portal(Team team, Point location, float angle, SpriteBundleEntity sprites) {
        super(team, location, angle, sprites);

        undead = true;
        stepable = true;

        state = EntityState.Special;
    }

    @Override
    public void update(int d) {
        super.update(d);

    }

    @Override
    public void activate() {
        super.activate();

        Core.c.requestNewMap();
    }

}
