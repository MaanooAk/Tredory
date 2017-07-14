// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Action.State;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * @author MaanooAk
 */
public class Projectile extends Entity {

    public static final class AttackProjectile extends Action {

        public AttackProjectile(Entity user, float charge_time) {
            super(user, charge_time, 0, 0, 0);
        }

        @Override
        public void perform() {
            // nothing
        }

        @Override
        public void end() {
            // end
        }

        @Override
        public void cooldown() {
            // nothing
        }

    }

    public boolean damaging;

    public float rota;
    public float lifetime;

    private float tmp;

    public Projectile() {
    }

    public Projectile init(Team team, Point location, float angle, SpriteBundleEntity sprites, float speed, float rota,
            float charge_time, float lifetime) {
        init(team, location, angle, sprites);
        this.rota = rota;
        this.lifetime = lifetime;

        this.speed = speed;

        undead = true;

        damaging = false;

        final AttackProjectile attack = new AttackProjectile(this, charge_time);
        actions.add(attack);
        startAttack(attack);

        return this;
    }

    @Override
    public void update(int d) {
        super.update(d);

        switch (state) {
        case Attack:

            if (actions.get().getState() == State.Ending) {
                state = EntityState.Move;
                damaging = true;
                lifetime += life;
            }

            break;
        case Move:

            if (rota != 0) {
                angle += rota * d;
            }
            move.init(angle).mul(speed * d);
            location.add(move);

            if (life >= lifetime) {
                damaging = false;
                state = EntityState.Die;
            }

            break;
        }

//    	life += d;
//
//        sprites.getAnimation(state).update(d);
//
//        actions.update(d);
//
//        switch (state) {
//        case Idle:
//
//        	state = EntityState.Move;
//
//            break;
//        case Move:
//
//			if (rota != 0) {
//				angle += rota * d;
//				speed.rotate(rota * d);
//			}
//			move.init(speed).mul(d);
//			location.add(move);
//
//			if (life >= lifetime) {
//				damaging = false;
//				state = EntityState.Die;
//			}
//
//			break;
//        case Die:
//
//            if (!opened && sprites.die.getFrame() >= 4) {
//                opened = true;
//                drops();
//            }
//
//            if (sprites.die.isStopped()) {
//                dead = true;
//            }
//
//            break;
//        }

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
