// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.utils.Point;

/**
 * The base class for attacks
 * 
 * Attack life cycle:
 * - start
 * - perform
 * - end
 * 
 * @author MaanooAk
 */
public class AttackOld implements IUpdate {

    public Team team;
    public float attackspeed;

    private int activatein;
    private Core act_c;
    protected Entity ent;
    private Effect act_effect;

    public AttackOld(Team team, float attackspeed) {
        this.team = team;
        this.attackspeed = attackspeed;

        activatein = -1;
    }

    public void start(Entity ent, Effect effect) {
        activatein = (int) (450 / effect.attackspeed.apply(attackspeed));
        this.ent = ent;
        act_effect = effect;
    }

    public void activate(Point p, float angle, Effect effect) {

    }

    public final float getAttackspeed() {
        return attackspeed;
    }

    public final float getAttackspeed(Effect e) {
        return e.attackspeed.apply(attackspeed);
    }

    public boolean isActive() {
        return activatein > 0;
    }

    @Override
    public void update(int d) {

        if (activatein > 0) {
            activatein -= d;
            if (activatein <= 0) activate(ent.location, ent.angle, act_effect);
        }
    }


}
