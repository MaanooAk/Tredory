// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.IDraw;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.effect.Effect;
import com.maanoo.tredory.core.entity.effect.EffectStack;
import com.maanoo.tredory.core.memory.Poolable;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.core.utils.Rectangle;
import com.maanoo.tredory.face.assets.SpriteBundle;


/**
 * @author MaanooAk
 */
public class Entity implements IUpdate, IDraw, Poolable {

    // TODO fame gain
    // TODO remove frame depend and add time depend
    // TODO fix player not dead after die call
    // TODO move all objects to init

    private final float lspeed_base = 0.35f;

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
    public float speed;
    public float angle;
    public float lspeed;
    /**
     * Stores the last vector that was added to the location.
     */
    public Point move;
    public EntityState state;
    public SpriteBundle sprites;
    public boolean spriteRotate;
    public int size;
    /**
     * Collision circle size.
     */
    public int sizecol;
    public boolean alerted;
    public int shieldsSum;
    public boolean opened;

    public final EffectStack effects;

    protected final int targetlayer;

    public Actions actions;

    public Entity() {
        move = new Point();
        targetlayer = 5;
        effects = new EffectStack();
    }

    public Entity(Team team, Point location, float angle, SpriteBundle sprites) {
        move = new Point();
        effects = new EffectStack();
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
        speed = 0;
        lspeed = lspeed_base;
        spriteRotate = true;
        move.init();
        effects.init(this);
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

        sprites.getAnimation(state).addProgress(d);

        actions.update(d);

        effects.update(d);

        switch (state) {
        case Attack:

            final Action action = actions.getActive();

            if (action == null) {

                stopAction();

            } else {

                sprites.action.setSprite(action.getAnimationFrame());
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

            if (!sprites.die.isActive()) {
                dead = true;
            }

            break;
        default:
            break;
        }
    }

    public final void changeAngle(float angle, float max, int d) {
        this.angle = (this.angle + Ma.limit(Ma.anglesub(angle, this.angle), max * d) + 360) % 360;
    }

    @Override
    public void draw(Graphics g, int layer) {

        if (layer == targetlayer) {

            g.pushTransform();
            g.translate(location.x, location.y);
            if (spriteRotate) g.rotate(0, 0, angle + 180);

            sprites.getAnimation(state).getSprite().draw(-size, -size, 2 * size, 2 * size);

            g.popTransform();
        }

        if (Op.debug && layer == 9) {
            g.pushTransform();
            g.translate(location.x, location.y);
            if (spriteRotate) g.rotate(0, 0, angle + 180);

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
        return effects.getEffect();
    }

    public void updateEffectsEffects() {

        lspeed = getEffect().speed.apply(lspeed_base);
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
    public final void startAction(Action action) {

        if (action.canStartBackground()) {
            action.start();

        } else if (action.canStart() && changeState(EntityState.Attack)) {
            action.start();

            sprites.action.reset();
        }

    }

//    // TODO remove
//    public final void startAttack(float speed) {
//
//        sprites.attack.reset();
//        sprites.attack.setSpeed(Ma.min(speed, 14f)); // TODO 86124 remove min
//
//        if (spritesMask != null) {
//            spritesMask.attack.restart();
//            spritesMask.attack.setSpeed(Ma.min(speed, 14f));
//        }
//
//        state = EntityState.Attack;
//    }

    public final void stopAction() {

        sprites.action.reset();

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
