package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.EntityState;
import com.maanoo.tredory.core.Point;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundle;

/**
 *
 * @author Akritas
 */
public class Portal extends Entity {

    public Portal(Team team, Point location, float angle, SpriteBundle sprites) {
        super(team, location, angle, sprites);
        
        undead = true;
        stepable = true;
        
        state = EntityState.Special;
    }

    @Override
    public void update(int d) {
        super.update(d);
        
    }

    
    
    @Override
    public void activate() {
        super.activate();
        
        Core.c.requestNewMap();
    }
    
    
}
