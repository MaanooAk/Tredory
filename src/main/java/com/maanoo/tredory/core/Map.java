// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.face.assets.Assets;
import java.util.ArrayList;
import java.util.Iterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Akritas
 */
public class Map implements IUpdate, IDraw {

    public final static class TerrainThing {
        public final Point p;
        public final float angle;
        public final Image img;
        public final int w;
        public final int h;

        public TerrainThing(Point p, float angle, Image img) {
            this.p = p;
            this.angle = angle;
            this.img = img;
            
            w = img.getWidth();
            h = img.getHeight();
            
            p.intify();
        } 
    }
    
    public final static class Spot extends Point {        
        public final float r;
        public Color color = Color.darkGray;

        public Spot(float x, float y, float r) {
            super(x, y);
            this.r = r;
        }
    }
    
    public final Point size;
    
    public final Point spawn;
    public final ArrayList<TerrainThing> things;
    public final ArrayList<Entity> l;
    
    public int bigs;
    public final ArrayList<Spot> spots;
    public int spots_index = 0;
    
    public final ArrayList<Entity> hotspots;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public Map(Point size) {
        this.size = size;
        
        things = new ArrayList<>();
        l = new ArrayList<>();
        
        hotspots = new ArrayList<>();
        
        spots = new ArrayList<>();
        
        bigs = 1;
        if(Ra.chance(0.2f)) bigs += 1;
        
        int count = createSpots(0.6f, bigs);        
        
        spawn = new Point(spots.get(0).x, spots.get(0).y);
        
        for(int i=0; i<20; i++) {
            things.add(new TerrainThing(
                    new Point(Ra.range(32, (int)size.x-32), Ra.range(32, (int)size.y-32)),
                    Ra.range(360), Assets.terrain_small.get(Ra.range(0, 2))));
        }
        
        MapGen.genSpawn(this, spots.get(0));
        
        MapGen.genBigBoxguards(this, spots.get(2));
        if(bigs > 1) MapGen.genBigAltarguards(this, spots.get(3));
        
        MapGen.genPortal(this, spots.get(1));
                
        int count_boxes = (int) (count * 0.25f);
        for(int i=0; i<count_boxes; i++) {
            MapGen.genBoxguards(this, emptyPoint());
        }
        
        int count_nonbox = (int) (count * 0.50f);
        for(int i=0; i<count_nonbox; i++) {
            if(Ra.chance(1f/3f)) {
                MapGen.genSquad(this, emptyPoint());
            }else if(Ra.chance(1f/2f)) {
                MapGen.genGroup(this, emptyPoint());
            }else{
                MapGen.genFireplace(this, emptyPoint());
            }
        }
        
        for(int i=0; i<20; i++) {
            //c.l.add(new Container(Team.Bad, emptyPoint(), 0, new SpriteBundleEntity(Assets.box.get())));
        }
        for(int i=0; i<80; i++) {
            //c.l.add(new Animal(Team.Bad, emptyPoint(), 0, new SpriteBundleEntity(Assets.axeman.get())));
        }
        
    }    
    
    private int createSpots(float area, int bigs) {
        spots.clear();
        
        area = (size.x * size.y * area);
        
        area -= addSpot(500); // spawn
        area -= addSpot(300); // portal
        for(int i=0; i<bigs; i++) area -= addSpot(400); // big
        
        spots_index = spots.size();
        
        int r = 250;
        int count = (int) (area / (Math.PI * r * r));
        
        for (int i = 0; i < count; i++) {
            spots.add(new Spot(Ra.range(r, (int) size.x - r), Ra.range(r, (int) size.y - r), r));
        }
        
        for(int i=0; i<150; i++) {
            if(!fitSpots()) break;
        }
        
        return count;
    }
    
    private float addSpot(float r) {
        spots.add(new Spot(Ra.range(r, (int) size.x - r), Ra.range(r, (int) size.y - r), r));
        return Ma.PI * r * r;
    }
    
    private boolean fitSpots() {
        boolean changed = false;
        
        int count = spots.size();
        for (int i1 = 0; i1 < count; i1++) {
            Spot p1 = spots.get(i1);
            
            for (int i2 = i1+1; i2 < count; i2++) {
                
                float r1 = spots.get(i1).r;
                float r2 = spots.get(i2).r;
                
                Point vec = spots.get(i1).clone().sub(spots.get(i2));
                float inside = vec.len() / (r1 + r2);
                
                if(inside <= 1) {
                    
                    if(inside < 0.5f) {
                        vec.norm().mul((r1 + r2)/2);
                    } 
                    
                    spots.get(i1).add(vec.mul(0.4f*(1.01f-inside)));
                    spots.get(i2).add(vec.mul(-1));
                    changed = true;
                }
                
            }
            
            if(p1.x < p1.r) p1.x = p1.r;
            if(p1.y < p1.r) p1.y = p1.r;
            if(p1.x > size.x - p1.r) p1.x = size.x - p1.r;
            if(p1.y > size.y - p1.r) p1.y = size.y - p1.r;
            
        }
        
        return changed;
    }
    
    private Spot emptyPoint() {
        //return new Point(Ra.range(32, (int)size.x-32), Ra.range(32, (int)size.y-32));
        return spots.get(spots_index++); 
    }
    
    private int tmp = 2000;
    private int tmp2 = 0;
    
    @Override
    public void update(int d) {
        
        for (Iterator<Entity> it = hotspots.iterator(); it.hasNext();) {
            Entity i = it.next();
            
            if(i.dead) it.remove();
        }
        /*
        tmp -= d;
        if(tmp > 0) return ;
        tmp = 1;
        //while(fitSpots()) {
        tmp2 += 1;
        //}
        if(!fitSpots()) {
            System.out.println(tmp2);
            tmp = 2000;
            tmp2 = 0;
            createSpots(0.5f);
        }
        //*/
    }

    @Override
    public void draw(Graphics g) {
        
        if(Op.debug) {
            g.setColor(Color.red);
            for(Spot i : spots) {
                g.setLineWidth(i.r==250? 1f : 20f);
                g.drawOval(i.x-i.r, i.y-i.r, i.r*2, i.r*2);
            }
        }
        for(TerrainThing i : things) {
            g.pushTransform();
            g.translate(i.p.x, i.p.y);
            g.rotate(0, 0, i.angle);
            
            i.img.draw(-i.w, -i.h, 2);
            
            g.popTransform();
        }
        
        g.setColor(new Color(0.1f,0.1f,0.1f));
        g.drawRect(0, 0, size.x, size.y);
    }

    
    public void drawMini(Graphics g, Point p, float radius) {
        
        
        for(int ii=1; ii<2+bigs; ii++) {
            Spot i = spots.get(ii);
            if(i.clone().sub(p).len() > radius) continue;
            g.setColor(Color.lightGray);
            g.fillOval(i.x-i.r/2, i.y-i.r/2, i.r, i.r);
        }
        
        g.setColor(Color.darkGray);
        for(int ii=2+bigs; ii<spots_index; ii++) {
            Spot i = spots.get(ii);
            if(i.clone().sub(p).len() > radius) continue;
            g.setColor(i.color);
            g.fillOval(i.x-i.r/2, i.y-i.r/2, i.r, i.r);            
        }
        
        //g.setColor(new Color(0.1f,0.1f,0.1f));
        //g.drawRect(0, 0, size.x, size.y);
    }

}
