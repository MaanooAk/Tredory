// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.Recipe.Bundle;
import com.maanoo.tredory.core.entity.entities.Item;
import com.maanoo.tredory.core.entity.entities.ItemType;
import com.maanoo.tredory.core.utils.Ra;

import java.util.ArrayList;

import static com.maanoo.tredory.core.entity.entities.ItemType.*;

/**
 * @author MaanooAk
 */
public class Recipes {

    public static final Recipe all[] = new Recipe[]{

            new Recipe(500f, new Bundle(Crystal1, Crystal2, Crystal3, Shield3), new Bundle(Shield4)),

            new Recipe(100f, new Bundle(Shield0, Shield0, Shield0), new Bundle(Shield1)),
            new Recipe(100f, new Bundle(Shield1, Shield1, Shield1), new Bundle(Shield2)),
            new Recipe(100f, new Bundle(Shield2, Shield2, Shield2), new Bundle(Shield3)),

            new Recipe(20f, new Bundle(Crystal1, Crystal2, Crystal3), new Bundle(Crystal1, Crystal1, Crystal1)),
            new Recipe(20f, new Bundle(Crystal1, Crystal2, Crystal3), new Bundle(Crystal2, Crystal2, Crystal2)),
            new Recipe(20f, new Bundle(Crystal1, Crystal2, Crystal3), new Bundle(Crystal3, Crystal3, Crystal3)),
            new Recipe(20f, new Bundle(Crystal1, Crystal1, Crystal1), new Bundle(Crystal1, Crystal2, Crystal3)),
            new Recipe(20f, new Bundle(Crystal2, Crystal2, Crystal2), new Bundle(Crystal1, Crystal2, Crystal3)),
            new Recipe(20f, new Bundle(Crystal3, Crystal3, Crystal3), new Bundle(Crystal1, Crystal2, Crystal3)),

            //new Recipe(10f, new Bundle(Crystal1), new Bundle(Shield1, Shield1, Shield1, Shield1)),
            //new Recipe(10f, new Bundle(Crystal1, Crystal1), new Bundle(Shield3, Shield3, Shield3, Shield3)),
            new Recipe(10f, new Bundle(Crystal1, Crystal2, Crystal3), new Bundle(Shield3)),

            new Recipe(5f, new Bundle(Crystal1, Crystal2, Crystal3), new Bundle(Stone)),

    };

    public static Recipe getRecipe(ArrayList<Item> crystals, ArrayList<Item> shields) {

        ItemType types[] = new ItemType[crystals.size() + shields.size()];
        int types_index = 0;

        for (Item i : crystals) types[types_index++] = i.type;
        for (Item i : shields) types[types_index++] = i.type;

        Recipe accepted[] = new Recipe[all.length];
        int accepted_index = 0;

        for (Recipe r : all) {
            if (has(r.in, types)) accepted[accepted_index++] = r;
        }

        if (accepted_index == 0) return null;

        float rasum = 0;
        for (int i = 0; i < accepted_index; i++) rasum += accepted[i].rarity;

        float raval = Ra.range(rasum);
        for (int i = 0; i < accepted_index; i++) {
            raval -= accepted[i].rarity;

            if (raval <= 0) return accepted[i];
        }

        return null;
    }

    private static boolean has(Bundle b, ItemType types[]) {
        ItemType t[] = types.clone();

        boolean found;
        for (ItemType it : b.items) {
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
