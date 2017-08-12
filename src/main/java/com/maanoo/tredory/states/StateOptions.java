// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.face.assets.Assets;


/**
 * @author MaanooAk
 */
public class StateOptions extends State {

    public StateOptions() {
        super(StateId.Options);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        ; // TODO implement
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        final int w = gc.getWidth(), h = gc.getHeight();

        Assets.font1.drawStringMarkdown(100, 100,
                "These are not the options, this is the markdown print test\n\n\n#Options\n\n##Video\n\nThis is the first section\n\n##Controls\n\nThis is for the keyboard\n\n###Mouse\n\nThis is for the mouse");

        final String message = "Press ESC to exit";
        Assets.font1.drawStringCentered(w / 2, h - 50, message);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        final Input in = container.getInput();

        if (in.isKeyPressed(Op.Keys.Select) || in.isKeyPressed(Op.Keys.Back)) {
            changeState(game, StateId.Menu);
        }
    }

}
