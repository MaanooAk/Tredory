// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.Op.Keys;
import com.maanoo.tredory.core.*;
import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Attack;
import com.maanoo.tredory.core.entity.AttackOld;
import com.maanoo.tredory.core.entity.Collision;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.entity.entities.Item;
import com.maanoo.tredory.core.entity.entities.ItemType;
import com.maanoo.tredory.core.memory.Inspector;
import com.maanoo.tredory.core.utils.Colors;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Str;
import com.maanoo.tredory.face.assets.Assets;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author MaanooAk
 */
public class StateGame extends State {

    private int w, h;
    private float zoom;

    private Core c;

    // TODO to core
    // TODO support multiple players (in the logic)
    private PlayerAttacks pa;

    /**
     * The mouse position with origin the top left corner of the screen.
     */
    private final Point mouse;

    /**
     * The mouse position with origin the center of the screen.
     */
    private final Point mouseC;

    // TODO add mouse position in the map coordinate system

    public StateGame() {
        super(StateId.Game);

        mouse = new Point();
        mouseC = new Point();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {


    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        super.enter(gc, game);

        gc.getInput().clearKeyPressedRecord();

        if (c == null || c.player.dead) {

            c = new Core();
            Core.c = c;
            c.init();

            pa = new PlayerAttacks(c.player, Assets.fireball);

            c.player.actions.add(pa.spellFireball1);
            c.player.actions.add(pa.spellFireball2);
            c.player.actions.add(pa.spellFireball3);
            c.player.actions.add(pa.spellFireballCyclone1);
            c.player.actions.add(pa.spellFireballCyclone2);
            c.player.actions.add(pa.spellTeleport);
            c.player.actions.add(pa.spellSwap);
            c.player.actions.add(pa.spellChannel);
            c.player.actions.add(pa.spellPush);
            
            zoom = 1;

            Stats.reset();
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        w = gc.getWidth();
        h = gc.getHeight();

        //g.setAntiAlias(true); // TODO can we support aa ?

        // ===
        g.pushTransform();
        g.translate(w / 2, h / 2);
        g.scale(zoom, zoom);
        g.translate(-c.camera.x, -c.camera.y);

        Draws.set(g, c.camera.x - w/2/zoom, c.camera.y - h/2/zoom, w/zoom,  h/zoom);

        c.map.pushDraw();

        for (Entity i : c.l) i.pushDraw();

        c.player.pushDraw();

        Draws.drawAll();

        g.popTransform();

        // map
        {
            g.setColor(Colors.black75);
            g.fillRect(-1, -1, 10 + 96, 10 + 96);
            g.setColor(Color.darkGray);
            g.drawRect(-1, -1, 10 + 96, 10 + 96);

            g.pushTransform();
            g.translate(5 + 48, 5 + 48);

            {
                g.pushTransform();
                g.scale(0.02f, 0.02f);
                g.translate(-c.camera.x, -c.camera.y);

                c.map.drawMini(g, c.camera, 34f / 0.02f);

                g.popTransform();
            }

            g.setColor(Color.white);
            g.fillOval(-1, 1, 2, 2);

            g.rotate(0, 0, Core.c.arrow_angle);

            Assets.effect_arrow.draw(-48, -48, 96, 96, Color.gray);


            g.popTransform();
        }
        // TODO create gui object
        // shields
        {

            if (c.player.shields.size() > 0) {
                g.setColor(Colors.black75);
                g.fillRect(-1, h - 52, 33 + 18 * c.player.shields.max + 1, 52);
                g.setColor(Color.darkGray);
                g.drawRect(-1, h - 52, 33 + 18 * c.player.shields.max + 1, 52);

                int x = 10;
                for (int i = c.player.shields.size() - 1; i >= 0; i--) {
                    c.player.shields.get(i).sprites.idle.draw(x, h - 42, 32, 32);
                    x += 18;
                }
            }
        }
        // crystal
        {
            int count = c.player.crystals.size();
            if (count > 0) {

                int pad = 16 - 4;
                int wi = 20 + count * pad;

                g.setColor(Colors.black75);
                g.fillRect(w - wi, h - 52, wi, 52);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi, h - 52, wi, 52);

                int x = w - wi + 10 - pad + 2, y = 2;
                if (count % 2 == 0) y *= -1;
                for (int i = 0; i < c.player.crystals.size(); i++) {
                    c.player.crystals.get(i).sprites.idle.draw(x, h - 42 + y, 32, 32);
                    x += pad;
                    y *= -1;
                }
            }
        }
        // stones and souls
        {
            // stones

            int count = c.player.stones.size();
            if (count > 0) {

                int pad = 16 - 4 + 6;
                int wi = 20 + count * pad;

                g.setColor(Colors.black75);
                g.fillRect(w - wi, h - 52*2, wi, 52);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi, h - 52*2, wi, 52);

                int x = w - wi + 10 - pad + 12, y = 2;
                if (count % 2 == 0) y *= -1;
                for (int i = 0; i < c.player.stones.size(); i++) {
                    c.player.stones.get(i).sprites.idle.draw(x, h - 42 + y - 52, 32, 32);
                    x += pad;
                    y *= -1;
                }

                // souls

                int barh = 5;

                g.setColor(Color.darkGray);
                g.fillRect(w - wi, h - 52*2 - barh, wi * c.player.souls.getSouls() / c.player.souls.getCapacity(), barh);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi, h - 52*2 - barh, wi, barh);
            }
        }
        // coins
        {
            if (c.player.coins > 0) {
                final int groups = c.player.coins < 400 ? 5 : c.player.coins < 800 ? 10 : c.player.coins < 2000 ? 20 : 30;

                int wi = 16 * (c.player.coins / (groups * 10)) + 16;
                wi += groups <= 10 ? (10 / groups) * 16 : 16;

                g.setColor(Colors.black75);
                g.fillRect(w - wi - 10, 0, wi + 20, 28 + 3 * groups);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi - 10, -1, wi + 20 + 1, 28 + 3 * groups + 1);

                int x = w - 10 - 16;
                int y = 0;
                int left = c.player.coins;
                while (left >= 10) {
                    Assets.getItem(ItemType.Gold).get().draw(x, y, 2);
                    left -= 10;
                    y = (y + 3) % (3 * groups);
                    if (y == 0) x -= 16;
                }

                if (y != 0) {
                    y = 0;
                    x -= 16;
                }
                while (left >= 1) {
                    Assets.getItem(ItemType.Copper).get().draw(x, y, 2);
                    left -= 1;
                    y = (y + 3) % (3 * groups);
                    if (y == 0) x -= 16;
                }
            }

        }
        // spells
        {
            int count = 4;

            int wi = 16;

            int x = (int) (-count / 2f * (wi + 10));
            for (int i = 0; i < count; i++) {

                g.setColor(Colors.black75);
                g.fillOval(w / 2 + x, h - wi - 10, wi, wi);
                g.setColor(Color.darkGray);
                g.drawOval(w / 2 + x, h - wi - 10, wi, wi);

                g.setColor(Color.darkGray);
                g.fillArc(w / 2 + x, h - wi - 10, wi, wi, -180, -180);

                x += wi + 10;
            }
        }


        // debug
        if (Op.debug) {
            Assets.font1.drawString(10, 10, Str.create(
                    "fps ", Integer.toString(gc.getFPS()),
                    " | e ", Integer.toString(c.l.size()),
                    " | s ", Integer.toString(c.l.size() + c.map.things.size()),
                    " | dr ", Draws.draws_performed,
                    " | cd ", Integer.toString(Collision.collision_checks), " ", Integer.toString(Collision.collision_detections),
                    " | p ", Integer.toString((int) c.player.location.x),
                    " ", Integer.toString((int) c.player.location.y),
                    " ", Integer.toString((int) c.player.angle)),
                    Color.darkGray);
            Assets.font1.drawString(10, h - 100, Str.create(
                    "gcs ", Long.toString(Inspector.instance.getGcs()),
                    " | am ", Float.toString(Inspector.instance.getAllocatedMemory()),
                    (Inspector.instance.didGarbageCollect() ? " | Garbage collection performed" : ""))
                    , Color.darkGray);
        }

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int d) throws SlickException {
        //d = d/10;

        Input in = gc.getInput();

        // TODO use this two variables to get mouse position

        mouse.init(in.getMouseX(), in.getMouseY());
        mouseC.init(in.getMouseX() - w / 2, in.getMouseY() - h / 2);

        // TODO move time handling into core
        // TODO implement time speed

        if (in.isKeyDown(Input.KEY_P)) d = d / 2;
        if (in.isKeyDown(Input.KEY_O)) d = d / 5;


        if (in.isKeyPressed(Keys.Back)) {

            changeState(game, StateId.Menu);
        }

        if (c.player.state != EntityState.Attack || c.player.sprites.attack.getFrame() >= 4) { // TODO change second coondition

            c.player.angle = (float) (-Math.atan2(gc.getInput().getMouseX() - w / 2, gc.getInput().getMouseY() - h / 2) * 360 / (2 * Math.PI));
        }

        if (c.player.state != EntityState.Attack) {

            Action action = null;

            if (in.isMouseButtonDown(Keys.Attack1)) action = pa.getAttack(0, 0);
            if (in.isMouseButtonDown(Keys.Attack2)) action = pa.getAttack(0, 1);
            if (in.isMouseButtonDown(Keys.Attack3)) action = pa.getAttack(0, 2);
            if (in.isKeyDown(Keys.Attack4)) action = pa.getAttack(0, 3);

            for (int i = 0; i < 7; i++) if (in.isKeyDown(Keys.Spell[i])) action = pa.getAttack(1, i);

            if (action != null) {
                c.player.startAttack(action);
                action.start();
            }

        }

        if (c.player.state != EntityState.Attack) {

            Point vec = new Point();
            if (!in.isKeyDown(Keys.PickUp)) {
                if (in.isKeyDown(Keys.MoveU)) vec.y -= 1;
                if (in.isKeyDown(Keys.MoveD)) vec.y += 1;
                if (in.isKeyDown(Keys.MoveL)) vec.x -= 1;
                if (in.isKeyDown(Keys.MoveR)) vec.x += 1;
            }

            // move player
            if (!vec.isZero()) {

                // calculate speed multiplier based on movement direction relative to facing angle
                float m = Ma.abs(vec.angle() - c.player.angle);
                while (m > 180) m -= 360;
                m = Ma.abs(m / 180f);

                float speed = (1 - m * .6f) * c.player.lspeed;

                c.player.location.add(vec.norm().mul(speed * d));
            }


            if (c.player.state == EntityState.Idle && !vec.isZero()) {
                c.player.state = EntityState.Move;

            } else if (c.player.state == EntityState.Move && vec.isZero()) {
                c.player.state = EntityState.Idle;
            }

        }

        if (in.isKeyPressed(Keys.Select)) {
            c.player.selectpressed = true;
        }

        // ====

        c.update(d);
        pa.update(d);

        // ====

        if (in.isKeyDown(Input.KEY_TAB)) {
            zoom *= 0.985f;
            if (zoom <= 0.1f) zoom = 0.1f;
        } else if (zoom != 1) {
            zoom /= 0.94f;
            if (zoom >= 1) zoom = 1;
        }
        zoom = in.isKeyDown(Input.KEY_TAB) ? 0.1f : 1f;

        if (in.isKeyDown(Input.KEY_1)) Assets.bam.playAt(new Point(
                in.getMouseX() - (w / 2) + c.player.location.x,
                in.getMouseY() - (h / 2) + c.player.location.y));

        if (in.isKeyPressed(Input.KEY_2)) c.player.takeShield(new Item(ItemType.Shield0, null));

        if (in.isKeyPressed(Input.KEY_3)) c.player.coins += 10;

        if (in.isKeyPressed(Input.KEY_4)) Core.c.addItem(c.player, 4);

        if (in.isKeyPressed(Input.KEY_7)) c.player.team = c.player.team == Team.Good ? Team.Bad : Team.Good;

        if (in.isKeyPressed(Input.KEY_8)) Core.c.addItem(c.player, 2);

        if (in.isKeyPressed(Input.KEY_9)) Core.c.requestNewMap();

        if (in.isKeyPressed(Input.KEY_0)) c.player.takeDamage();

        if (in.isKeyPressed(Input.KEY_F1)) Op.debug = !Op.debug;

        if (c.player.dead) {
            changeState(game, StateId.Over);
        }
    }

}
