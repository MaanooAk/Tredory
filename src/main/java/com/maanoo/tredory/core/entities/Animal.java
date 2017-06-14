// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.AttackMelee;
import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.EntityBrain;
import com.maanoo.tredory.core.Point;
import com.maanoo.tredory.core.Stats;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundleEntity;
import java.util.ArrayList;

/**
 *
 * @author Akritas
 */
public class Animal extends Entity {
    
    public final AttackMelee attack;

    private final float lspeed_base = 0.2f;
    public float lspeed;
    
    public final EntityBrain brain;
    
    public Animal(Team team, Point location, float angle, SpriteBundleEntity sprites, float atackSpeedMul, float speedMul) {
        super(team, location, angle, sprites);
        
        attack = new AttackMelee(Team.Bad, atackSpeedMul, 50, 70);
        
        lspeed = lspeed_base*speedMul;
        
        brain = new EntityBrain(this);
    }

    @Override
    public void update(int d) {
        super.update(d);
        
        brain.update(d);
        
    }

    @Override
    public void drops() {
        super.drops();
        
        Core.c.addItem(this, 0);
    }

    
    
    @Override
    public void takeDamage() {
        super.takeDamage();
        
        ArrayList<Entity> l = Core.c.findAll(this, team, 250);
        for(Entity i : l) {
            i.alerted = true;
        }
    }

    @Override
    public void die() {
        super.die();
        
        if(team != Team.Good) Stats.addKilled(this);
    }
    
    
    
}
