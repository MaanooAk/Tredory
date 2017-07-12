// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.brains;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Brain;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.entity.actions.AttackMelee;
import com.maanoo.tredory.core.entity.entities.Animal;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class AnimalBrain extends Brain {

    protected final Animal ent;

    protected float viewRange;
    protected float viewRangeMax;

    protected Entity target;

    public AnimalBrain(float intelligence, Animal ent) {
        super(intelligence);
        this.ent = ent;

        viewRange = 300;
        viewRangeMax = 900;

        target = null;
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
