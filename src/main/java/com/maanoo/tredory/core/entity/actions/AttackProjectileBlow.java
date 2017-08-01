// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.actions;

import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.utils.Ma;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class AttackProjectileBlow extends AttackProjectileArc {

    private final float count_start;
    private final float count_diff;
    private final float angle_start;
    private final float angle_diff;

    public AttackProjectileBlow(Entity user, float charge_time, float recharge_time, float end_time,
            float cooldown_time, ProjectileType projectile, float speed, float count_start, float count_diff,
            float angle_start, float angle_diff) {
        super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count_start, angle_start);
        this.count_start = count_start;
        this.count_diff = count_diff;
        this.angle_start = angle_start;
        this.angle_diff = angle_diff;
    }

    @Override
    public float getStateTime(State state) {
        switch (state) {
        case Charging:
            return charge_time / user.getEffect().attackspeed.apply(1f);
        case Recharging:
            return Ma.max(recharge_time / user.getEffect().attackspeed.apply(1f) - perform_count * 10, 200);
        case Ending:
            return end_time / user.getEffect().attackspeed.apply(1f);
        case Cooling:
            return user.getEffect().cooldown.apply(cooldown_time);
        default:
            return 0;
        }
    }

    @Override
    public void perform() {

        this.count = Ma.limit(count_start + count_diff * perform_count, 1, 20);
        this.angle = Ma.limit(angle_start + angle_diff * perform_count, 0, 360);

        super.perform();
    }

    @Override
    public void end() {
        super.end();

        this.count = count_start;
    }

}
