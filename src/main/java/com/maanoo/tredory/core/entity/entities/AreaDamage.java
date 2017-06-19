// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundle;
import com.maanoo.tredory.face.assets.Assets;

/**
 * @author MaanooAk
 */
public class AreaDamage extends Entity {

    public boolean damaging;

    public AreaDamage(Team team, Point location, float angle, SpriteBundle sprites, float attackspeed) {
        super(team, location, angle, sprites);

        undead = true;

        damaging = false;

        startAttack(attackspeed);
    }

    @Override
    public void update(int d) {
        super.update(d);

        angle -= d / 2f;

        switch (state) {
        case Idle:

            if (life > 1200) {
                damaging = false;
                state = EntityState.Die;

            }

            break;
        case Attack:

            if (sprites.attack.getFrame() >= 4) {
                state = EntityState.Idle;
                damaging = true;
            }

            break;
        }

    }

    @Override
    public void collide(Entity e) {
        //super.collide(e);

        if (damaging && !e.undead && e.team != team) {

            Assets.bam.playAt(location);

            e.takeDamage();
        }
    }
}
