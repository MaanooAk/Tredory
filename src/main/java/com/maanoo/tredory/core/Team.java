// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

/**
 * @author MaanooAk
 */
public enum Team {

    // TODO split into team and factions and rename holder something else

    Neutral,

    Good,

    Bad,

    Evil,

    Pure;

    public final boolean isEnemy(Team team) {
        final Team[] enemies = getEnemies(this);
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] == team) return true;
        }
        return false;
    }

    public final boolean isAllie(Team team) {
        final Team[] allies = getAllies(this);
        for (int i = 0; i < allies.length; i++) {
            if (allies[i] == team) return true;
        }
        return false;
    }

    // TODO change
    private static final Team[] TEAMS_EMPTY = new Team[] {};
    private static final Team[] TEAMS_1 = new Team[] { Bad, Evil };
    private static final Team[] TEAMS_2 = new Team[] { Good, Evil };
    private static final Team[] TEAMS_3 = new Team[] { Neutral, Good, Bad };
    private static final Team[] TEAMS_4 = new Team[] { Neutral, Pure };
    private static final Team[] TEAMS_5 = new Team[] { Good, Neutral, Pure };
    private static final Team[] TEAMS_6 = new Team[] { Bad, Pure };
    private static final Team[] TEAMS_7 = new Team[] { Evil };
    private static final Team[] TEAMS_8 = new Team[] { Pure, Neutral, Good, Bad, Evil };
    private static final Team[] TEAMS_9 = new Team[] { Pure };

    // TODO change
    // TODO use in logic
    public static final Team[] getEnemies(Team team) {
        switch (team) {
        case Neutral:
            return TEAMS_EMPTY;
        case Good:
            return TEAMS_1;
        case Bad:
            return TEAMS_2;
        case Evil:
            return TEAMS_3;
        case Pure:
            return TEAMS_EMPTY;
        default:
            return TEAMS_EMPTY;
        }
    }

    // TODO change
    // TODO use in logic
    public static final Team[] getAllies(Team team) {
        switch (team) {
        case Neutral:
            return TEAMS_4;
        case Good:
            return TEAMS_5;
        case Bad:
            return TEAMS_6;
        case Evil:
            return TEAMS_7;
        case Pure:
            return TEAMS_8;
        default:
            return TEAMS_9;
        }
    }

}
