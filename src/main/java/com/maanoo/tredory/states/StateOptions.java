// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import com.maanoo.tredory.Op;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


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
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        ; // TODO implement
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input in = container.getInput();

        if (in.isKeyPressed(Op.Keys.Select) || in.isKeyPressed(Op.Keys.Back)) {
            changeState(game, StateId.Menu);
        }
    }

}
