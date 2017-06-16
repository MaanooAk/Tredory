// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.*;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.Assets;

/**
 * TODO activate somehow
 *
 * @author MaanooAk
 */
public class Altar extends Entity {

    public Altar(Team team, Point location, float angle) {
        super(team, location, angle, new SpriteBundleEntity(Assets.altar.get()));

        shieldsSum = 2;

        movable = false;
        activatable = true;
    }

    @Override
    public void activate() {
        super.activate();

        if (state != EntityState.Idle) return;

        Player p = Core.c.player;

        Recipe rec = Recipes.getRecipe(p.crystals, p.shields);

        if (rec != null) {

            for (ItemType i : rec.in.items) {
                p.giveItem(i);
            }
            for (ItemType i : rec.out.items) {
                CoreDrops.dropItem(Core.c, this, 0, 360, 0.2f, 0.25f, i);
            }

        }

        super.takeDamage();
    }

    @Override
    public void takeDamage() {

    }

}
