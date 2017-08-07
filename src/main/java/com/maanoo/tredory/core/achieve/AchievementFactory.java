// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.achieve;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.maanoo.tredory.core.InfoText;
import com.maanoo.tredory.core.achieve.Achievement.Status;
import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class AchievementFactory {

    private final Achievement achievement;

    public static AchievementFactory create(InfoText info, Predicate<Player> predicate) {

        return new AchievementFactory(new Achievement(info, null, Status.Viewable, predicate));
    }

    private AchievementFactory(Achievement achievement) {
        this.achievement = achievement;
    }

    public AchievementFactory child(InfoText info, Predicate<Player> predicate) {

        return new AchievementFactory(new Achievement(info, achievement, Status.Hidden, predicate));
    }

    public void collect(Consumer<Achievement> consumer) {
        Achievement i = achievement;
        while (i != null) {

            consumer.accept(i);

            i = i.parrent;
        }
    }

}
