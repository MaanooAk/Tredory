// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.utils.Point;

/**
 * @author MaanooAk
 */
public class Attack implements IUpdate {

    public Team team;
    public float attackspeed;

    private int activatein;
    private Core act_c;
    protected Entity ent;
    private Effect act_effect;

    public Attack(Team team, float attackspeed) {
        this.team = team;
        this.attackspeed = attackspeed;

        activatein = -1;
    }

    // TODO remove
    public final void perform(Core c, Entity ent) {
        perform(c, ent, new Effect());
    }

    public void perform(Core c, Entity ent, Effect effect) {
        activatein = (int) (450 / effect.attackspeed.apply(attackspeed));
        act_c = c;
        this.ent = ent;
        act_effect = effect;
    }

    // TODO remove
    public final void activate(Core c, Point p, float angle) {
        // TODO remove the object creation (create a static empty effect)
        activate(c, p, angle, new Effect());
    }

    public void activate(Core c, Point p, float angle, Effect effect) {

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
            if (activatein <= 0) activate(act_c, ent.location, ent.angle, act_effect);
        }
    }


}
