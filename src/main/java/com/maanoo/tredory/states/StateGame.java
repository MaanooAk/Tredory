// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.Op.Keys;
import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Draws;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.achieve.Achievements;
import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Collision;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.entity.item.Uniques;
import com.maanoo.tredory.core.memory.Inspector;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Str;
import com.maanoo.tredory.engine.Logger;
import com.maanoo.tredory.engine.Screenshot;
import com.maanoo.tredory.engine.profile.Profile;
import com.maanoo.tredory.face.InputHandler;
import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.face.ui.InterfaceMap;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class StateGame extends State {

    private int w, h;
    private float zoom;

    // TODO change
    private Core c;

    private final InterfaceMap face;

    private final InputHandler inhalder;

    // TODO add mouse position in the map coordinate system

    public StateGame() {
        super(StateId.Game);

        face = new InterfaceMap();

        inhalder = new InputHandler();

        zoom = 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        Profile.global.add("render");
        Profile.global.add("update");
        Profile.global.add("special");

    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        super.enter(gc, game);

        gc.getInput().clearKeyPressedRecord();
        gc.getInput().clearMousePressedRecord();

        c = Core.c;

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

        Profile.global.start("render");

        face.setSize(gc.getWidth(), gc.getHeight());
        face.setZoom(zoom);

        face.draw(g);

        if (Op.debug) {
            Assets.font1.drawString(10, 10,
                    Str.create("fps ", Integer.toString(gc.getFPS()), " | e ", Integer.toString(c.entities.count()),
                            " | s ", Integer.toString(c.entities.count() + c.map.things.size()), " | dr ",
                            Draws.draws_performed, " | tb ", Draws.texture_binds, " | cd ",
                            Integer.toString(Collision.collision_checks), " ",
                            Integer.toString(Collision.collision_detections), " | p ",
                            Integer.toString((int) c.player.location.x), " ",
                            Integer.toString((int) c.player.location.y), " ", Integer.toString((int) c.player.angle)),
                    Color.darkGray);
            Assets.font1.drawString(10, h - 200,
                    Str.create(Long.toString(Profile.global.get("render") / 100000), " ",
                            Long.toString(Profile.global.get("update") / 100000), " ",
                            Long.toString(
                                    160 - (Profile.global.get("render") - Profile.global.get("update")) / 100000)),
                    Color.darkGray);
            Assets.font1.drawString(10, h - 120,
                    Str.create("gcs ", Long.toString(Inspector.instance.getGcs()), " | am ",
                            Float.toString(Inspector.instance.getAllocatedMemory()),
                            (Inspector.instance.didGarbageCollect() ? " | Garbage collection performed" : "")),
                    Color.darkGray);
        }
        if (Op.debug || Op.debugBare) {

            g.setColor(Color.darkGray);

            g.fillRect(10, h - 180, 10000f / 60f, 2f);
            g.fillRect(10, h - 183, (Profile.global.get("render") + Profile.global.get("update")) / 100000f, 2f);
            g.fillRect(10, h - 186, Profile.global.get("update") / 100000f, 2f);
            g.fillRect(10, h - 189, Profile.global.get("special") / 100000f, 2f);

            g.fillRect(10, h - 100, 160f, 2f);
            g.fillRect(10, h - 103, Inspector.instance.getAllocatedMemory() * 160f, 2f);
            if (Inspector.instance.didGarbageCollect()) g.fillRect(10, h - 106, 160f, 2f);
        }

        Profile.global.end("render");

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int d) throws SlickException {

        Profile.global.start("update");

        w = gc.getWidth();
        h = gc.getHeight();

        inhalder.process(gc.getInput(), gc.getWidth(), gc.getHeight());

        final Input in = gc.getInput(); // TODO remove

        // TODO move time handling into core
        // TODO implement time speed

        if (in.isKeyDown(Input.KEY_P)) d = d / 2;
        if (in.isKeyDown(Input.KEY_O)) d = d / 5;

        if (in.isKeyPressed(Keys.Back)) {

            changeState(game, StateId.Menu);
        }

        if ((c.player.getState() != EntityState.Action
                || c.player.actions.getActive().getState() != Action.State.Charging)
                && c.player.getState() != EntityState.Die) { // TODO change second coondition

//            c.player.angle = inhalder.mouseAngle;
            c.player.changeAngle(inhalder.mouseAngle, 1, d);
        }

        Action action = null;

        if (inhalder.isSelectedAction) {
            action = Core.c.player.getActions().get(inhalder.selectedActionGroup, inhalder.selectedAction);
        }

        if (c.player.getState() != EntityState.Action) {

            if (action != null) {

                c.player.startAction(action);
            }

        } else {

            final Action active_action = c.player.actions.getActive();

            if (action != null && action.canStartBackground()) {

                c.player.startAction(action);

            } else if (active_action != null && active_action.isRecharge()) {
                if (action != active_action) {
                    active_action.stopRecharge();
                }
            }

        }

        { // player input movement

            boolean perform_move = false;

            if (!inhalder.direction.isZero()) {
                if (c.player.getState().canMove()) {
                    perform_move = true;

                } else if (c.player.getState().canInterrupt()) {
                    c.player.changeState(EntityState.Move);
                    if (c.player.getState().canMove()) {
                        perform_move = true;
                    }
                }
            } else {
                if (c.player.getState() == EntityState.Move) {
                    c.player.changeState(EntityState.Idle);
                }
            }

            if (perform_move) {

                // TODO move to entity
                // calculate speed multiplier based on movement direction relative to facing
                // angle
                float m = Ma.abs(inhalder.direction.angle() - c.player.angle);
                while (m > 180)
                    m -= 360;
                m = Ma.abs(m / 180f);

                final float speed = (1 - m * .6f) * c.player.lspeed;

                c.player.location.add(inhalder.direction.norm().mul(speed * d));

            }
        }

        if (in.isKeyPressed(Keys.Select)) {
            c.player.selectpressed = true;
        }

        if (in.isKeyPressed(Op.Keys.Screenshot)) {
            Screenshot.capture(gc);
        }

        // ====

        c.update(d);

        // ====

        if (in.isKeyDown(Input.KEY_TAB)) {
            zoom *= 0.985f;
            if (zoom <= 0.1f) zoom = 0.1f;
        } else if (zoom != 1) {
            zoom /= 0.94f;
            if (zoom >= 1) zoom = 1;
        }
        zoom = in.isKeyDown(Input.KEY_TAB) ? 0.1f : 1f;

        if (in.isKeyDown(Input.KEY_1)) Assets.bam.playAt(new Point(in.getMouseX() - (w / 2) + c.player.location.x,
                in.getMouseY() - (h / 2) + c.player.location.y));

        if (in.isKeyPressed(Input.KEY_2)) c.player.takeItem(new Uniques.Heart());
        if (in.isKeyPressed(Input.KEY_F6)) c.player.takeItem(new Uniques.EnergyShield());

        if (in.isKeyPressed(Input.KEY_3)) c.player.coins += 10;

        if (in.isKeyPressed(Input.KEY_4)) c.addItem(c.player, 4);

        if (in.isKeyPressed(Input.KEY_7)) c.player.team = c.player.team == Team.Good ? Team.Bad : Team.Good;

        if (in.isKeyPressed(Input.KEY_8)) c.addItem(c.player, 2);

        if (in.isKeyPressed(Input.KEY_9)) c.requestNewMap();

        if (in.isKeyDown(Input.KEY_0)) Core.c.player.souls.addSoul();

        if (in.isKeyPressed(Input.KEY_F1)) {
            if (in.isKeyDown(Input.KEY_LSHIFT)) Op.debugBare = !Op.debugBare;
            else Op.debug = !Op.debug;
        }

        if (in.isKeyPressed(Input.KEY_F2)) {
            Op.second = !Op.second;
            Logger.log("DEBUG", "second: " + Op.second);
        }

        if (c.player.dead) {
            gameover(game);
        }

        Profile.global.end("update");

    }

    public void gameover(StateBasedGame game) {

        Achievements.instance.check(c.player);

        changeState(game, StateId.Over);
    }

}
