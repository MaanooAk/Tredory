// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.Assets;

/**
 * @author MaanooAk
 */
public class Projectile extends Entity {

    public boolean damaging;

    public float rota;
    public float lifetime;

    private float tmp;

    public Projectile() {
    }

    public Projectile init(Team team, Point location, float angle, SpriteBundleEntity sprites, float speed, float rota, float attackspeed, float lifetime) {
        init(team, location, angle, sprites);
        this.rota = rota;
        this.lifetime = lifetime;

        this.speed.init(angle).mul(speed);

        undead = true;

        damaging = false;

        startAttack(attackspeed); tmp = attackspeed;

        return this;
    }

    @Override
    public void update(int d) {
        super.update(d);

        switch (state) {
        case Attack:

            if (life * tmp >= 200) {
                damaging = true;
            }

            if (life * tmp >= 400) {
                state = EntityState.Move;
                lifetime += life;
            }

            break;
        case Move:

            if (rota != 0) {
                angle += rota * d;
                speed.rotate(rota * d);
            }
            move.init(speed).mul(d);
            location.add(move);

            if (life >= lifetime) {
                damaging = false;
                state = EntityState.Die;
            }

            break;
        }

    }

    @Override
    public void collide(Entity e) {
        super.collide(e);

        if (damaging && !e.undead && e.team != team) {

            Assets.bam.playAt(location);

            takeDamage();
            e.takeDamage();
        }
    }


}