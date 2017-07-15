// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.engine.Logger;
import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class StateLoading extends State {

    // TODO convert to generic loading state

    private static class LoadingStep {

        public final String name;
        public final Runnable r;
        public final boolean here;

        public boolean running;
        public boolean done;

        public float y;

        public LoadingStep(String name, Runnable r, boolean here) {
            super();
            this.name = name;
            this.r = r;
            this.here = here;

            running = false;
            done = false;

            y = Ra.range(1f);
        }

        public void run() {
            running = true;
            r.run();
            done = true;
            running = false;
        }

    }

    private final ArrayList<LoadingStep> loadings;

    private final Object lock = new Object();
    private float loadingProgress;

    private Boolean callNext = false;

    public StateLoading() {
        super(StateId.Loading);

        loadings = new ArrayList<>();

        loadings.add(new LoadingStep("Load1", Assets::load1, false));
        loadings.add(new LoadingStep("Load2", Assets::load2, true));
        loadings.add(new LoadingStep("Load3", Assets::load3, false));
        loadings.add(new LoadingStep("Pools", Pools::load, false));

    }

    private boolean nextLoading() {

        LoadingStep ls = null;
        for (int i = 0; i < loadings.size(); i++) {
            if (!loadings.get(i).done) {
                ls = loadings.get(i);
                break;
            }
        }

        if (ls == null) {
            return false;
        }

        Logger.log("Loading", ls.name);

        startLoading(ls);

        return true;
    }

    private void startLoading(LoadingStep ls) {

        if (ls.here) {

            ls.run();

            synchronized (callNext) {
                callNext = true;
            }

        } else {

            final LoadingStep final_ls = ls;

            new Thread() {

                @Override
                public void run() {

                    final_ls.run();

                    synchronized (callNext) {
                        callNext = true;
                    }
                }
            }.start();

        }
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        callNext = true;

    }

    private boolean toggle = false;

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

        final int w = gc.getWidth();
        final int h = gc.getHeight();

        g.setColor(Color.white);

        final int xStep = w / loadings.size();
        int x = xStep / 2;

        for (final LoadingStep i : loadings) {

            g.setColor(i.done ? Color.black : i.running ? Color.gray : Color.white);
            g.fillRect(x, h / 4 + i.y * h / 2, 4, 4);

            x += xStep;
        }

    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        float progress;
        synchronized (lock) {
            progress = loadingProgress;
        }

        boolean call;
        synchronized (callNext) {
            call = callNext;
            if (callNext) {
                callNext = false;
            }
        }

        if (call) {

            if (!nextLoading()) {

                gc.setDefaultFont(Assets.font1);

                try {
                    gc.setMouseCursor(Assets.cursor.getImage(0, 0).getScaledCopy(2), 0, 0);
                } catch (final SlickException e) {
                    e.printStackTrace();
                }

                changeState(game, StateId.Menu);
            }
        }
    }

}
