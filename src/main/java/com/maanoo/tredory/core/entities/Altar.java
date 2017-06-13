package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.CoreDrops;
import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.EntityState;
import com.maanoo.tredory.core.Ma;
import com.maanoo.tredory.core.Point;
import com.maanoo.tredory.core.Ra;
import com.maanoo.tredory.core.Recipe;
import com.maanoo.tredory.core.Recipes;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.Assets;
import java.util.ArrayList;

/**
 * TODO activate somehow
 * @author Akritas
 */
public class Altar extends Entity { 

    public Altar(Team team, Point location, float angle) {
        super(team, location, angle, new SpriteBundleEntity(Assets.altar.get()));
        
        shieldsSum = 2;
        
        movable = false;
        activatable = true;
    }
    
    @Override
    public void activate() {
        super.activate();
        
        if(state != EntityState.Idle) return;
        
        Player p = Core.c.player;
       
        Recipe rec = Recipes.getRecipe(p.crystals, p.shields);
        
        if(rec != null) {
        
            for(ItemType i : rec.in.items) {
                p.giveItem(i);
            }
            for(ItemType i : rec.out.items) {
                CoreDrops.dropItem(Core.c, this, 0, 360, 0.2f, 0.25f, i);
            }
            
        }
        
        super.takeDamage();
    }
    
    @Override
    public void takeDamage() {
        
    }
        
}
