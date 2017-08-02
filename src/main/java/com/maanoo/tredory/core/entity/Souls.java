// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.entity.ActionsPlayer.Group;
import com.maanoo.tredory.core.entity.item.Items;


/**
 * @author MaanooAk
 */
public final class Souls implements IUpdate {

    private static final int STONE_CAPACITY = 30;

    private int souls;
    private int stones;
    private int capacity;

    public Souls() {
        this.souls = 0;
        this.capacity = 0;
    }

    public void addSoul() {
        if (souls < capacity) {
            souls += 1;
        } else if (stones > 0) {
            souls -= 3 * stones;
            Core.c.player.getActions().get(Group.Soul, stones - 1).perform();
        }
    }

    public boolean removeSouls(int count) {
        if (souls >= count) {
            souls -= count;
            return true;
        }
        return false;
    }

    @Override
    public void update(int d) {

//        if (souls > capacity / 2) {
//            if (Ra.global.chance(0.0001f * d)) { // TODO this is not math correct
//                souls -= 1;
//            }
//        }
    }

    public void updateStones(Items stones) {
        this.stones = stones.size();
        this.capacity = stones.size() * STONE_CAPACITY; // TODO move multiplier elsewhere
    }

    public int getSouls() {
        return souls;
    }

    public int getCapacity() {
        return capacity;
    }
}
