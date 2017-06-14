// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entities;

/**
 *
 * @author Akritas
 */
public enum ItemType {

    Nothing,
    Copper, Gold,
    Shield0, Shield1, Shield2, Shield3, Shield4,
    Crystal1, Crystal2, Crystal3,
    Stone;
    
    public static ItemTag getTag(ItemType type) {
        switch (type) {
        case Copper:
        case Gold:
            return ItemTag.Coin;
        case Shield0:
        case Shield1:
        case Shield2:
        case Shield3:
        case Shield4:
            return ItemTag.Shield;
        case Crystal1:
        case Crystal2:
        case Crystal3:
            return ItemTag.Crystal;
        case Stone:
            return ItemTag.Stone;
        }
        return null;
    }
}
