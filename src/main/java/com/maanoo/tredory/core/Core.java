package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entities.Item;
import com.maanoo.tredory.core.entities.ItemType;
import com.maanoo.tredory.core.entities.Player;
import com.maanoo.tredory.face.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.Assets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Akritas
 */
public class Core implements IUpdate {
    
    public static Core c;
    
    public Player player;
    public Map map;
    
    public ArrayList<Entity> l;
    public LinkedList<Entity> ltoadd, ltoremove;
    
    public Point camera;
    
    private boolean request_newMap = false;
    
    public float arrow_angle;
    
    public void init() {
                
        l = new ArrayList<>();
        ltoadd = new LinkedList<>();
        ltoremove = new LinkedList<>();
        
        player = new Player(Team.Good, new Point(2000,2000), 0, new SpriteBundleEntity(Assets.chara.get()));
        l.add(player);
                
        newMap();
    }
    
    private void newMap() {
        
        while(l.size()>1) l.remove(1);
        
        map = new Map(new Point(4000, 4000));
        
        player.location.set(map.spawn);
        
        l.addAll(map.l);
    }
    
    public void requestNewMap() {
        request_newMap = true;
    }
    
    @Override
    public void update(int d) {
        
        if(request_newMap) {
            request_newMap = false;
            
            Stats.exitMap(map);
            
            Assets.trans.play();            
            
            newMap();
        }
        
        camera = player.location;
        
        arrow_angle = map.hotspots.get(0).location.clone().sub(player.location).angle();
        
        map.update(d);
        
        for(Entity i : l) {
            if(!i.dead) i.update(d);
        }
                
        for (Iterator<Entity> it = l.iterator(); it.hasNext();) {
            Entity i = it.next();
            if(i.dead) it.remove();
        }
        
        // == Collision detction ==
        
        int count = l.size();
        for(int i1=0; i1<count; i1++) {
            if(l.get(i1).state == EntityState.Die) continue;
            
            for(int i2=i1+1; i2<count; i2++) {
                if(l.get(i2).state == EntityState.Die) continue;
                
                float r1 = l.get(i1).sizecol;
                float r2 = l.get(i2).sizecol;
                Point c1 = l.get(i1).location.clone();//.add(new Point(r1, r1));
                Point c2 = l.get(i2).location.clone();//.add(new Point(r2, r2));
                
                if(c1.distance(c2) < (r1 + r2)*1.0f) {
                    l.get(i1).collide(l.get(i2));
                    l.get(i2).collide(l.get(i1));
                    
                    if(l.get(i1).state == EntityState.Die) i2 = count;
                }
                
            }
        }
        
        l.addAll(ltoadd);
        ltoadd.clear();
        
        l.removeAll(ltoremove);
        ltoremove.clear();
    }
    
    public Entity findClossest(Entity ent, Team team) {
        return findClossest(ent, team, Float.MAX_VALUE);
    }
    public Entity findClossest(Entity ent, Team team, float radius) {
        
        Entity min = null;
        float min_dis = Float.MAX_VALUE;
        
        for(Entity i : l) {
            
            if(i==ent) continue;
            if(i.team != team) continue;
            if(i.undead) continue;
            
            float dis = i.location.distance(ent.location);
            if(dis <= radius && dis < min_dis) {
                min_dis = dis;
                min = i;
            }
            
        }
        
        return min;
    }

    public ArrayList<Entity> findAll(Entity ent, Team team, float radius) {
        ArrayList<Entity> ret = new ArrayList<>();
                
        for(Entity i : l) {
            
            if(i == ent) continue;
            if(i.team != team) continue;
            if(i.undead) continue;
            
            float dis = i.location.distance(ent.location);
            if(dis <= radius) {
                ret.add(i);
            }
            
        }
        
        return ret;
    }
    
    public ArrayList<Item> findItems(Point location, int radius) {
        ArrayList<Item> ret = new ArrayList<>();
        
        for(Entity i : l) {
            
            if(!i.pickable) continue;
            
            float dis = i.location.distance(location);
            if(dis <= radius) {
                ret.add((Item) i);
            }
            
        }
        
        return ret;
    }
    
    public ArrayList<Entity> findStepables(Point location, int radius) {
        ArrayList<Entity> ret = new ArrayList<>();
        
        for(Entity i : l) {
            
            if(!i.stepable) continue;
            
            float dis = i.location.distance(location);
            if(dis <= radius) {
                ret.add(i);
            }
            
        }
        
        return ret;
    }
    
    public ArrayList<Entity> findActivatable(Point location, int radius) {
        ArrayList<Entity> ret = new ArrayList<>();
        
        for(Entity i : l) {
            
            if(!i.activatable) continue;
            
            float dis = i.location.distance(location);
            if(dis <= radius) {
                ret.add(i);
            }
            
        }
        
        return ret;
    }
    
    
    public void addItem(Entity con, int tier) {
        Item item;
        Point p = con.location.clone();

        switch (tier) {
        case 0:
            
            if (Ra.chance(0.05f)) {

                int value = Ra.range(0, 5) + Ra.range(1, 5);
                CoreDrops.dropCoins(this, con, 0, 360, .1f, .2f, value);
            }
        
            break;
        case 1:

            if (Ra.chance(0.75f)) {

                ItemType type = Ra.list(new ItemType[]{
                    ItemType.Shield0,
                    ItemType.Shield1, ItemType.Shield1, ItemType.Shield1,
                    ItemType.Shield2, ItemType.Shield2,
                    ItemType.Shield3
                });

                item = new Item(type, p);
                item.push(con.angle + 180, 0.2f);
                ltoadd.add(item);
            } else {
                int value = Ra.range(2, 22) + Ra.range(2, 22);

                while (value % 10 > 0) {
                    item = new Item(ItemType.Copper, p.clone().add(new Point(Ra.range(-8, 8), Ra.range(-8, 8))));
                    item.push(con.angle + 180 + Ra.range(-40f, 40f), Ra.range(0.1f, 0.4f));
                    ltoadd.add(item);

                    value -= 1;
                }
                while (value >= 10) {
                    item = new Item(ItemType.Gold, p.clone().add(new Point(Ra.range(-4, 4), Ra.range(-4, 4))));
                    item.push(con.angle + 180 + Ra.range(-40f, 40f), Ra.range(0.1f, 0.4f));
                    ltoadd.add(item);

                    value -= 10;
                }

            }
            break;
        case 2:

            ItemType type = Ra.list(new ItemType[]{
                ItemType.Crystal1, ItemType.Crystal2, ItemType.Crystal3
            });

            item = new Item(type, p);
            item.push(con.angle + 180, 0.1f);
            ltoadd.add(item);
            
            break;
        case 4:

            item = new Item(ItemType.Stone, p);
            item.push(con.angle + 180, 0.1f);
            ltoadd.add(item);
            
            break;
        }
    }
    
    public void dropItem(Entity ent, Item i) {
        i.dead = false;
        i.location = ent.location.clone();
        
        i.push(ent.angle + Ra.range(-30, 30), 0.3f);
        
        ltoadd.add(i);
        
    }
    
    public void removeItem(Item i) {
        i.location = null;
        i.dead = true;
        ltoremove.add(i);
        
        Assets.pick.play();
    }
}
