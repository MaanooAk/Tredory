// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Defs {

    public static int PLAYER_SHIELD_CAPACITY = 2;
    public static int PLAYER_STONE_CAPACITY = 4;
    public static int PLAYER_CRYSTAL_CAPACITY = 12;
    public static int PLAYER_UNIQUE_CAPACITY = 4;

    public static int STONE_CRYSTAL_CAPACITY = 1;

    public static transient int PLAYER_CRYSTAL_MAX_CAPACITY;

    public static int CRYSTALS_PER_EXTRA_SHIELD = 2;

    public static float ENTITY_BASE_SPEED = 0.35f;

    static {
        updateTransients();
    }

    public static void updateTransients() {
        PLAYER_CRYSTAL_MAX_CAPACITY = PLAYER_CRYSTAL_CAPACITY + PLAYER_STONE_CAPACITY * STONE_CRYSTAL_CAPACITY;
    }
}
