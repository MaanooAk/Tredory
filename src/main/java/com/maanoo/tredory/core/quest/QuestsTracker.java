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

    public final int maxMaxQuests;
    public int maxQuests;

    public final ArrayList<QuestProgress> quests;

    private transient boolean hasInActive = false;

    public QuestsTracker() {

        maxMaxQuests = 5;
        maxQuests = 0;

        quests = new ArrayList<>(maxMaxQuests);

    }

    public void increaseCapacity() {
        if (maxQuests < maxMaxQuests) {
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
