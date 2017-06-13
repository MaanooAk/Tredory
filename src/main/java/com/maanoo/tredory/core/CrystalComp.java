package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entities.Item;
import com.maanoo.tredory.core.entities.ItemType;
import java.util.ArrayList;

/**
 *
 * @author Akritas
 */
public final class CrystalComp {

    public final int count[];
    
    public Effect effect;

    public CrystalComp(int c1, int c2, int c3) {
        count = new int[] {c1, c2, c3};
        
        calcEffect();
    }
    
    public void update(ArrayList<Item>... items) {
        
        for(int i=0; i<count.length; i++) count[i] = 0;
        
        for(ArrayList<Item> iitems : items) for(Item i : iitems) {
            switch (i.type) {
            case Crystal1:
                count[0] += 1;
                break;
            case Crystal2:
                count[1] += 1;
                break;
            case Crystal3:
                count[2] += 1;
                break;            
            case Shield4:
                count[0] += 1;
                count[1] += 1;
                count[2] += 1;
                break;            
            }
        }
        
        calcEffect();
    }
    
    private void calcEffect() {
        effect = new Effect();
        effect.shields.add = count[0] * 0.5f;
        effect.speed.add = count[1] * 0.03f;
        effect.attackspeed.mul = count[2] * 0.2f;
    }
}
