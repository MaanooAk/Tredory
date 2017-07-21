// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.quest;

import java.util.ArrayList;

import com.maanoo.tredory.core.IUpdate;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class QuestsTracker implements IUpdate {

    public static final int MAX_QUESTS = 3;

    public int maxQuests;

    public final ArrayList<QuestProgress> quests;

    private transient boolean hasInActive = false;

    public QuestsTracker() {

        maxQuests = 0;

        quests = new ArrayList<>(MAX_QUESTS);

    }

    public void increaseCapacity() {
        if (maxQuests < MAX_QUESTS) {
            maxQuests += 1;
        }
    }

    public boolean canAddQuest() {
        return quests.size() < maxQuests || hasInActive;
    }

    public void addQuest(QuestProgress quest) {

        if (quests.size() < maxQuests) {
            // add the quest at the end
            quests.add(quest);

        } else {
            // find an inactive quest and replace it
            int index = 0;
            while (quests.get(index).isActive()) {
                index++;
            }

            quests.set(index, quest);
        }
    }

    @Override
    public void update(int d) {

        hasInActive = false;

        for (final QuestProgress i : quests) {
            if (i.isActive()) {
                i.update(d);
            } else {
                hasInActive = true;
            }
        }

    }

}
