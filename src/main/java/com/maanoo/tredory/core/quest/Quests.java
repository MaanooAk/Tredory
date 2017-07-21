// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Quests {

    public static class QuestGainCoins extends QuestStat {

        public QuestGainCoins(int duration, int count) {
            super(duration, count);
        }

        @Override
        public int getStat(Player player) {
            return player.coins;
        }

    }

    public static class QuestKill extends QuestStat {

        public QuestKill(int duration, int count) {
            super(duration, count);
        }

        @Override
        public int getStat(Player player) {
            return player.stats.kills;
        }

    }

    public static class QuestTravel extends QuestStat {

        public QuestTravel(int duration, int count) {
            super(duration, count);
        }

        @Override
        public int getStat(Player player) {
            return player.stats.maps;
        }

    }

}
