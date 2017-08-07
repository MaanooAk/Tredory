// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.SpriteBundleEntityBasic;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class HomingProjectile extends Projectile {

    protected float angleSpeed;
    protected Entity target;

    public HomingProjectile() {}

    public HomingProjectile init(Team team, Point location, float angle, SpriteBundleEntityBasic sprites, float speed,
            float rota, float charge_time, float lifetime, float angleSpeed, Entity target) {
        super.init(team, location, angle, sprites, speed, rota, charge_time, lifetime);
        this.angleSpeed = angleSpeed;
        this.target = target;
        return this;
    }

    @Override
    public void update(int d) {
        super.update(d);

        if (target != null) {

            if (!target.dead) {

                if (state == EntityState.Move) {

                    final float nangle = target.location.distanceAngle(this.location);
                    changeAngle(nangle, angleSpeed, d);
                }

            } else {
                target = null;
            }

        } else {
            target = Core.c.findClossestEnemy(this, team);
        }
    }

}
