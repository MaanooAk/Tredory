// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.core.utils.Str;
import com.maanoo.tredory.engine.Logger;
import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class StateLoading extends State {

    // TODO convert to generic loading state

    private static final int MAX_THREADS = 2 * Runtime.getRuntime().availableProcessors();

    private static class LoadingStep {

        public final String name;
        public final Runnable r;
        public final boolean here;
        public final LoadingStep[] depens;

        public final float y;

        private boolean running;
        private boolean done;

        private long timeS;
        private long timeE;

        public LoadingStep(String name, Runnable r, boolean here, LoadingStep... depens) {
            super();
            this.name = name;
            this.r = r;
            this.here = here;
            this.depens = depens;

            running = false;
            done = false;

            y = Ra.range(1f);
        }

        public boolean isRunning() {
            return running;
        }

        public boolean isDone() {
            return done;
        }

        public boolean canRun() {
            for (final LoadingStep i : depens) {
                if (!i.isDone()) return false;
            }
            return !done && !running;
        }

        public void prerun() {
            running = true;
        }

        public void run() {

            Logger.log("Loading", name);
            timeS = System.nanoTime();

            r.run();

            timeE = System.nanoTime();
//            Logger.log("Loading", name + " DONE");

            done = true;
            running = false;
        }

    }

    private final ArrayList<LoadingStep> loadings;

    private final AtomicInteger callNext = new AtomicInteger(0);

    private boolean done;

    public StateLoading() {
        super(StateId.Loading);

        loadings = new ArrayList<>();
        done = false;

        final LoadingStep loadImages, loadAtlas, loadColors;

        loadings.add(loadImages = new LoadingStep("loadImages", Assets::loadImages, false));
        loadings.add(loadAtlas = new LoadingStep("loadAtlas", Assets::loadAtlas, true, loadImages));
        loadings.add(new LoadingStep("loadAtlasStore", Assets::loadAtlasStore, true, loadAtlas));
        loadings.add(loadColors = new LoadingStep("loadColors", Assets::loadColors, false));
        loadings.add(new LoadingStep("loadSprites", Assets::loadSprites, false, loadAtlas, loadColors));
        loadings.add(new LoadingStep("loadSounds", Assets::loadSounds, false));
        loadings.add(new LoadingStep("Pools", Pools::load, false));

    }

    private boolean nextLoading() {

        int running = 0;
        int pending = 0;
        for (final LoadingStep i : loadings) {

            if (i.isRunning()) running++;
            if (!i.isDone()) pending++;

            // not here only
            if (i.canRun() && !i.here && running < MAX_THREADS) {

                startLoading(i);
                if (i.isRunning()) running++;
            }
        }

        // here only
        for (final LoadingStep i : loadings) {
            if (i.canRun() && i.here) {
                startLoading(i);
                break;
            }
        }

        return pending == 0;
    }

    private void startLoading(final LoadingStep ls) {

        if (ls.here) {

            ls.prerun();
            ls.run();

            callNext.getAndIncrement();

        } else {

            ls.prerun();
            new Thread() {

                @Override
                public void run() {

                    ls.run();

                    callNext.getAndIncrement();
                }

            }.start();

        }
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        callNext.getAndIncrement();

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

        while (callNext.get() > 0) {
            callNext.getAndDecrement();

            if (nextLoading()) {

                done(gc, game);
            }
        }
    }

    public void done(GameContainer gc, StateBasedGame game) {
        if (done) return;
        done = true;

        gc.setDefaultFont(Assets.font1);

        try {
            gc.setMouseCursor(Assets.cursor.getImage(0, 0).getScaledCopy(2), 0, 0);
        } catch (final SlickException e) {
            e.printStackTrace();
        }

        changeState(game, StateId.Menu);

        // debug
        Logger.log("Loading", "Loading tasks time diagram");
        long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
        for (final LoadingStep i : loadings) {
            if (i.timeS < min) min = i.timeS;
            if (i.timeE > max) max = i.timeE;
        }
        final long dis = max - min;
        final long chars = 120;
        for (final LoadingStep i : loadings) {
            System.out.println(Str.repeat(" ", (int) (chars * (i.timeS - min) / dis)) + "|"
                    + Str.repeat("-", (int) (chars * (i.timeE - i.timeS) / dis)) + "|" + i.name);
        }

    }

}
