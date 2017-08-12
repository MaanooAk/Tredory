// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.achieve.Achievement;
import com.maanoo.tredory.core.achieve.Achievement.Status;
import com.maanoo.tredory.core.achieve.Achievements;
import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class StateAchievements extends State {

    public StateAchievements() {
        super(StateId.Achievements);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // TODO Auto-generated method stub

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        final int w = gc.getWidth(), h = gc.getHeight();

        float y = 100;

        for (final Achievement i : Achievements.instance) {

            if (i.status == Status.Hidden) continue;

            Assets.font1.drawString(80, y, (i.status == Status.Complete) ? "" + ((char) (32 - 16)) : "");
            Assets.font1.drawString(120, y, i.info.name);

            y += Assets.font1.getLineHeight() * 1.5f;
        }

        final String message = "Press ESC to exit";
        Assets.font1.drawStringCentered(w / 2, h - 50, message);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        final Input in = container.getInput();

        if (in.isKeyPressed(Op.Keys.Back)) {
            changeState(game, StateId.Menu);
        }
    }

}
