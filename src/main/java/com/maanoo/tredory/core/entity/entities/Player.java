// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import java.util.Collections;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.CrystalComp;
import com.maanoo.tredory.core.Defs;
import com.maanoo.tredory.core.Stats;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.ActionsPlayer;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.Souls;
import com.maanoo.tredory.core.entity.item.Item;
import com.maanoo.tredory.core.entity.item.ItemTag;
import com.maanoo.tredory.core.entity.item.ItemType;
import com.maanoo.tredory.core.entity.item.Items;
import com.maanoo.tredory.core.entity.item.Unique;
import com.maanoo.tredory.core.quest.QuestsTracker;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * @author MaanooAk
 */
public final class Player extends Entity {

    public int coins;

    // TODO move items to new Inventory class

    public final Items shields;
    public final Items crystals;
    public final Items stones;

    public final Items uniques;

    public final Souls souls;

    public CrystalComp ccomp;

    public boolean selectpressed = false;

    public final Stats stats;

    public final QuestsTracker quests;

    // TODO change, tmp
    public ProjectileType projectile;

    public Player(Team team, Point location, float angle, SpriteBundleEntity sprites) {
        super(team, location, angle, sprites);

        coins = 0;

        shields = new Items(Defs.PLAYER_SHIELD_CAPACITY);
        crystals = new Items(Defs.PLAYER_CRYSTAL_CAPACITY);
        stones = new Items(Defs.PLAYER_STONE_CAPACITY);

        uniques = new Items(Defs.PLAYER_UNIQUE_CAPACITY);

        souls = new Souls();

        ccomp = new CrystalComp(0, 0, 0);
        effects.addPermanent(ccomp.effect);

        stats = new Stats();
        quests = new QuestsTracker();

        updateEffects();
    }

    @Override
    public void update(int d) {
        super.update(d);

        souls.update(d);

        for (final Item i : uniques) {
            i.update(d);
        }

        switch (state) {
        case Idle:

            Core.c.findItems(location, 52, (i) -> {
                this.handleReachableItem(i);
            });

            Core.c.activateStepables(location, 30);

            if (selectpressed) {
                Core.c.activateActivatables(location, 50);
            }

            break;
        default:
            break;
        }

        selectpressed = false;

        quests.update(d);
    }

    private void handleReachableItem(Item i) {

        // TODO transform to switch

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
        }

    }

    public void updateEffects() {

        ccomp.update(crystals, shields);

        // TODO move stone effect to separate effect
        ccomp.effect.crystals.add = stones.size() * Defs.STONE_CRYSTAL_CAPACITY;

        effects.updateEffects();

    }

    @Override
    public void updateEffectsEffects() {
        super.updateEffectsEffects();

        shields.max = (int) getEffect().shields.apply(shields.max_base);
        crystals.max = (int) getEffect().crystals.apply(crystals.max_base);

        dropWorstShields();
    }

    public void takeItem(Item i) {

        switch (i.type.tag) {
        case Crystal:
            takeCrystal(i);
            break;
        case Shield:
            takeShield(i);
            break;
        case Stone:
            takeStone(i);
            break;
        case Unique:
            takeUnique(i);
            break;
        default:
            throw new RuntimeException();
        }
    }

    public boolean canTakeItem(Item i) {

        switch (i.type.tag) {
        case Crystal:
            return canTakeCystal();
        case Shield:
            return canTakeShield(i);
        case Stone:
            return canTakeStone();
        default:
            return true;
        }
    }

    public boolean canTakeShiled() {
        return shields.size() < shields.max;
    }

    public boolean canTakeShield(Item shield) {
        return shields.size() < shields.max || shields.get(0).type.ordinal() < shield.type.ordinal();
    }

    private void takeShield(Item shield) {
        shields.add(shield);
        Collections.sort(shields);

        dropWorstShields();
    }

    public void dropWorstShields() {
        while (shields.size() > shields.max) {
            Core.c.dropItem(this, shields.remove(0));
        }

    }

    public boolean canTakeCystal() {
        return crystals.size() < crystals.max;
    }

    private void takeCrystal(Item crystal) {

        if (!canTakeCystal()) {
            final Item i = crystals.remove(Ra.global.range(crystals.size()));

            i.unpicablify(1000);
            Core.c.dropItem(this, i);
        }

        crystals.add(crystal);

        Collections.sort(crystals);

        updateEffects();
    }

    private Item giveCrystal() {

        final Item crystal = crystals.remove(Ra.global.range(0, crystals.size()));

        updateEffects();
        return crystal;
    }

    public boolean canTakeStone() {
        return !stones.isMax();
    }

    private void takeStone(Item stone) {

        if (!canTakeStone()) {
            final Item it = stones.remove(Ra.global.range(stones.size()));

            it.unpicablify(1000);
            Core.c.dropItem(this, it);
        }

        stones.add(stone);
        Collections.sort(stones);

        souls.updateStones(stones);
        updateEffects();
    }

    private void takeUnique(Item unique) {

        uniques.add(unique);
        Collections.sort(uniques);

        ((Unique) unique).setUser(this);

        updateEffects();
    }

    public void destroyItem(ItemType type) {

        switch (type.tag) {
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
        default:
            // TODO handle the rest of the types
            throw new UnsupportedOperationException();
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

    public ActionsPlayer getActions() {
        return (ActionsPlayer) actions;
    }

}
