// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entities.Animal;
import com.maanoo.tredory.face.assets.Assets;

/**
 *
 * @author Akritas
 */
public class EntityBrain implements IUpdate {
    
    public final Animal ent;

    private float viewRange = 300;
    private float viewRangeMax = 900;
    
    private boolean attacked = false;
    
    private Entity target;
    
    public EntityBrain(Animal ent) {
        this.ent = ent;
        
    }

    @Override
    public void update(int d) {
        
        switch(ent.state) {
        case Idle:
            
            if(ent.alerted) {
                ent.alerted = false;
                target = Core.c.findClossest(ent, Team.Good, viewRangeMax);
            }else{
                target = Core.c.findClossest(ent, Team.Good, viewRange);                
            }
            
            if(target != null) {
                ent.state = EntityState.Move;
            }
            
            break;
        case Move:
            
            target = Core.c.findClossest(ent, Team.Good, viewRangeMax);            
            
            if(target != null) {

                Point vec = target.location.clone().sub(ent.location);

                if (vec.len() < ent.attack.range) {
                    ent.startAttack(ent.attack.getAttackspeed());
                } else {
                    ent.location.add(vec.norm().mul(ent.lspeed*d));
                }
                
                ent.angle = vec.angle();
            }else {
                ent.state = EntityState.Idle;
            }
            
            break;
        case Attack:
            
            if(ent.sprites.attack.getFrame() < 4) {
                attacked = false;
                
                target = Core.c.findClossest(ent, Team.Good, viewRangeMax);            

                if(target != null) {

                    Point vec = target.location.clone().sub(ent.location);

                    if(vec.len() > ent.attack.rangeMax) {
                        ent.stopAttack();
                    }

                    ent.angle = vec.angle();
                    //ent.location.add(vec.norm().mul(2));
                    
                }else {                    
                    ent.stopAttack();
                }
                
            }else{
                
                if(!attacked) {
                    attacked = true;
                    
                    Assets.hit.playAt(ent.location);
                    
                    target.takeDamage();
                }
                
            }
            
            break;
        }
        
    }
        
}
