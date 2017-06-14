// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.Point;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.face.SpriteBundleStatic;
import com.maanoo.tredory.face.assets.Assets;

/**
 *
 * @author Akritas
 */
public class Item extends Entity implements Comparable<Item>{
    
    public final ItemType type;
    public final ItemTag tag;
    
    public float speedm;
    
    private int unpicable;
    
    public Item(ItemType type, Point location) {
        super(Team.Neutral, location, 180, new SpriteBundleStatic(Assets.getItem(type).get()));
        
        this.type = type;
        this.speedm = 0;
        
        tag = ItemType.getTag(type);
        
        undead = true;
        pickable = true;
        size = 16;
        sizecol = 16;
        
        unpicable = 0;
    }

    public void push(float angle, float speed) {
        
        this.speedm = speed;        
        this.speed = new Point(angle);
    }
    
    public void unpicablify(int duration) {
        pickable = false;
        unpicable = duration;
    }
    
    @Override
    public void update(int d) {
        super.update(d); 
        
        if(speedm > 0) {
            location.add(speed.clone().mul(speedm*d));
            speedm -= d/500f;
        }
        
        if(unpicable > 0) {
            unpicable -= d;
            if(unpicable <= 0) pickable = true;
        }
    }

    @Override
    public int compareTo(Item t) {
        return type.ordinal()-t.type.ordinal();
    }
    
}
