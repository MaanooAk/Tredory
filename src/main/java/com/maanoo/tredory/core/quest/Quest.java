// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Quest {

    public final int duration;

    public Quest(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return "Quest";
    }

    public abstract QuestProgress start(Player player);

    public abstract void giveReward(Player player);

    public final boolean hasDuration() {
        return duration > 0;
    }

}
