// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.Op.Keys;
import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.PlayerSetup;
import com.maanoo.tredory.core.PlayerSetups;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Str;
import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class StateGameSetup extends State {

    private int index;
    private int count;
    private String[] bar;

    public StateGameSetup() {
        super(StateId.GameSetup);
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // TODO Auto-generated method stub

        index = 0;

    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        super.enter(gc, game);

        gc.getInput().clearKeyPressedRecord();

        count = PlayerSetups.all.length;

        bar = new String[count];
        for (int i = 0; i < count; i++) {
            bar[i] = Str.create(Str.repeat("- ", i), "O", Str.repeat(" -", count - i - 1));
        }

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

        final int w = gc.getWidth(), h = gc.getHeight();

        final PlayerSetup ps = PlayerSetups.all[index];

        ps.body.getSprite(0, 0).draw(w / 2 - 32, h / 2 - 32, 64, 64);

        ps.projectile.get(0).getSprite(0, 1).draw(w / 4 - 32, h / 2 - 32, 64, 64);

        Assets.font1.drawString(w / 2 - Assets.font1.getWidth(ps.info.name) / 2, 100, ps.info.name);

        final String current_bar = bar[index];
        Assets.font1.drawString(w / 2 - Assets.font1.getWidth(current_bar) / 2, h - 70, current_bar, Color.darkGray);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        // input

        final Input in = gc.getInput();

        if (in.isKeyPressed(Op.Keys.MoveL)) index = Ma.looparound(index - 1, count);
        if (in.isKeyPressed(Op.Keys.MoveR)) index = Ma.looparound(index + 1, count);
        if (in.isKeyPressed(Op.Keys.MoveU)) index = 0;
        if (in.isKeyPressed(Op.Keys.MoveD)) index = count - 1;

        if (in.isKeyPressed(Keys.Select)) {

            Assets.trans.play();

            final Core c = new Core();
            Core.c = c;

            c.init(PlayerSetups.all[index]);

            changeState(game, StateId.Game);
        }

        if (in.isKeyPressed(Keys.Back)) {
            changeState(game, StateId.Menu);
        }

    }

}
