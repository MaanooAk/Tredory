// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.memory.Poolable;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.face.SpriteBundle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author MaanooAk
 */
public class Entity implements IUpdate, IDraw, Poolable {

    public int life;
    public boolean undead;
    public boolean dead;
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
    public int sizecol;
    public boolean alerted;
    public int shieldsSum;
    public boolean opened;

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

        switch (state) {
        case Attack:

            if (sprites.attack.isStopped()) {
                stopAttack();
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

    public final void startAttack(float speed) {
        sprites.attack.restart();
        sprites.attack.setSpeed(speed);
        state = EntityState.Attack;
    }

    public final void stopAttack() {
        sprites.attack.restart();
        state = EntityState.Idle;
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
