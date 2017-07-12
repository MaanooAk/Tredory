// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.memory.Poolable;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.core.utils.Rectangle;
import com.maanoo.tredory.face.SpriteBundle;


/**
 * @author MaanooAk
 */
public class Entity implements IUpdate, IDraw, Poolable {

    // TODO fame gain
    // TODO remove frame depend and add time depend
    // TODO fix player not dead after die call

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

    protected final int targetlayer;

    public Actions actions;

    public Entity() {
        speed = new Point();
        move = new Point();
        targetlayer = 5;
    }

    public Entity(Team team, Point location, float angle, SpriteBundle sprites) {
        speed = new Point();
        move = new Point();
        init(team, location, angle, sprites);
        targetlayer = 5;
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
        actions = new ActionsSimple();
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

    public boolean changeState(EntityState new_state) {

        if (state.canInterrupt()) {
            state = new_state;
            return true;
        }
        return false;
    }

    @Override
    public void update(int d) {
        life += d;

        sprites.getAnimation(state).update(d);

        actions.update(d);

        switch (state) {
        case Attack:

            final Action action = actions.getActive();

            if (action == null) {

                stopAttack();

            } else {

                sprites.attack.setCurrentFrame(action.getAnimationFrame());
            }

            break;
        case Move:

            // location.add(speed.clone().mul(d));
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
        default:
            break;
        }
    }

    @Override
    public void draw(Graphics g, int layer) {

        if (layer == targetlayer) {

            g.pushTransform();
            g.translate(location.x, location.y);
            g.rotate(0, 0, angle + 180);

            sprites.getAnimation(state).draw(-size, -size, 2 * size, 2 * size);

            g.popTransform();
        }

        if (Op.debug && layer == 9) {
            g.pushTransform();
            g.translate(location.x, location.y);
            g.rotate(0, 0, angle + 180);

            g.setColor(Color.red);
            g.drawOval(-sizecol, -sizecol, 2 * sizecol, 2 * sizecol);

            g.popTransform();
        }
    }

    @Override
    public boolean needDraw(Rectangle view) {

        return view.inside(location, 4 * size);
    }

    public Effect getEffect() {
        return Effect.EMPTY;
    }

    public void collide(Entity e) {

        if (!undead && !e.undead) {

            final Point vec = Points.create(e.location).sub(location).norm();
            final Point vec1 = Points.create(vec).mul(-1);
            final Point vec2 = Points.create(vec).mul(1);

            if (movable) location.add(vec1);
            if (e.movable) e.location.add(vec2);

            Points.dispose(vec, vec1, vec2);
        }

    }

    // TODO change to start action
    public final void startAttack(Action action) {

        if (action.canStart() && changeState(EntityState.Attack)) {
            action.start();

            sprites.attack.restart();
        }

    }

    // TODO remove
    public final void startAttack(float speed) {
        sprites.attack.restart();
        sprites.attack.setSpeed(Ma.min(speed, 14f)); // TODO 86124 remove min
        state = EntityState.Attack;
    }

    public final void stopAttack() {
        sprites.attack.restart();
        state = EntityState.Idle;

        // TODO revisit
        final Action action = actions.getActive();
        if (action != null) {
            action.stop();
        }
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
        if (state != EntityState.Die) {
            state = EntityState.Die;
        }
    }

    public void activate() {

    }

    // Getters

    public EntityState getState() {
        return state;
    }

}
