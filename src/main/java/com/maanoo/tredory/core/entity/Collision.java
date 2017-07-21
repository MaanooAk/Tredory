// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import java.util.ArrayList;

import com.maanoo.tredory.core.utils.Ma;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Collision {

    public static int collision_checks = 0;
    public static int collision_detections = 0;

    public static void perform(ArrayList<Entity> l) {

        collision_checks = 0;
        collision_detections = 0;

        doublePass(l);

    }

//    private static void onePass(ArrayList<Entity> l) {
//
//        final int count = l.size();
//        for (int i1 = 0; i1 < count; i1++) {
//            final Entity e1 = l.get(i1);
//            if (e1.state == EntityState.Die) continue;
//
//            for (int i2 = i1 + 1; i2 < count; i2++) {
//                final Entity e2 = l.get(i2);
//                if (e2.state == EntityState.Die) continue;
//
//                // TODO remove when collision detection is not O(n^2)
//                if (e1.state == EntityState.Idle && e2.state == EntityState.Idle) continue;
//
//                final float r = e1.sizecol + e2.sizecol;
//                final float d = e1.location.distance(e2.location);
//
//                if (d < r) {
//                    e1.collide(e2);
//                    e2.collide(e1);
//
//                    if (e1.state == EntityState.Die) i2 = count;
//
//                    collision_detections += 1;
//                }
//
//                collision_checks += 1;
//            }
//        }
//
//    }

    private static final ArrayList<Entity> list = new ArrayList<>();

    private static void doublePass(ArrayList<Entity> l) {

        list.clear();

        { // find collisions

            // TODO use grid
            // TODO make parallel

            final int count = l.size();
            for (int i1 = 0; i1 < count; i1++) {
                final Entity e1 = l.get(i1);
                if (e1.state == EntityState.Die) continue;

                for (int i2 = i1 + 1; i2 < count; i2++) {
                    final Entity e2 = l.get(i2);
                    if (e2.state == EntityState.Die) continue;

                    // TODO remove when collision detection is not O(n^2)
                    if (e1.state == EntityState.Idle && e2.state == EntityState.Idle) continue;

                    final float r = Ma.pow2(e1.sizecol + e2.sizecol);
                    final float d = e1.location.distanceSquared(e2.location);

                    if (d < r) {
                        list.add(e1);
                        list.add(e2);

                        collision_detections += 1;
                    }

                    collision_checks += 1;
                }
            }
        }

        { // perform collision

            final int count = list.size();
            for (int i = 0; i < count; i += 2) {
                final Entity e1 = list.get(i);
                final Entity e2 = list.get(i + 1);

                if (e1.state == EntityState.Die || e2.state == EntityState.Die) continue;

                e1.collide(e2);
                e2.collide(e1);
            }

        }
    }

}
