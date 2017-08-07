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
public class AttackProjectileSigleton extends AttackProjectileArc {

    public AttackProjectileSigleton(Entity user, float charge_time, float recharge_time, float end_time,
            float cooldown_time, ProjectileType projectile, float speed, float count, float angle,
            int max_perform_count) {
        super(user, charge_time, recharge_time, end_time, cooldown_time, projectile, speed, count, angle);

        this.max_perform_count = max_perform_count;

    }

    @Override
    public int getAnimationFrame() {
        switch (state) {
        case Charging:
            return Ma.limit((int) (4 * (1f - time_left / state_time)), 0, 3);
        case Ending:
            return Ma.limit((int) (4 + 2 * (1f - time_left / state_time)), 4, 5);
        case Recharging:
            return 3;
        default:
            return 0;
        }
    }

}
