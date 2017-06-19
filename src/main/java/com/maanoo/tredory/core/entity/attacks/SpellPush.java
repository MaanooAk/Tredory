// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpellPush extends Spell {

    public SpellPush(Team team, float attackspeed) {
        super(team, attackspeed);
    }

    @Override
    public void spell(Core c, Point p, float angle, Effect effect) {

        Point vec = Points.create();

        for (Entity i : c.findAll(ent, Team.Bad, 200)) {

            if (!i.movable) continue;

            vec.init(i.location).sub(ent.location);
            vec.mul(10000 / Ma.pow2(vec.len()));

            i.location.add(vec);

        }

        Points.dispose(vec);
    }
}
