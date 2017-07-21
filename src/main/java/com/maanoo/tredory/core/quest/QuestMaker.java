// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import com.maanoo.tredory.core.IMaker;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Ra;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class QuestMaker implements IMaker {

    public QuestMaker() {
        // TODO create possible
    }

    public Quest make(Player player) {

        return makeKill(player);
    }

    public Quest makeKill(Player player) {

        final int value = Ra.global.range(60);

        if (value < 20) {
            return new Quests.QuestKill(0, Ra.global.range(10, 30));
        } else if (value < 40) {
            return new Quests.QuestGainCoins(0, Ra.global.range(10, 50));
        } else if (value < 60) {
            return new Quests.QuestTravel(0, Ra.global.range(2, 5));
        } else {
            return new Quests.QuestKill(0, Ra.global.range(10, 30));
        }
    }

}
