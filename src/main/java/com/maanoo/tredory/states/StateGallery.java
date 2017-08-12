// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.Op.Keys;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.engine.Sprite;
import com.maanoo.tredory.engine.SpriteSheet;
import com.maanoo.tredory.face.assets.AssetSet;
import com.maanoo.tredory.face.assets.Assets;


/**
 * @author MaanooAk
 */
public class StateGallery extends State {

    public StateGallery() {
        super(StateId.Gallery);
    }

    private ArrayList<Sprite> sprites;
    private int index;

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        super.enter(container, game);

        sprites = new ArrayList<>();
        addImages(Assets.axeman);
        addImages(Assets.maceman);
        addImages(Assets.swordman);

    }

    private void addImages(AssetSet<SpriteSheet> set) {

        for (int i = 0; i < set.getCount(); i++) {
            sprites.add(set.get(i).getSprite(0, 0));
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        final int w = gc.getWidth(), h = gc.getHeight();

        final int pres = 32;
        final int xstep = pres * 2 + 10;
        int x = w / 2 - pres;
        final int y = h - h / 4 - pres;
        x -= index * xstep;
        for (int i = 0; i < sprites.size(); i++) {
            sprites.get(i).draw(x, y, pres / 16);
            x += xstep;
        }

        sprites.get(index).draw(w / 2 - 32, h / 2 - 32, 2);

        final String message = "Press SPACE to exit";
        Assets.font1.drawStringCentered(w / 2, h - 50, message);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        final int w = gc.getWidth(), h = gc.getHeight();

        final Input in = gc.getInput();

        if (in.isKeyPressed(Input.KEY_A)) index -= 1;
        if (in.isKeyPressed(Input.KEY_W)) index -= Assets.colors.size();
        if (in.isKeyPressed(Input.KEY_D)) index += 1;
        if (in.isKeyPressed(Input.KEY_S)) index += Assets.colors.size();

        if (in.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            final int x = in.getMouseX(), y = in.getMouseY();

            if (y < h - h / 4 + 26 && y > h - h / 4 - 26) {
                if (x > w / 2) index += 1;
                else index -= 1;
            }
        }

        index = Ma.limit(index, 0, sprites.size() - 1);

        if (in.isKeyPressed(Keys.Select) || in.isKeyPressed(Keys.Back)) {
            changeState(game, StateId.Menu);
        }
    }

}
