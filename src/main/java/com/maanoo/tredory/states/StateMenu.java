// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import com.maanoo.tredory.core.Ma;
import com.maanoo.tredory.face.assets.Assets;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author MaanooAk
 */
public class StateMenu extends State {

    private int t;

    public StateMenu() {
        super(StateId.Menu);
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        t = 0;

        gc.getGraphics().setFont(Assets.font1);
    }

    private String menu[], menuInd[];
    private int menu_index;

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        super.enter(gc, game);

        gc.getInput().clearKeyPressedRecord();

        menu = new String[]{
                "Press SPACE to start",
                "Press SPACE to enter the gallery",
                "Press SPACE to enter the options"
        };
        menuInd = new String[]{
                "",
                "- O -",
                "- - O"
        };
        menu_index = 0;

    }


    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        int w = gc.getWidth(), h = gc.getHeight();

        Assets.icon.draw(w / 2 - Assets.icon.getWidth() * 4, (float) (50 + Math.cos(t / 800.0) * 8), 8);

        String message = menu[menu_index];
        Assets.font1.drawString(w / 2 - Assets.font1.getWidth(message) / 2, h - 100, message);

        message = menuInd[menu_index];
        Assets.font1.drawString(w / 2 - Assets.font1.getWidth(message) / 2, h - 70, message, Color.darkGray);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        t += delta;

        if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {

            switch (menu_index) {
            case 0:

                Assets.trans.play();
                changeState(game, StateId.Game);
                break;
            case 1:

                changeState(game, StateId.Gallery);
                break;
            case 2:

                changeState(game, StateId.Options);
                break;
            }

        }

        if (gc.getInput().isKeyPressed(Input.KEY_A)) menu_index -= 1;
        if (gc.getInput().isKeyPressed(Input.KEY_W)) menu_index -= 1;
        if (gc.getInput().isKeyPressed(Input.KEY_D)) menu_index += 1;
        if (gc.getInput().isKeyPressed(Input.KEY_S)) menu_index += 1;
        menu_index = Ma.looparound(menu_index, menu.length);


        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            gc.exit();
        }

        if (gc.getInput().isKeyPressed(Input.KEY_F)) {
            //setDisplayMode(Display.getDesktopDisplayMode().getWidth(), Display.getDesktopDisplayMode().getHeight(), true);
        }

    }

}
