// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.states;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.Op.Keys;
import com.maanoo.tredory.core.Attack;
import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Entity;
import com.maanoo.tredory.core.EntityState;
import com.maanoo.tredory.core.Ma;
import com.maanoo.tredory.core.PlayerAttacks;
import com.maanoo.tredory.core.Point;
import com.maanoo.tredory.core.Stats;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entities.Item;
import com.maanoo.tredory.core.entities.ItemType;
import com.maanoo.tredory.face.assets.Assets;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Akritas
 */
public class StateGame extends State {

    private int w, h;    
    private float zoom;

    public StateGame() {
        super(StateId.Game);
    }
    
    private Core c;

    private PlayerAttacks pa;
    
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        
        
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        super.enter(gc, game);
        
        gc.getInput().clearKeyPressedRecord();
        
        if(c == null || c.player.dead) {
        
            c = new Core();
            Core.c = c;
            c.init();        

            pa = new PlayerAttacks(c.player);
            
            zoom = 1;
        
            Stats.reset();
        }
    }

    
    
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        w = gc.getWidth();
        h = gc.getHeight();
        
        //g.setAntiAlias(true);
        
        
        /*
        g.setColor(new Color(.3f, .4f, .3f));        
        g.fillRect(10, 580, 780, 10);
        
        g.setColor(new Color(.2f, .9f, .2f));        
        g.fillRect(10, 580, 780-20, 10);
        g.setColor(new Color(.2f, .8f, .2f));        
        g.fillRect(15, 585, 770-20, 5);
        */
        
        // ===
        g.pushTransform();
        g.translate(w/2, h/2);
        g.scale(zoom, zoom);
        g.translate(-c.camera.x, -c.camera.y);
        
        c.map.draw(g);
                
        for(Entity i : c.l) i.draw(g);
        
        c.player.draw(g);
        
        /*
        g.pushTransform();
        g.translate(player.location.x, player.location.y);
        g.rotate(0, 0, player.angle);

        SpriteBundles.char1.image.draw(-32, -32, 2);
        
        g.popTransform();*/
        
        g.popTransform();

        // map
        {
            g.setColor(new Color(0, 0, 0, 0.75f));
            g.fillRect(-1, -1, 10 + 96, 10+96);
            g.setColor(Color.darkGray);
            g.drawRect(-1, -1, 10 + 96, 10+96);
            
            g.pushTransform();
            g.translate(5+48, 5+48);
            
            {
                g.pushTransform();
                g.scale(0.02f, 0.02f);
                g.translate(-c.camera.x, -c.camera.y);
                
                c.map.drawMini(g, c.camera, 34f/0.02f);

                g.popTransform();
            }
            
            g.setColor(Color.white);
            g.fillOval(-1, 1, 2, 2);
            
            g.rotate(0, 0, Core.c.arrow_angle);
            
            Assets.effect_arrow.draw(-48, -48, 96, 96, Color.gray);
            
            
            
            g.popTransform();
        }
        // shields
        {
            
            if(c.player.shields.size() > 0) {
                g.setColor(new Color(0, 0, 0, 0.75f));
                g.fillRect(-1, h - 52, 33 + 18 * c.player.shields.max + 1, 52);
                g.setColor(Color.darkGray);
                g.drawRect(-1, h - 52, 33 + 18 * c.player.shields.max + 1, 52);

                int x = 10;
                for (int i = c.player.shields.size() - 1; i >= 0; i--) {
                    c.player.shields.get(i).sprites.idle.draw(x, h - 42, 32, 32);
                    x += 18;
                }
            }
        }
        // crystal
        {
            int count = c.player.crystals.size();
            if(count > 0) {
                
                int pad = 16-4;
                int wi = 20 + count * pad;

                g.setColor(new Color(0, 0, 0, 0.75f));
                g.fillRect(w - wi, h - 52, wi, 52);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi, h - 52, wi, 52);

                int x = w - wi + 10 -pad+2, y = 2;
                if(count%2 == 0) y *= -1;
                for(int i=0; i<c.player.crystals.size(); i++) {
                    c.player.crystals.get(i).sprites.idle.draw(x, h-42+y, 32, 32);
                    x += pad;
                    y *= -1;
                }
            }
        }
        // stones
        {
            int count = c.player.stones.size();
            if(count > 0) {
                
                int pad = 16-4+6;
                int wi = 20 + count * pad;

                g.setColor(new Color(0, 0, 0, 0.75f));
                g.fillRect(w - wi, h - 52-52, wi, 52);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi, h - 52-52, wi, 52);

                int x = w - wi + 10 -pad+12, y = 2;
                if(count%2 == 0) y *= -1;
                for(int i=0; i<c.player.stones.size(); i++) {
                    c.player.stones.get(i).sprites.idle.draw(x, h-42+y-52, 32, 32);
                    x += pad;
                    y *= -1;
                }
            }
        }
        // coins 
        {
            if(c.player.coins>0) { 
                final int groups = c.player.coins<400?5:c.player.coins<800?10:c.player.coins<2000?20:30;

                int wi = 16 * (c.player.coins / (groups*10)) + 16;
                wi += groups<=10?(10/groups) * 16:16;

                g.setColor(new Color(0, 0, 0, 0.75f));
                g.fillRect(w - wi - 10, 0, wi + 20, 28+3*groups);
                g.setColor(Color.darkGray);
                g.drawRect(w - wi - 10, -1, wi + 20+1, 28+3*groups+1);

                int x = w - 10 - 16;
                int y = 0;
                int left = c.player.coins;
                while (left >= 10) {
                    Assets.getItem(ItemType.Gold).get().draw(x, y, 2);
                    left -= 10;
                    y = (y + 3) % (3*groups);
                    if (y == 0) x -= 16;
                }

                if(y!=0) {
                    y = 0;
                    x -= 16;
                }
                while (left >= 1) {
                    Assets.getItem(ItemType.Copper).get().draw(x, y, 2);
                    left -= 1;
                    y = (y + 3) % (3*groups);
                    if (y == 0) x -= 16;
                }
            }
            
        }
        // spells
        {
            int count = 4;
            
            int wi = 16;
            
            int x = (int) (-count/2f*(wi+10));
            for(int i=0; i<count; i++) {
                
                g.setColor(new Color(0, 0, 0, 0.75f));
                g.fillOval(w/2 + x, h-wi-10, wi, wi);
                g.setColor(Color.darkGray);
                g.drawOval(w/2 + x, h-wi-10, wi, wi);
                
                g.setColor(Color.darkGray);
                g.fillArc(w/2 + x, h-wi-10, wi, wi, -180, -180);
                
                x += wi+10;
            }
        }

        // debug
        if(Op.debug) {
            Assets.font1.drawString(10, 10, "fps " + gc.getFPS() +
                    " | e " + c.l.size() +
                    " | s " + (c.l.size() + c.map.things.size()) +
                    " | " + (int)c.player.location.x + " " + (int)c.player.location.y + " " + (int)c.player.angle , Color.darkGray);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int d) throws SlickException {
        //d = d/10;
        
        Input in = gc.getInput();
        
        Point mouse = new Point(in.getMouseX()-w/2, in.getMouseY()-h/2);
        
        if(in.isKeyDown(Input.KEY_P)) d = d/2;
        if(in.isKeyDown(Input.KEY_O)) d = d/5;
        
        
        if(in.isKeyPressed(Keys.Back)) {
            
            changeState(game, StateId.Menu);
        }
        
        if(c.player.state != EntityState.Attack) {
                   
            c.player.angle = (float) (-Math.atan2(gc.getInput().getMouseX()-w/2, gc.getInput().getMouseY()-h/2)*360/(2*Math.PI));
            
            Attack spell = null;
            
            if(in.isMouseButtonDown(Keys.Attack1)) spell = pa.getAttack(0, 0);
            if(in.isMouseButtonDown(Keys.Attack2)) spell = pa.getAttack(0, 1);
            if(in.isMouseButtonDown(Keys.Attack3)) spell = pa.getAttack(0, 2);
            if(in.isKeyDown(Keys.Attack4)) spell = pa.getAttack(0, 3);
            
            for(int i=0; i<7; i++) if(in.isKeyDown(Keys.Spell[i])) spell = pa.getAttack(1, i);
            
            if(spell != null) {
                c.player.startAttack(spell.getAttackspeed(c.player.ccomp.effect));
                spell.perform(c, c.player.location, c.player.angle, c.player.ccomp.effect);
            }
            
            /*
            if(in.isKeyDown(Input.KEY_Q)) {

                c.player.startAttack(1.0f);

                Entity ent = new AreaDamage(Team.Good, c.player.location.clone().add(mouse), 0, 
                        new SpriteBundleEntity(Assets.flameblast.get()), 
                        c.player.sprites.attack.getSpeed());
                ent.size *= 3;
                ent.sizecol = ent.size*2/3;
                
                c.l.add(0, ent);
                
            }*/
            
        } 
        
        if(c.player.state != EntityState.Attack) {

            Point vec = new Point();
            if (!in.isKeyDown(Keys.PickUp)) {
                if (in.isKeyDown(Keys.MoveU)) vec.y -= 1;
                if (in.isKeyDown(Keys.MoveD)) vec.y += 1;
                if (in.isKeyDown(Keys.MoveL)) vec.x -= 1;
                if (in.isKeyDown(Keys.MoveR)) vec.x += 1;
            }

            // move player
            if (!vec.isZero()) {

                // calculate speed multiplier based on movement direction relative to facing angle
                float m = Ma.abs(vec.angle() - c.player.angle);
                while (m > 180) m -= 360;
                m = Ma.abs(m / 180f);

                float speed = (1 - m * .6f) * c.player.lspeed;

                c.player.location.add(vec.norm().mul(speed * d));
            }


            if (c.player.state == EntityState.Idle && !vec.isZero()) {
                c.player.state = EntityState.Move;

            } else if (c.player.state == EntityState.Move && vec.isZero()) {
                c.player.state = EntityState.Idle;
            }

        }
        
        if(in.isKeyPressed(Keys.Select)) {
            c.player.selectpressed = true;
        }
        
        // ====
        
        c.update(d);
        pa.update(d);
        
        // ====
        
        if(in.isKeyDown(Input.KEY_TAB)) {
            zoom *= 0.985f;
            if(zoom <= 0.1f) zoom = 0.1f;
        }else if(zoom != 1){
            zoom /= 0.94f;
            if(zoom >= 1) zoom = 1;
        }
        zoom = in.isKeyDown(Input.KEY_TAB) ? 0.1f : 1f;
        
        if(in.isKeyDown(Input.KEY_1)) Assets.bam.playAt(new Point(
                in.getMouseX()-(w/2)+c.player.location.x, 
                in.getMouseY()-(h/2)+c.player.location.y));
        
        if(in.isKeyPressed(Input.KEY_2)) c.player.takeShield(new Item(ItemType.Shield0, null));
        
        if(in.isKeyPressed(Input.KEY_3)) c.player.coins += 10;
        
        if(in.isKeyPressed(Input.KEY_4)) Core.c.addItem(c.player, 4);
        
        if(in.isKeyPressed(Input.KEY_7)) c.player.team = c.player.team == Team.Good? Team.Bad : Team.Good;
        
        if(in.isKeyPressed(Input.KEY_8)) Core.c.addItem(c.player, 2);
        
        if(in.isKeyPressed(Input.KEY_9)) Core.c.requestNewMap();
        
        if(in.isKeyPressed(Input.KEY_0)) c.player.takeDamage();
        
        if(in.isKeyPressed(Input.KEY_F1)) Op.debug = !Op.debug;        
        
        if(c.player.dead) {
            changeState(game, StateId.Over);
        }
    }

}
