// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

/**
 *
 * @author Akritas
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
    public void perform(Core c, Point p, float angle, Effect effect) {
        super.perform(c, p, angle, effect);
    }
    
    
}
