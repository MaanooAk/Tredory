// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import com.maanoo.tredory.states.StateGallery;
import com.maanoo.tredory.states.StateGame;
import com.maanoo.tredory.states.StateGameSetup;
import com.maanoo.tredory.states.StateLoading;
import com.maanoo.tredory.states.StateMenu;
import com.maanoo.tredory.states.StateOptions;
import com.maanoo.tredory.states.StateOver;


/**
 * @author MaanooAk
 */
public class Main extends StateBasedGame {

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {

        Op.load(args);

        Log.setVerbose(Op.debug);

        final AppGameContainer game = new AppGameContainer(new Main());
        game.setIcon("com/maanoo/tredory/data/icon.png");

        if (Op.fullscreen) {
            final DisplayMode ddm = Display.getDesktopDisplayMode();
            game.setDisplayMode(ddm.getWidth(), ddm.getHeight(), true);
        } else {
            game.setDisplayMode(Op.w, Op.h, false);
        }

        game.start();
    }

    public Main() {
        super("Tredory");
    }

    @Override
    public void initStatesList(GameContainer gc) {

        gc.setMaximumLogicUpdateInterval(Op.fps);
        gc.setTargetFrameRate(Op.fps);

        gc.setVSync(Op.vsync);
        gc.setAlwaysRender(Op.alwaysRender);

        gc.setClearEachFrame(true);
        gc.setMultiSample(1);
        gc.setShowFPS(false);

        this.addState(new StateLoading());
        this.addState(new StateMenu());
        this.addState(new StateGameSetup());
        this.addState(new StateGame());
        this.addState(new StateOver());
        this.addState(new StateGallery());
        this.addState(new StateOptions());

    }

}
