// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author MaanooAk
 */
public abstract class State extends BasicGameState {

    public final StateId stateid;

    public State(StateId stateid) {
        this.stateid = stateid;
    }

    protected void changeState(StateBasedGame game, StateId stateId) {
        game.enterState(stateId.ordinal());
    }

    @Override
    public int getID() {
        return stateid.ordinal();
    }


}
