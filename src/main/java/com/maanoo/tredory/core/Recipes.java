// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import static com.maanoo.tredory.core.entity.item.ItemType.CrystalR;
import static com.maanoo.tredory.core.entity.item.ItemType.CrystalG;
import static com.maanoo.tredory.core.entity.item.ItemType.CrystalB;
import static com.maanoo.tredory.core.entity.item.ItemType.Nothing;
import static com.maanoo.tredory.core.entity.item.ItemType.Shield3;
import static com.maanoo.tredory.core.entity.item.ItemType.Shield4;
import static com.maanoo.tredory.core.entity.item.ItemType.Stone;

import java.util.ArrayList;

import com.maanoo.tredory.core.Recipe.Bundle;
import com.maanoo.tredory.core.entity.item.Item;
import com.maanoo.tredory.core.entity.item.ItemType;


/**
 * @author MaanooAk
 */
public class Recipes {

    public static final Recipe all[] = new Recipe[] {

            new Recipe(new Bundle(CrystalR, CrystalR, CrystalR, CrystalG, CrystalG, CrystalG, CrystalB, CrystalB,
                    CrystalB), new Bundle(Stone)),

            new Recipe(new Bundle(CrystalR, CrystalG, CrystalB, Shield3), new Bundle(Shield4)),

            new Recipe(new Bundle(CrystalR, CrystalR, CrystalR), new Bundle(CrystalR, CrystalG, CrystalB)),
            new Recipe(new Bundle(CrystalG, CrystalG, CrystalG), new Bundle(CrystalR, CrystalG, CrystalB)),
            new Recipe(new Bundle(CrystalB, CrystalB, CrystalB), new Bundle(CrystalR, CrystalG, CrystalB)),

    };

    public static Recipe getRecipe(ArrayList<Item> crystals, ArrayList<Item> shields) {

        final ItemType types[] = new ItemType[crystals.size() + shields.size()];
        int types_index = 0;

        for (final Item i : crystals)
            types[types_index++] = i.type;
        for (final Item i : shields)
            types[types_index++] = i.type;

        for (final Recipe r : all) {
            if (has(r.in, types)) return r;
        }

        return null;

//        final Recipe accepted[] = new Recipe[all.length];
//        final int accepted_index = 0;
//
//        for (final Recipe r : all) {
//            if (has(r.in, types)) accepted[accepted_index++] = r;
//        }
//
//        if (accepted_index == 0) return null;
//
//        float rasum = 0;
//        for (int i = 0; i < accepted_index; i++)
//            rasum += accepted[i].rarity;
//
//        float raval = Ra.global.range(rasum);
//        for (int i = 0; i < accepted_index; i++) {
//            raval -= accepted[i].rarity;
//
//            if (raval <= 0) return accepted[i];
//        }
//
//        return null;
    }

    private static boolean has(Bundle b, ItemType types[]) {
        final ItemType t[] = types.clone();

        boolean found;
        for (final ItemType it : b.items) {
            found = false;
            for (int i = 0; i < t.length; i++) {

                if (it == t[i]) {
                    t[i] = Nothing;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }

        return true;
    }

}
