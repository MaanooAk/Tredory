// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.actions.AttackMelee;
import com.maanoo.tredory.core.entity.entities.Animal;


/**
 * @author MaanooAk
 */
public class Brain implements IUpdate {

    // TODO create inheriting structrue
    // TODO add intelligence parameter

    public final Animal ent;

    private float viewRange = 300;
    private float viewRangeMax = 900;

    private Entity target;

    public Brain(Animal ent) {
        this.ent = ent;

    }

    @Override
    public void update(int d) {

        switch (ent.state) {
        case Idle:

            if (ent.alerted) {
                ent.alerted = false;
                target = Core.c.findClossest(ent, Team.Good, viewRangeMax);
            } else {
                target = Core.c.findClossest(ent, Team.Good, viewRange);
            }

            if (target != null) {
                ent.changeState(EntityState.Move);
            }

            break;
        case Move:

            target = Core.c.findClossest(ent, Team.Good, viewRangeMax);

            if (target != null) {

                final float distance = target.location.distance(ent.location);
                final float angle = target.location.distanceAngle(ent.location);

                if (distance < ((AttackMelee) ent.actions.get()).range) { // TODO move logic inside AttackMelee
                    ent.startAttack(ent.actions.get());
                } else {
                    ent.location.addAngled(angle, ent.lspeed * d);
                }

                ent.angle = angle;

            } else {
                ent.changeState(EntityState.Idle);
            }

            break;
        default:
            break;
        }

    }

}
