// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.entities.Altar;
import com.maanoo.tredory.core.entity.entities.Animal;
import com.maanoo.tredory.core.entity.entities.Container;
import com.maanoo.tredory.core.entity.entities.Portal;
import com.maanoo.tredory.core.utils.Colors;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.Assets;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * @author MaanooAk
 */
public class MapGen {

    private static int range(Map m, int min, int max, float ampl) {
        return Ma.round(m.ra.range(min, max) * ampl);
    }

    public static final void genFireplace(Map m, Spot center) {
        center.color = Colors.c202020;

        for (int i = 0; i < 20; i++) {
            final Point p = center.clone();

            p.add(new Point(m.ra.angle()).mul(m.ra.range(15, 60) + m.ra.range(15, 60)));

            m.things.add(new TerrainThing(p, Assets.terrain_steps.get()));
        }

        m.things.add(new TerrainThing(center.clone(), Assets.terrain_small.get(10)));

        int man = range(m, 2, 8, m.type.population);

        for (; man > 0; man--) {
            final Point p = center.clone();

            final float angle = m.ra.angle();
            p.add(new Point(angle).mul(20 + m.ra.range(15, 60) + m.ra.range(15, 60)));

            genManTier1(m, p, angle + 180);
        }

    }

    public static final void genGroup(Map m, Spot center) {
        center.color = Colors.c202020;

        for (int i = 0; i < 20; i++) {
            final Point p = center.clone();

            p.add(new Point(m.ra.angle()).mul(m.ra.range(15, 60) + m.ra.range(15, 60)));

            m.things.add(new TerrainThing(p, Assets.terrain_steps.get()));
        }

        int man = range(m, 4, 9, m.type.population);

        for (; man > 0; man--) {
            final Point p = center.clone();

            final float angle = m.ra.angle();
            p.add(new Point(angle).mul(20 + m.ra.range(15, 80) + m.ra.range(15, 80)));

            genManTier1(m, p, m.ra.angle());
        }

    }

    public static final void genSquad(Map m, Spot center) {
        center.color = Colors.c202020;

        final int man = range(m, 2, 8, m.type.population);

        final float pad = 64;

        final float angle = m.ra.angle();
        final Point pos = center.clone().sub(new Point(man * pad / 4, pad));

        final int tier = m.ra.range(1, 3);

        for (int i = 0; i < man - 1; i += 2) {
            Point p;

            p = pos.clone().rotate(angle, center);
            genManTier(tier, m, p, angle + 90);
            p = pos.clone().add(new Point(0, pad)).rotate(angle, center);
            genManTier(tier, m, p, angle + 90);

            pos.add(new Point(pad, 0));
        }
        if (man % 2 == 1) {
            pos.add(new Point(0, pad / 2)).rotate(angle, center);
            genManTier(tier, m, pos, angle + 90);
        }

    }

    public static final void genBoxguards(Map m, Spot center) {
        center.color = Colors.c404040;

        m.l.add(new Container(Team.Bad, 1, center.clone(), m.ra.angle(), new SpriteBundleEntity(Assets.box.get())));

        genCircleGuard(m, center, 70, 10, range(m, 3, 7, m.type.population), 1);
    }

    // === Big ===

    public static final void genBigBoxguards(Map m, Spot center) {

        genStepsAround(m, center, 40, 80, 300);

        final Container con = new Container(Team.Bad, 2, center.clone(), m.ra.angle(),
                new SpriteBundleEntity(Assets.boxg.get()));
        m.l.add(con);
        m.hotspots.add(con);

        genCircleGuard(m, center, 240, 30, range(m, 10, 15, m.type.population), 1);
        genCircleGuard(m, center, 120, 30, range(m, 4, 5, m.type.population), 2);

    }

    public static final void genBigAltarguards(Map m, Spot center) {

        genStepsAround(m, center, 40, 80, 300);

        final Altar con = new Altar(Team.Bad, center.clone(), m.ra.angle());
        m.l.add(con);
        m.hotspots.add(con);

        genCircleGuard(m, center, 240, 30, range(m, 10, 15, m.type.population), 1);
        genCircleGuard(m, center, 120, 30, range(m, 4, 5, m.type.population), 2);

    }

    // === Special ===

    public static final void genSpawn(Map m, Spot center) {

        genStepsAround(m, center, 10, 32, 200);

//         m.things.add(new TerrainGlyph(center.clone(), 0,
//         Assets.terrain_glyphs.get()));

//         m.l.add(new Container(Team.Bad, 2, center.clone(), m.ra.angle(), new SpriteBundleEntity(Assets.boxg.get())));

    }

    public static final void genPortal(Map m, Spot center) {

        genStepsAround(m, center, 50, 80, 300);

        final Portal po = new Portal(Team.Neutral, center.clone(), m.ra.angle(),
                new SpriteBundleEntity(Assets.portal.get()));
        m.l.add(po);
        m.hotspots.add(po);
    }

    // === Sub function ===

    public static final void genStepsAround(Map m, Spot center, int count, int min, int max) {
        min /= 2;
        max /= 2;

        for (int i = 0; i < count; i++) {
            final Point p = center.clone();

            p.add(new Point(m.ra.angle()).mul(m.ra.range(min, max) + m.ra.range(min, max)));

            m.things.add(new TerrainThing(p, Assets.terrain_steps.get()));
        }

    }

    public static final void genCircleGuard(Map m, Point center, int r, int rd, int count, int tier) {
        rd /= 2;

        final float toxo = 360f / count;
        final float toxooff = m.ra.range(0, toxo);
        for (int i = 0; i < count; i++) {
            final Point p = center.clone();

            final float angle = toxooff + i * toxo + toxo + m.ra.range(-10, 10);
            p.add(new Point(angle).mul(r + m.ra.range(-rd, rd) + m.ra.range(-rd, rd)));

            genManTier(tier, m, p, angle);
        }

    }

    public static final void genManTier(int tier, Map m, Point p, float angle) {
        switch (tier) {
        case 1:
            genManTier1(m, p, angle);
            return;
        case 2:
            genManTier2(m, p, angle);
            return;
        }
    }

    public static final void genManTier1(Map m, Point p, float angle) {

        switch (m.ra.range(2)) {
        case 0:
            m.l.add(new Animal(Team.Bad, p, angle, new SpriteBundleEntity(Assets.axeman.get()), 1, 1));
            return;
        case 1:
            m.l.add(new Animal(Team.Bad, p, angle, new SpriteBundleEntity(Assets.maceman.get()), 1, 1));
            return;
        }
    }

    public static final void genManTier2(Map m, Point p, float angle) {

        switch (m.ra.range(1)) {
        case 0:
            m.l.add(new Animal(Team.Bad, p, angle, new SpriteBundleEntity(Assets.swordman.get()), 1.2f, 1));
            return;
        }
    }

}
