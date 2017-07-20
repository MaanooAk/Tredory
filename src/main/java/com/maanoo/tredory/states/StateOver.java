// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.engine.Sprite;
import com.maanoo.tredory.face.assets.Assets;


/**
 * @author MaanooAk
 */
public class StateOver extends State {

    public StateOver() {
        super(StateId.Over);
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        gc.getGraphics().setFont(Assets.font1);
    }

    private int t;
    private String stats[];

    public ArrayList<Sprite> killed;
    private ArrayList<Point> killedp;
    private float killedph;

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        super.enter(gc, game);

        gc.getInput().clearKeyPressedRecord();

        final int w = gc.getWidth(), h = gc.getHeight();

        t = 0;

        // Stats.kills = 478;

        stats = new String[] { "" + Core.c.player.life / 60000 + "'" + (Core.c.player.life / 1000) % 60 + "\" old", "",
                "" + Core.c.player.coins + " coins", "" + Core.c.player.stats.maps + " maps",
                "" + Core.c.player.stats.kills + " kills" };

        killed = Core.c.player.stats.killed;
        killedp = new ArrayList<>();

        final int xm = w / 2, xM = w - 64, ym = 0, yM = h - 64;
        int x = xm, y = ym;
        for (int i = 0; i < Core.c.player.stats.kills; i++) {
            killedp.add(new Point(Ra.global.range(x - 2, x + 2), Ra.global.range(y - 2, y + 2)));

            x += 32;
            if (x > xM) {
                x = xm;
                y += 32;
            }
        }
        killedph = y - 32;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        final int w = gc.getWidth(), h = gc.getHeight();

        Core.c.player.sprites.idle.getSprite().draw(w / 4 - 32, 150, 64, 64);

        int y = 300;
        for (int i = 0; i < stats.length; i++) {
            Assets.font1.drawString(40, y, stats[i], Color.lightGray);
            y += 14;
        }

        g.pushTransform();
        g.translate(0, (float) (h / 2 - (Math.cos(t / 8000f) * 0.5f + 0.5f) * killedph));
        for (int i = 0; i < killed.size(); i++) {
            killed.get(i).draw(killedp.get(i).x, killedp.get(i).y, 1);
        }

        g.popTransform();

        final String message = "Press SPACE to continue";
        Assets.font1.drawString(w / 4 - Assets.font1.getWidth(message) / 2, h - 100, message);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int d) throws SlickException {

        t += d;

        if (gc.getInput().isKeyPressed(Input.KEY_SPACE) || gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {

            changeState(game, StateId.Menu);
        }

    }

}
