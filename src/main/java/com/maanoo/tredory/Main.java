// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory;

import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.states.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Akritas
 */
public class Main extends StateBasedGame {

    /**
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {

        AppGameContainer game = new AppGameContainer(new Main()); 
        game.setIcon("com/maanoo/tredory/data/icon.png"); 

        Op.load(args);

        if(Op.fullscreen){
            DisplayMode ddm = Display.getDesktopDisplayMode();
            game.setDisplayMode(ddm.getWidth(), ddm.getHeight(), true);
        }else{
            game.setDisplayMode(Op.w, Op.h, false);
        }
        
        game.start();
    }

    public Main() {
        super("Tredory");
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        
        gc.setMaximumLogicUpdateInterval(Op.fps);
        gc.setTargetFrameRate(Op.fps);
        
        gc.setVSync(Op.vsync);        
        gc.setAlwaysRender(Op.alwaysRender);
        
        gc.setClearEachFrame(true);
        gc.setMultiSample(1);
        gc.setShowFPS(false);

        Assets.load();
        
        gc.setDefaultFont(Assets.font1);
        gc.setMouseCursor(Assets.cursor.getSubImage(0, 0).getScaledCopy(2), 0, 0);
        
        this.addState(new StateMenu());
        this.addState(new StateGame());
        this.addState(new StateOver());
        this.addState(new StateGallery());
        this.addState(new StateOptions());
        
    }

    
}
