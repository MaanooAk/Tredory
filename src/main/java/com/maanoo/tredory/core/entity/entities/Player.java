// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import com.maanoo.tredory.core.*;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.Souls;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.SpriteBundle;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author MaanooAk
 */
public final class Player extends Entity {

    private final float lspeed_base = 0.35f;
    public float lspeed;

    public int coins;

    public final Items shields;
    public final Items crystals;
    public final Items stones;

    public final Souls souls;

    public CrystalComp ccomp;

    public boolean selectpressed = false;

    public Player(Team team, Point location, float angle, SpriteBundle sprites) {
        super(team, location, angle, sprites);

        coins = 0;

        lspeed = lspeed_base;

        shields = new Items(2);
        crystals = new Items(15);
        stones = new Items(5);

        souls = new Souls();

        ccomp = new CrystalComp(0, 0, 0);

        takeShield(new Item(ItemType.Shield0, null));
        takeShield(new Item(ItemType.Shield0, null));
        
        updateEffects();
    }

    @Override
    public void update(int d) {
        super.update(d);

        souls.update(d);

        switch (state) {
        case Idle: {
            ArrayList<Item> l = Core.c.findItems(location, 52);

            for (Item i : l) {
                if (i.type == ItemType.Copper) {
                    Core.c.removeItem(i);
                    coins += 1;
                } else if (i.type == ItemType.Gold) {
                    Core.c.removeItem(i);
                    coins += 10;
                } else if (i.tag == ItemTag.Shield && canTakeShield(i)) {
                    Core.c.removeItem(i);
                    takeShield(i);
                } else if (i.tag == ItemTag.Crystal) {
                    Core.c.removeItem(i);
                    takeCrystal(i);
                } else if (i.tag == ItemTag.Stone) {
                    Core.c.removeItem(i);
                    takeItem(i);
                } else {
                    continue;
                }
                break;
            }
        }
        {
            ArrayList<Entity> l = Core.c.findStepables(location, 30);

            for (Entity i : l) {
                i.activate();
            }
        }

        if (selectpressed) {
            ArrayList<Entity> l = Core.c.findActivatable(location, 50);

            for (Entity i : l) {
                i.activate();
            }
        }

        break;
        }

        selectpressed = false;
    }

    public void updateEffects() {

        ccomp.update(crystals, shields);
        ccomp.effect.crystals.add = stones.size() * 2;

        shields.max = (int) ccomp.effect.shields.apply(shields.max_base);
        crystals.max = (int) ccomp.effect.crystals.apply(crystals.max_base);

        lspeed = ccomp.effect.speed.apply(lspeed_base);

        dropWorstShields();
    }

    @Override
    public Effect getEffect() {
        return ccomp.effect;
    }

    public void takeItem(Item i) {

        switch (ItemType.getTag(i.type)) {
        case Crystal:
            takeCrystal(i);
            break;
        case Shield:
            takeShield(i);
            break;
        case Stone:
            takeStone(i);
            break;
        }
    }

    public boolean canTakeShield(Item shield) {
        return shields.size() < shields.max || shields.get(0).type.ordinal() < shield.type.ordinal();
    }

    public void takeShield(Item shield) {
        shields.add(shield);
        Collections.sort(shields);

        dropWorstShields();
    }

    public void dropWorstShields() {
        while (shields.size() > shields.max) {
            Core.c.dropItem(this, shields.remove(0));
        }


    }

    public void takeCrystal(Item crystal) {

        if (crystals.size() == crystals.max) {
            Item i = crystals.remove(Ra.range(crystals.size()));

            i.unpicablify(1000);
            Core.c.dropItem(this, i);
        }

        crystals.add(crystal);

        Collections.sort(crystals);

        updateEffects();
    }

    public Item giveCrystal() {

        Item crystal = crystals.remove(Ra.range(0, crystals.size()));

        updateEffects();
        return crystal;
    }

    public void takeStone(Item stone) {

        if (stones.isMax()) {
            Item it = stones.remove(Ra.range(stones.size()));

            it.unpicablify(1000);
            Core.c.dropItem(this, it);
        }

        stones.add(stone);
        Collections.sort(stones);

        souls.updateStones(stones);
        updateEffects();
    }

    public void giveItem(ItemType type) {

        switch (ItemType.getTag(type)) {
        case Crystal:
            for (int i = 0; i < crystals.size(); i++) {
                if (crystals.get(i).type == type) {
                    crystals.remove(i);
                    updateEffects();
                    return;
                }
            }
            break;
        case Shield:
            for (int i = 0; i < shields.size(); i++) {
                if (shields.get(i).type == type) {
                    shields.remove(i);
                    updateEffects();
                    return;
                }
            }
            break;
        }

    }

    @Override
    public void takeDamage() {
        if (shields.isEmpty()) {
            die();
        } else {
            shields.remove(0);
        }
    }


}
