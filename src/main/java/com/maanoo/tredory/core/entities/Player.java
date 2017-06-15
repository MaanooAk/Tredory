// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entities;

import com.maanoo.tredory.core.*;
import com.maanoo.tredory.face.SpriteBundle;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author MaanooAk
 */
public final class Player extends Entity {

    public static final class Items extends ArrayList<Item> {
        public int max;
        public int max_base;

        public Items(int max) {
            this.max = max;
            this.max_base = max;
        }

        public boolean isMax() {
            return size() >= max;
        }
    }

    public int coins;

    private final float lspeed_base = 0.35f;
    public float lspeed;

    public final Items shields;
    public final Items crystals;
    public final Items stones;

    public CrystalComp ccomp;

    public boolean selectpressed = false;

    public Player(Team team, Point location, float angle, SpriteBundle sprites) {
        super(team, location, angle, sprites);

        coins = 0;

        lspeed = lspeed_base;

        shields = new Items(2);
        crystals = new Items(15);
        stones = new Items(3);

        ccomp = new CrystalComp(0, 0, 0);

        takeShield(new Item(ItemType.Shield0, null));
        takeShield(new Item(ItemType.Shield0, null));

        updateEffects();
    }

    @Override
    public void update(int d) {
        super.update(d);

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
    public void draw(Graphics g) {
        super.draw(g);
    }

    public void takeItem(Item i) {
        Items its = null;

        switch (ItemType.getTag(i.type)) {
        case Crystal:
            takeCrystal(i);
            break;
        case Shield:
            takeShield(i);
            break;
        case Stone:
            its = stones;
            break;
        }

        if (its != null) {

            if (its.isMax()) {
                Item it = its.remove(Ra.range(its.size()));

                it.unpicablify(1000);
                Core.c.dropItem(this, it);
            }

            its.add(i);
            Collections.sort(its);

            updateEffects();
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
