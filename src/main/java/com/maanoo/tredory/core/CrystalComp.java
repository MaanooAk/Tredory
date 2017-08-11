// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.effect.Effect;
import com.maanoo.tredory.core.entity.item.Item;
import com.maanoo.tredory.core.entity.item.Items;


/**
 * @author MaanooAk
 */
public final class CrystalComp {

    // TODO rename class

    public final int count[];

    public final Effect effect;

    public CrystalComp(int c1, int c2, int c3) {
        count = new int[] { c1, c2, c3 };

        effect = new Effect();
        calcEffect();
    }

    public void update(Items... items) {

        for (int i = 0; i < count.length; i++)
            count[i] = 0;

        for (final Items iitems : items)
            for (final Item i : iitems) {
                switch (i.type) {
                case CrystalR:
                    count[0] += 1;
                    break;
                case CrystalG:
                    count[1] += 1;
                    break;
                case CrystalB:
                    count[2] += 1;
                    break;
                case CrystalW:
                    count[0] += 1;
                    count[1] += 1;
                    count[2] += 1;
                    break;
                case Shield4:
                    count[0] += 1;
                    count[1] += 1;
                    count[2] += 1;
                    break;
                }
            }

        calcEffect();
    }

    private void calcEffect() {
        effect.shields.add = count[0] / Defs.CRYSTALS_PER_EXTRA_SHIELD;
        effect.speed.add = count[1] * 0.03f;
        effect.attackspeed.mul = count[2] * 0.2f;
    }
}
