// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Attack;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.utils.Point;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Spell extends Attack {

    public Spell(Team team, float attackspeed) {
        super(team, attackspeed);
    }

    @Override
    public void activate(Core c, Point p, float angle, Effect effect) {
        super.activate(c, p, angle, effect);

        spell(c, p, angle, effect);
    }

    public abstract void spell(Core c, Point p, float angle, Effect effect);

}
