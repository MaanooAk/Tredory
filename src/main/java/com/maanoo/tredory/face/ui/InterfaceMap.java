// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Draws;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.Souls;
import com.maanoo.tredory.core.entity.entities.ItemType;
import com.maanoo.tredory.core.entity.entities.Items;
import com.maanoo.tredory.core.map.Map;
import com.maanoo.tredory.core.utils.Colors;
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
        drawCrystals(g, c.player.crystals);
        drawStones(g, c.player.stones, c.player.souls);
        drawCoins(g, c.player.coins);
        drawSpells(g);

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
                shields.get(i).sprites.idle.draw(x, h - 42, 32, 32);
                x += 18;
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
                crystals.get(i).sprites.idle.draw(x, h - 42 + y, 32, 32);
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
                stones.get(i).sprites.idle.draw(x, h - 42 + y - 52, 32, 32);
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

    private void drawSpells(Graphics g) {

        final int count = 7;
        final int wi = 16;

        int x = (int) (-count / 2f * (wi + 10));
        for (int i = 0; i < count; i++) {

            g.setColor(Colors.black75);
            g.fillOval(w / 2 + x, h - wi - 10, wi, wi);
            g.setColor(Color.darkGray);
            g.drawOval(w / 2 + x, h - wi - 10, wi, wi);

            g.setColor(Color.darkGray);
            g.fillArc(w / 2 + x, h - wi - 10, wi, wi, -180, -180);

            x += wi + 10;
        }
    }

}
