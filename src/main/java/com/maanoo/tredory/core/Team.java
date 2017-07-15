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
    // TODO use in logic
    public static final Team[] getEnemies(Team team) {
        switch (team) {
        case Neutral:
            return new Team[] {};
        case Good:
            return new Team[] { Bad, Evil };
        case Bad:
            return new Team[] { Good, Evil };
        case Evil:
            return new Team[] { Neutral, Good, Bad };
        case Pure:
            return new Team[] {};
        default:
            return new Team[] {};
        }
    }

    // TODO change
    // TODO use in logic
    public static final Team[] getAllies(Team team) {
        switch (team) {
        case Neutral:
            return new Team[] { Neutral, Pure };
        case Good:
            return new Team[] { Good, Neutral, Pure };
        case Bad:
            return new Team[] { Bad, Pure };
        case Evil:
            return new Team[] { Evil };
        case Pure:
            return new Team[] { Pure, Neutral, Good, Bad, Evil };
        default:
            return new Team[] { Pure };
        }
    }

}
