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
import com.maanoo.tredory.core.entity.item.ItemTag;
import com.maanoo.tredory.core.entity.item.ItemType;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Str;
import com.maanoo.tredory.engine.Sprite;
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

        changeIndex(index);

    }

    // draw cache variables

    private Sprite body, projectile, items[];
    private boolean items_group[];
    private String name, current_bar;
    private int name_w, current_bar_w;

    private void changeIndex(int new_index) {

        index = new_index;

        final PlayerSetup ps = PlayerSetups.all[index];

        body = ps.body.getSprite(0, 0);
        projectile = ps.projectile.get().getSprite(0, 1);

        items = new Sprite[ps.items.length];
        items_group = new boolean[ps.items.length];

        ItemTag last_tag = ps.items[0].tag;
        for (int i = 0; i < ps.items.length; i++) {
            final ItemType ii = ps.items[i];

            items[i] = Assets.getItem(ii).get();

            // true if different group
            items_group[i] = ii.tag != last_tag;
            last_tag = ii.tag;
        }

        name = ps.info.name;
        name_w = Assets.font1.getWidth(name);
        current_bar = bar[index];
        current_bar_w = Assets.font1.getWidth(current_bar);

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

        final int w = gc.getWidth(), h = gc.getHeight();

        body.draw(w / 2 - 32, h / 2 - 32, 64, 64);

        projectile.draw(w / 4 - 32, h / 2 - 32, 64, 64);

        int iw = w * 3 / 4 - 16;
        int ih = h / 2 - 16;
        for (int i = 0; i < items.length; i++) {
            if (items_group[i]) {
                iw += 32;
                ih = h / 2 - 16;
            }
            items[i].draw(iw, ih, 32, 32);
            ih += 32;
        }

        Assets.font1.drawString(w / 2 - name_w / 2, 100, name);

        Assets.font1.drawString(w / 2 - current_bar_w / 2, h - 70, current_bar, Color.darkGray);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        // input

        final Input in = gc.getInput();

        if (in.isKeyPressed(Op.Keys.MoveL)) changeIndex(Ma.looparound(index - 1, count));
        if (in.isKeyPressed(Op.Keys.MoveR)) changeIndex(Ma.looparound(index + 1, count));
        if (in.isKeyPressed(Op.Keys.MoveU)) changeIndex(0);
        if (in.isKeyPressed(Op.Keys.MoveD)) changeIndex(count - 1);

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
