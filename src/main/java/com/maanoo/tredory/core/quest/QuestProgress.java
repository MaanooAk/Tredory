// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class QuestProgress<T extends Quest> implements IUpdate {

    public final T quest;
    public final Player player;

    private int left;
    protected boolean active;
    protected boolean failed;

    public QuestProgress(T quest, Player player) {
        this.quest = quest;
        this.player = player;

        left = quest.duration;
        active = true;
        failed = false;

        start();
    }

    public abstract void start();

    public abstract void end();

    public abstract float getProgress();

    @Override
    public void update(int d) {
        left -= d;

        if (quest.hasDuration() && left <= 0) {
            left = 0;
            active = false;
            failed = true;

            end();
        } else if (getProgress() == 1f) {

            active = false;
            failed = false;

            quest.giveReward(player);
            end();
        }

    }

    public float getTimeProgress() {
        return 1f * left / quest.duration;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFailed() {
        return failed;
    }

}
