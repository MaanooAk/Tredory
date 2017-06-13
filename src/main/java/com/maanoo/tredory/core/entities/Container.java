package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.EntityState;
import com.maanoo.tredory.core.Point;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundleEntity;

/**
 *
 * @author Akritas
 */
public class Container extends Entity{

    public int tier;
    
    private static final int shieldsSumPerTier[] = new int[]{0, 0, 2, 8};
    
    public Container(Team team, int tier, Point location, float angle, SpriteBundleEntity sprites) {
        super(team, location, angle, sprites);
        
        this.tier = tier;
        
        shieldsSum = shieldsSumPerTier[tier];
        
    }

    @Override
    public void drops() {
        super.drops();
        
        Core.c.addItem(this, tier);
    }
    
    
    
}
