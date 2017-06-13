package com.maanoo.tredory.core;

import java.util.ArrayList;
import org.newdawn.slick.Image;

/**
 *
 * @author Akritas
 */
public class Stats {
    
    public static int kills;
    public static int maps;
    
    public static ArrayList<Image> killed;
    
    public static void reset() {
        kills = 0;
        maps = 0;
        
        killed = new ArrayList<>();
    }
    
    public static void addKilled(Entity ent) {
        kills += 1;
        killed.add(ent.sprites.getStaticImage());
    }
    
    public static void exitMap(Map m) {
        maps += 1;
    }
}
