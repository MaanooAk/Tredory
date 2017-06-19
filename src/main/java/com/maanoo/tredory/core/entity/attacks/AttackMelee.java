// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Attack;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.utils.Point;

/**
 * @author MaanooAk
 */
public class AttackMelee extends Attack {

    public final float range;
    public final float rangeMax;

    public AttackMelee(Team team, float attackspeed, float range, float rangeMax) {
        super(team, attackspeed);

        this.range = range;
        this.rangeMax = rangeMax;
    }

    @Override
    public void perform(Core c, Entity ent, Effect effect) {
        super.perform(c, ent, effect);
    }


}
