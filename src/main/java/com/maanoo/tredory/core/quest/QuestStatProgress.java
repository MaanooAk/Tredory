// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Ma;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class QuestStatProgress<T extends QuestStat> extends QuestProgress<T> {

    public QuestStatProgress(T quest, Player player) {
        super(quest, player);
    }

    public int offset;

    @Override
    public void start() {
        offset = -quest.getStat(player);
    }

    @Override
    public void end() {}

    @Override
    public float getProgress() {
        return Ma.limit(1f * (quest.getStat(player) + offset) / quest.count, 0f, 1f);
    }

}
