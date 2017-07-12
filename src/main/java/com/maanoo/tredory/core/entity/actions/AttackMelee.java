// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.face.assets.Assets;


/**
 * @author MaanooAk
 */
public class AttackMelee extends Attack {

    public final float range;
    public final float rangeMax;

    private Entity target;

    public AttackMelee(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time,
            float range, float rangeMax) {
        super(user, charge_time, recharge_time, end_time, cooldown_time);
        this.range = range;
        this.rangeMax = rangeMax;
    }

    @Override
    public void start() {
        super.start();

        target = Core.c.findClossest(user, Team.Good); // TODO revisit

        if (target == null) {
            stop();
        }
    }

    @Override
    public void update(int d) {
        super.update(d);

        switch (state) {
        case Charging:
        case Recharging:

            if (target.dead) {
                stop();
            }

            final float distance = target.location.distance(user.location);
            final float angle = target.location.distanceAngle(user.location);

            if (distance > rangeMax) {
                stop();

//            } else if (distance < range) {
//                // TODO handle

            } else {

                user.angle = angle;
            }

            break;
        default:
            break;
        }

    }

    @Override
    public void perform() {

        Assets.hit.playAt(target.location);

        target.takeDamage();
    }

}
