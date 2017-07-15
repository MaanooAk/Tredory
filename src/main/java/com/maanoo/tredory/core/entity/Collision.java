// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.utils.Point;

import java.util.ArrayList;


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

        int count = l.size();
        for (int i1 = 0; i1 < count; i1++) {
            Entity e1 = l.get(i1);
            if (e1.state == EntityState.Die) continue;

            for (int i2 = i1 + 1; i2 < count; i2++) {
                Entity e2 = l.get(i2);
                if (e2.state == EntityState.Die) continue;

                // TODO remove when collision detection is not O(n^2)
                if (e1.state == EntityState.Idle && e2.state == EntityState.Idle) continue;

                float r1 = e1.sizecol;
                float r2 = e2.sizecol;
                Point c1 = e1.location;
                Point c2 = e2.location;

                if (c1.distance(c2) < (r1 + r2) * 1.0f) {
                    e1.collide(e2);
                    e2.collide(e1);

                    if (e1.state == EntityState.Die) i2 = count;

                    collision_detections += 1;
                }

                collision_checks += 1;
            }
        }

    }

}
