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
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.Assets;

/**
 * @author MaanooAk
 */
public class MapGen {

    private static int range(int min, int max, float ampl) {
        return Ma.round(Ra.range(min, max) * ampl);
    }

    public static final void genFireplace(Map m, Spot center) {
        center.color = Colors.c202020;

        for (int i = 0; i < 20; i++) {
            Point p = center.clone();

            p.add(new Point(Ra.angle()).mul(Ra.range(15, 60) + Ra.range(15, 60)));

            m.things.add(new TerrainThing(p, Assets.terrain_steps.get()));
        }

        m.things.add(new TerrainThing(center.clone(), Assets.terrain_small.get(10)));

        int man = range(2, 8, m.type.population);

        for (; man > 0; man--) {
            Point p = center.clone();

            float angle = Ra.angle();
            p.add(new Point(angle).mul(20 + Ra.range(15, 60) + Ra.range(15, 60)));

            genManTier1(m, p, angle + 180);
        }

    }

    public static final void genGroup(Map m, Spot center) {
        center.color = Colors.c202020;

        for (int i = 0; i < 20; i++) {
            Point p = center.clone();

            p.add(new Point(Ra.angle()).mul(Ra.range(15, 60) + Ra.range(15, 60)));

            m.things.add(new TerrainThing(p, Assets.terrain_steps.get()));
        }

        int man = range(4, 9, m.type.population);

        for (; man > 0; man--) {
            Point p = center.clone();

            float angle = Ra.angle();
            p.add(new Point(angle).mul(20 + Ra.range(15, 80) + Ra.range(15, 80)));

            genManTier1(m, p, Ra.angle());
        }

    }

    public static final void genSquad(Map m, Spot center) {
        center.color = Colors.c202020;

        int man = range(2, 8, m.type.population);

        float pad = 64;

        float angle = Ra.angle();
        Point pos = center.clone().sub(new Point(man * pad / 4, pad));

        int tier = Ra.range(1, 3);

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

        m.l.add(new Container(Team.Bad, 1, center.clone(), Ra.angle(), new SpriteBundleEntity(Assets.box.get())));

        genCircleGuard(m, center, 70, 10, range(3, 7, m.type.population), 1);
    }

    // === Big ===

    public static final void genBigBoxguards(Map m, Spot center) {

        genStepsAround(m, center, 40, 80, 300);

        Container con = new Container(Team.Bad, 2, center.clone(), Ra.angle(), new SpriteBundleEntity(Assets.boxg.get()));
        m.l.add(con);
        m.hotspots.add(con);

        genCircleGuard(m, center, 240, 30, range(10, 15, m.type.population), 1);
        genCircleGuard(m, center, 120, 30, range(4, 5, m.type.population), 2);

    }

    public static final void genBigAltarguards(Map m, Spot center) {

        genStepsAround(m, center, 40, 80, 300);

        Altar con = new Altar(Team.Bad, center.clone(), Ra.angle());
        m.l.add(con);
        m.hotspots.add(con);

        genCircleGuard(m, center, 240, 30, range(10, 15, m.type.population), 1);
        genCircleGuard(m, center, 120, 30, range(4, 5, m.type.population), 2);

    }


    // === Special ===

    public static final void genSpawn(Map m, Spot center) {

        genStepsAround(m, center, 10, 32, 200);

        //m.things.add(new TerrainGlyph(center.clone(), 0, Assets.terrain_glyphs.get()));

    }

    public static final void genPortal(Map m, Spot center) {

        genStepsAround(m, center, 50, 80, 300);

        Portal po = new Portal(Team.Neutral, center.clone(), Ra.angle(), new SpriteBundleEntity(Assets.portal.get()));
        m.l.add(po);
        m.hotspots.add(po);
    }

    // === Sub function ===

    public static final void genStepsAround(Map m, Spot center, int count, int min, int max) {
        min /= 2;
        max /= 2;

        for (int i = 0; i < count; i++) {
            Point p = center.clone();

            p.add(new Point(Ra.angle()).mul(Ra.range(min, max) + Ra.range(min, max)));

            m.things.add(new TerrainThing(p, Assets.terrain_steps.get()));
        }

    }

    public static final void genCircleGuard(Map m, Point center, int r, int rd, int count, int tier) {
        rd /= 2;

        float toxo = 360f / count;
        float toxooff = Ra.range(0, toxo);
        for (int i = 0; i < count; i++) {
            Point p = center.clone();

            float angle = toxooff + i * toxo + toxo + Ra.range(-10, 10);
            p.add(new Point(angle).mul(r + Ra.range(-rd, rd) + Ra.range(-rd, rd)));

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

        switch (Ra.range(2)) {
        case 0:
            m.l.add(new Animal(Team.Bad, p, angle, new SpriteBundleEntity(Assets.axeman.get()), 1, 1));
            return;
        case 1:
            m.l.add(new Animal(Team.Bad, p, angle, new SpriteBundleEntity(Assets.maceman.get()), 1, 1));
            return;
        }
    }

    public static final void genManTier2(Map m, Point p, float angle) {

        switch (Ra.range(1)) {
        case 0:
            m.l.add(new Animal(Team.Bad, p, angle, new SpriteBundleEntity(Assets.swordman.get()), 1.2f, 1));
            return;
        }
    }

}
