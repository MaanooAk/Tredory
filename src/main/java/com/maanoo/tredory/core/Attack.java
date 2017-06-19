// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.utils.Point;

/**
 * @author MaanooAk
 */
public class Attack implements IUpdate {

    public final Team team;
    public final float attackspeed;

    private int activatein;
    private Core act_c;
    private Point act_p;
    private float act_angle;
    private Effect act_effect;

    public Attack(Team team, float attackspeed) {
        this.team = team;
        this.attackspeed = attackspeed;

        activatein = -1;
    }

    public final void perform(Core c, Point p, float angle) {
        perform(c, p, angle, new Effect());
    }

    public void perform(Core c, Point p, float angle, Effect effect) {
        activatein = (int) (450 / effect.attackspeed.apply(attackspeed));
        act_c = c;
        act_p = p;
        act_angle = angle;
        act_effect = effect;
    }

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

    @Override
    public void update(int d) {

        if (activatein > 0) {
            activatein -= d;
            if (activatein <= 0) activate(act_c, act_p, act_angle, act_effect);
        }
    }


}
