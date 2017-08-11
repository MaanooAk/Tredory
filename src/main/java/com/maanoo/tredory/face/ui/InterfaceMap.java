// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Draws;
import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Action.State;
import com.maanoo.tredory.core.entity.ActionsPlayer;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.Souls;
import com.maanoo.tredory.core.entity.item.ItemType;
import com.maanoo.tredory.core.entity.item.Items;
import com.maanoo.tredory.core.map.Map;
import com.maanoo.tredory.core.quest.QuestProgress;
import com.maanoo.tredory.core.quest.QuestsTracker;
import com.maanoo.tredory.core.utils.Colors;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class InterfaceMap extends Interface {

    private float zoom;

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    @Override
    public void draw(Graphics g) {

        final Core c = Core.c;
        final Point camera = c.camera;
        final Map map = c.map;

        // g.setAntiAlias(true); // TODO can we support aa ?

        g.pushTransform();
        g.translate(w / 2, h / 2);
        g.scale(zoom, zoom);
        g.translate(-camera.x, -camera.y);

        Draws.set(g, camera.x - w / 2 / zoom, camera.y - h / 2 / zoom, w / zoom, h / zoom);

        c.map.pushDraw();

        for (final Entity i : c.entities.getAll()) {
            i.pushDraw();
        }

        c.player.pushDraw();

        Draws.drawAll();

        g.popTransform();

        drawMinimap(g, map, camera);
        drawShields(g, c.player.shields);
        drawUniques(g, c.player.uniques);
        drawCrystals(g, c.player.crystals);
        drawStones(g, c.player.stones, c.player.souls);
        drawCoins(g, c.player.coins);
        drawSpells(g, c.player.getActions());
        drawQuests(g, c.player.quests);

    }

    private void drawMinimap(Graphics g, Map map, Point camera) {

        g.setColor(Colors.black75);
        g.fillRect(-1, -1, 10 + 96, 10 + 96);
        g.setColor(Color.darkGray);
        g.drawRect(-1, -1, 10 + 96, 10 + 96);

        g.pushTransform();
        g.translate(5 + 48, 5 + 48);

        {
            g.pushTransform();
            g.scale(0.02f, 0.02f);
            g.translate(-camera.x, -camera.y);

            map.drawMini(g, camera, 34f / 0.02f);

            g.popTransform();
        }

        g.setColor(Color.white);
        g.fillOval(-1, 1, 2, 2);

        g.rotate(0, 0, Core.c.arrow_angle);

        Assets.effect_arrow.draw(-48, -48, 96, 96, Color.gray);

        g.popTransform();

    }

    private void drawShields(Graphics g, Items shields) {

        if (shields.size() > 0) {
            g.setColor(Colors.black75);
            g.fillRect(-1, h - 52, 33 + 18 * shields.max + 1, 52);
            g.setColor(Color.darkGray);
            g.drawRect(-1, h - 52, 33 + 18 * shields.max + 1, 52);

            int x = 10;
            for (int i = shields.size() - 1; i >= 0; i--) {
                shields.get(i).sprites.idle.getSprite().draw(x, h - 42, 32, 32);
                x += 18;
            }
        }
    }

    private void drawUniques(Graphics g, Items uniques) {

        final int step = 32;

        if (uniques.size() > 0) {

            g.setColor(Colors.black75);
            g.fillRect(-1, h - 52 - 52, 20 + step * uniques.size() + 1, 52);
            g.setColor(Color.darkGray);
            g.drawRect(-1, h - 52 - 52, 20 + step * uniques.size() + 1, 52);

            int x = 10;
            for (int i = uniques.size() - 1; i >= 0; i--) {
                uniques.get(i).getAnimation().getSprite().draw(x, h - 42 - 52, 32, 32);
                x += step;
            }
        }
    }

    private void drawCrystals(Graphics g, Items crystals) {

        final int count = crystals.size();
        if (count > 0) {

            final int pad = 16 - 4;
            final int wi = 20 + count * pad;

            g.setColor(Colors.black75);
            g.fillRect(w - wi, h - 52, wi, 52);
            g.setColor(Color.darkGray);
            g.drawRect(w - wi, h - 52, wi, 52);

            int x = w - wi + 10 - pad + 2, y = 2;
            if (count % 2 == 0) y *= -1;
            for (int i = 0; i < crystals.size(); i++) {
                crystals.get(i).sprites.idle.getSprite().draw(x, h - 42 + y, 32, 32);
                x += pad;
                y *= -1;
            }
        }
    }

    private void drawStones(Graphics g, Items stones, Souls souls) {

        final int count = stones.size();
        if (count > 0) {

            final int pad = 16 - 4 + 6;
            final int wi = 20 + count * pad;

            g.setColor(Colors.black75);
            g.fillRect(w - wi, h - 52 * 2, wi, 52);
            g.setColor(Color.darkGray);
            g.drawRect(w - wi, h - 52 * 2, wi, 52);

            int x = w - wi + 10 - pad + 12, y = 2;
            if (count % 2 == 0) y *= -1;
            for (int i = 0; i < stones.size(); i++) {
                stones.get(i).sprites.idle.getSprite().draw(x, h - 42 + y - 52, 32, 32);
                x += pad;
                y *= -1;
            }

            // souls

            final int barh = 5;

            g.setColor(Color.darkGray);
            g.fillRect(w - wi, h - 52 * 2 - barh, wi * souls.getSouls() / souls.getCapacity(), barh);
            g.setColor(Color.darkGray);
            g.drawRect(w - wi, h - 52 * 2 - barh, wi, barh);
        }
    }

    private void drawCoins(Graphics g, int coins) {

        if (coins > 0) {
            final int groups = coins < 400 ? 5 : coins < 800 ? 10 : coins < 2000 ? 20 : 30;

            int wi = 16 * (coins / (groups * 10)) + 16;
            wi += groups <= 10 ? (10 / groups) * 16 : 16;

            g.setColor(Colors.black75);
            g.fillRect(w - wi - 10, 0, wi + 20, 28 + 3 * groups);
            g.setColor(Color.darkGray);
            g.drawRect(w - wi - 10, -1, wi + 20 + 1, 28 + 3 * groups + 1);

            int x = w - 10 - 16;
            int y = 0;
            int left = coins;
            while (left >= 10) {
                Assets.getItem(ItemType.Gold).get().draw(x, y, 2);
                left -= 10;
                y = (y + 3) % (3 * groups);
                if (y == 0) x -= 16;
            }

            if (y != 0) {
                y = 0;
                x -= 16;
            }
            while (left >= 1) {
                Assets.getItem(ItemType.Copper).get().draw(x, y, 2);
                left -= 1;
                y = (y + 3) % (3 * groups);
                if (y == 0) x -= 16;
            }
        }

    }

    private void drawSpells(Graphics g, ActionsPlayer actions) {

        final int count_prime = 5;
        final int count = count_prime + 7;
        final int wi = 16;
        final int down_offset = 8;
        final int down_space = 14;

        int x = w / 2 - count * (wi + 10) / 2;
        for (int i = 0; i < count; i++) {

            if (i == count_prime) {
                x += 5;
            }

            Action action;
            if (i <= count_prime) {
                action = actions.get(0, i);
            } else {
                action = actions.get(1, i - count_prime);
            }

            if (action.isCooling()) {

                final float progress = action.getStateProgress();
                final float time = action.getStateTime();
                final float time_left = action.getStateTimeLeft();
                final float time_passed = time - time_left;

                float offset = 0;
                if (time_passed < 300f) {
                    offset = down_offset * Ma.pow2(1 - time_passed / 300f);
                } else if (time_left < 300f) {
                    offset = down_offset * Ma.pow2(1 - time_left / 300f);
                }

                Assets.action.get(0, progress).draw(x - 8, h - wi - down_space - offset, 1);

            } else if (action.canStart()) {
                Assets.action.get().draw(x - 8, h - wi - down_offset - down_space, 1);
            } else if (action.isActive()) {

                float progress = action.getStateProgress() / 2;

                if (action.isRecharge()) {
                    progress = 0.5f;
                } else if (action.getState() == State.Ending) {
                    progress += 0.5;
                }

                Assets.action.get(1, progress).draw(x - 8, h - wi - down_offset - down_space, 1);
            }

            x += wi + 10;
        }
    }

    private void drawQuests(Graphics g, QuestsTracker quests) {

        final int barh = 6;
        final int barw = 30;

        for (int i = 0; i < quests.quests.size(); i++) {
            final QuestProgress q = quests.quests.get(i);

            if (!q.isActive()) continue;

            g.setColor(Color.darkGray);
            g.fillRect(4, h / 2 + (barh + 10) * i, barw * q.getProgress(), barh);
            if (q.quest.hasDuration()) {
                g.setColor(Color.gray);
                g.drawRect(4, h / 2 + (barh + 10) * i, barw * (1f - q.getTimeProgress()), barh);
            }
            g.setColor(Color.darkGray);
            g.drawRect(4, h / 2 + (barh + 10) * i, barw, barh);
        }

    }

}
