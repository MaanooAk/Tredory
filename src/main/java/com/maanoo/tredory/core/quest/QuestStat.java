// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.engine.Logger;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class QuestStat extends Quest {

    public final int count;

    public QuestStat(int duration, int count) {
        super(duration);
        this.count = count;
    }

    public abstract int getStat(Player player);

    @Override
    public QuestProgress start(Player player) {
        return new QuestStatProgress<QuestStat>(this, player);
    }

    @Override
    public void giveReward(Player player) {
        // TODO give reward
        Logger.log("Quest", "Give reward");
    }

}
