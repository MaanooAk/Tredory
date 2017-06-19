// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.memory.Poolable;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.face.SpriteBundle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author MaanooAk
 */
public class Entity implements IUpdate, IDraw, Poolable {

    // TODO fame gain

    /**
     * The duration of its life.
     */
    public int life;
    public boolean undead;
    public boolean dead;
    /**
     * If it can be picked up by an other Entity.
     */
    public boolean pickable;
    public boolean stepable;
    public boolean activatable;
    public boolean movable;
    public Team team;
    public Point location;
    public Point speed;
    public float angle;
    /**
     * Stores the last vector that was added to the location.
     */
    public Point move;
    public EntityState state;
    public SpriteBundle sprites;
    public int size;
    /**
     * Collision circle size.
     */
    public int sizecol;
    public boolean alerted;
    public int shieldsSum;
    public boolean opened;

    public Attack activeAttack;

    public Entity() {
        speed = new Point();
        move = new Point();
    }

    public Entity(Team team, Point location, float angle, SpriteBundle sprites) {
        speed = new Point();
        move = new Point();
        init(team, location, angle, sprites);
    }

    public void init() {
        life = 0;
        undead = false;
        movable = true;
        dead = false;
        pickable = false;
        stepable = false;
        activatable = false;
        state = EntityState.Idle;
        size = 32;
        sizecol = size / 2;
        alerted = false;
        shieldsSum = 0;
        activeAttack = null;
        speed.init();
        move.init();
    }

    public void init(Team team, Point location, float angle, SpriteBundle sprites) {
        init();
        this.team = team;
        this.location = location;
        this.angle = angle;
        this.sprites = sprites.copy();
    }

    @Override
    public void update(int d) {
        life += d;

        sprites.getAnimation(state).update(d);

        if (activeAttack != null) {
            activeAttack.update(d);
        }

        switch (state) {
        case Attack:

            if (sprites.attack.isStopped()) { // TODO find a new way to determine if its over and then remove 86123 and 86124
                stopAttack();
            }

            if (activeAttack != null && activeAttack.isActive()) {
                if (sprites.attack.getFrame() > 4) {
                    sprites.attack.setCurrentFrame(4);
                }
            }

            break;
        case Move:

            //location.add(speed.clone().mul(d));
            break;
        case Die:

            if (!opened && sprites.die.getFrame() >= 4) {
                opened = true;
                drops();
            }

            if (sprites.die.isStopped()) {
                dead = true;
            }

            break;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.pushTransform();
        g.translate(location.x, location.y);
        g.rotate(0, 0, angle + 180);

        sprites.getAnimation(state).draw(-size, -size, 2 * size, 2 * size);

        if (Op.debug) {
            g.setColor(Color.red);
            g.drawOval(-sizecol, -sizecol, 2 * sizecol, 2 * sizecol);
        }

        g.popTransform();
    }

    public Effect getEffect() {
        return Effect.empty;
    }

    public void collide(Entity e) {

        if (!undead && !e.undead) {

            Point vec = Points.create(e.location).sub(location).norm();
            Point vec1 = Points.create(vec).mul(-1);
            Point vec2 = Points.create(vec).mul(1);

            if (movable) location.add(vec1);
            if (e.movable) e.location.add(vec2);

            Points.dispose(vec, vec1, vec2);
        }

    }

    public final void startAttack(Attack attack) {
        sprites.attack.restart();
        sprites.attack.setSpeed(Ma.min(attack.getAttackspeed(getEffect()), 14f)); // TODO 86123 remove min
        state = EntityState.Attack;
        activeAttack = attack;
    }

    public final void startAttack(float speed) {
        sprites.attack.restart();
        sprites.attack.setSpeed(Ma.min(speed, 14f)); // TODO 86124 remove min
        state = EntityState.Attack;
    }

    public final void stopAttack() {
        sprites.attack.restart();
        state = EntityState.Idle;
        activeAttack = null;
    }

    public void takeDamage() {
        if (shieldsSum > 0) {
            shieldsSum -= 1;
        } else {
            die();
        }
    }

    public void drops() {

    }

    public void die() {
        state = EntityState.Die;
    }

    public void activate() {

    }
}
