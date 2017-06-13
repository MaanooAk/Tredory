package com.maanoo.tredory.core;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.face.SpriteBundle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Akritas
 */
public class Entity implements IUpdate, IDraw {
    
    public int life;
    public boolean undead;
    public boolean dead;
    public boolean pickable = false;
    public boolean stepable = false;
    public boolean activatable = false;
    public boolean movable = true;
    public Team team;
    public Point location;
    public Point speed;
    public float angle;
    public EntityState state;
    public SpriteBundle sprites;
    public int size = 32;
    public int sizecol = size/2;
    public boolean alerted = false;
    public int shieldsSum = 0;
    public boolean opened;
    
    public Entity(Team team, Point location, float angle, SpriteBundle sprites) {
        this.team = team;
        this.location = location;
        this.angle = angle;
        this.sprites = sprites.copy();
        
        life = 0;
        undead = false;
        dead = false;
        speed = new Point();
        state = EntityState.Idle;
    }
    
    @Override
    public void update(int d) {
        life += d;
        
        sprites.getAnimation(state).update(d);
        
        switch (state) {
        case Attack:

            if (sprites.attack.isStopped()) {
                stopAttack();
            }

            break;
        case Move:

            //location.add(speed.clone().mul(d));
            break;
        case Die:
            
            if (!opened && sprites.die.getFrame() >= 4) {
                opened = true;
                drops();
            }
            
            if (sprites.die.isStopped()) {
                dead = true;
            }

            break;
        }
    }
    
    @Override
    public void draw(Graphics g) {
        g.pushTransform();
        
        g.translate(location.x, location.y);
        g.rotate(0, 0, angle+180);
        //g.scale(0.5f, 0.5f);
                
        sprites.getAnimation(state).draw(-size, -size, 2*size, 2*size);
        //sprites.getAnimation(state).draw(-32/2, -32/2, 64/2, 64/2);
        
        if(Op.debug) {
            g.setColor(Color.red);
            g.drawOval(-sizecol, -sizecol, 2*sizecol, 2*sizecol);
        }
        
        g.popTransform();
    }
    
    public void collide(Entity e) {
        
        if(!undead && !e.undead) {
            
            Point vec = e.location.clone().sub(location).norm();
            
            if(movable) location.add(vec.clone().mul(-1));
            if(e.movable) e.location.add(vec.clone().mul(1));
            
        }
        
    }
    
    public final void startAttack(float speed) {
        sprites.attack.restart();
        sprites.attack.setSpeed(speed);        
        state = EntityState.Attack;
    }
    
    public final void stopAttack() {
        sprites.attack.restart();     
        state = EntityState.Idle;
    }
    
    public void takeDamage() {
        if (shieldsSum > 0) {
            shieldsSum -= 1;
        } else {
            die();
        }
    }
    
    public void drops() {
        
    }
    
    public void die() {
        state = EntityState.Die;
    }
    
    public void activate() {
        
    }
}
