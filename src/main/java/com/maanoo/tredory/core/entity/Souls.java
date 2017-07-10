// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.entity.entities.Items;
import com.maanoo.tredory.core.utils.Ra;

/**
 * @author MaanooAk
 */
public final class Souls implements IUpdate {

    private int souls;
    private int capacity;

    public Souls() {
        this.souls = 0;
        this.capacity = 0;
    }

    public void addSoul() {
        if (souls < capacity) {
            souls += 1;
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

        if (souls > capacity / 2) {
            if (Ra.chance(0.001f * d)) { // TODO this is not math correct
                souls -= 1;
            }
        }
    }

    public void updateStones(Items stones) {
        capacity = stones.size() * 33; // TODO move multiplier elsewhere
    }

    public int getSouls() {
        return souls;
    }

    public int getCapacity() {
        return capacity;
    }
}
