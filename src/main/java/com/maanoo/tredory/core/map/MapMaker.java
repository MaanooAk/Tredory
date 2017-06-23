// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import org.newdawn.slick.util.Log;

import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.assets.Assets;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class MapMaker {

    private static Point basicSize = new Point(4000, 4000);

    public static Map make() {

        // choose a map type at random
        MapType type = MapType.Plain;
        if (Ra.chance(0.2f)) {
            type = MapType.Altar;
        }
        if (Ra.chance(0.2f)) {
            type = MapType.Overpop;
        }

        return make(basicSize, type);
    }

    public static Map make(Point size, MapType type) {

        Log.info("Map making (" + type + ")");

        Map map = new Map(size);
        map.type = type;

        map.bigs = type.bigs;

        int count = createSpots(map, 0.6f, map.bigs);

        map.spawn.init(map.spots.get(0).x, map.spots.get(0).y);

        for (int i = 0; i < 20; i++) {
            map.things.add(new TerrainThingAngled(
                    new Point(Ra.range(32, (int) size.x - 32), Ra.range(32, (int) size.y - 32)),
                    Ra.angle(), Assets.terrain_small.get(Ra.range(0, 2))));
        }

        MapGen.genSpawn(map, map.spots.get(0));

        MapGen.genBigBoxguards(map, map.spots.get(2));
        if (map.bigs > 1) MapGen.genBigAltarguards(map, map.spots.get(3));

        MapGen.genPortal(map, map.spots.get(1));

        int count_boxes = (int) (count * 0.25f);
        for (int i = 0; i < count_boxes; i++) {
            MapGen.genBoxguards(map, emptyPoint(map));
        }

        int count_nonbox = (int) (count * 0.50f);
        for (int i = 0; i < count_nonbox; i++) {
            if (Ra.chance(1f / 3f)) {
                MapGen.genSquad(map, emptyPoint(map));
            } else if (Ra.chance(1f / 2f)) {
                MapGen.genGroup(map, emptyPoint(map));
            } else {
                MapGen.genFireplace(map, emptyPoint(map));
            }
        }

        for (int i = 0; i < 20; i++) {
            //c.l.add(new Container(Team.Bad, emptyPoint(), 0, new SpriteBundleEntity(Assets.box.get())));
        }
        for (int i = 0; i < 200; i++) {
            //MapGen.genManTier(1, map, new Point(Ra.range(32, (int) size.x - 32), Ra.range(32, (int) size.y - 32)), Ra.angle());
        }

        return map;
    }

    private static int createSpots(Map map, float area, int bigs) {
        map.spots.clear();

        area = (map.size.x * map.size.y * area);

        area -= addSpot(map, 500); // spawn
        area -= addSpot(map, 300); // portal
        for (int i = 0; i < bigs; i++) area -= addSpot(map, 400); // big

        map.spots_index = map.spots.size();

        int r = 250;
        int count = (int) (area / (Math.PI * r * r));

        for (int i = 0; i < count; i++) {
            map.spots.add(new Spot(Ra.range(r, (int) map.size.x - r), Ra.range(r, (int) map.size.y - r), r));
        }

        for (int i = 0; i < 150; i++) {
            if (!fitSpots(map)) break;
        }

        return count;
    }

    private static float addSpot(Map map, float r) {
        map.spots.add(new Spot(Ra.range(r, (int) map.size.x - r), Ra.range(r, (int) map.size.y - r), r));
        return Ma.PI * r * r;
    }

    private static boolean fitSpots(Map map) {
        boolean changed = false;
        Point vec = new Point();

        int count = map.spots.size();
        for (int i1 = 0; i1 < count; i1++) {
            Spot p1 = map.spots.get(i1);

            for (int i2 = i1 + 1; i2 < count; i2++) {

                float r1 = map.spots.get(i1).r;
                float r2 = map.spots.get(i2).r;

                vec.init( map.spots.get(i1)).sub(map.spots.get(i2));
                float inside = vec.len() / (r1 + r2);

                if (inside <= 1) {

                    if (inside < 0.5f) {
                        vec.norm().mul((r1 + r2) / 2);
                    }

                    map.spots.get(i1).add(vec.mul(0.4f * (1.01f - inside)));
                    map.spots.get(i2).add(vec.mul(-1));
                    changed = true;
                }

            }

            if (p1.x < p1.r) p1.x = p1.r;
            if (p1.y < p1.r) p1.y = p1.r;
            if (p1.x > map.size.x - p1.r) p1.x = map.size.x - p1.r;
            if (p1.y > map.size.y - p1.r) p1.y = map.size.y - p1.r;

        }

        return changed;
    }

    private static Spot emptyPoint(Map map) {
        //return new Point(Ra.range(32, (int)size.x-32), Ra.range(32, (int)size.y-32));
        return map.spots.get(map.spots_index++);
    }

}
