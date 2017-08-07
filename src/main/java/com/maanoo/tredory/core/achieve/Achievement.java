// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.achieve;

import java.util.function.Predicate;

import com.maanoo.tredory.core.InfoText;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.engine.Logger;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Achievement {

    public enum Status {
        Complete, Viewable, Hidden
    }

    public final InfoText info;

    public final Achievement parrent;

    public Status status;

    public final Predicate<Player> predicate;

    public Achievement(InfoText info, Achievement parrent, Status starting_status, Predicate<Player> predicate) {
        this.info = info;
        this.parrent = parrent;

        status = starting_status;

        this.predicate = predicate;
    }

    public boolean check(Player player) {
        if (predicate.test(player)) {
            status = Status.Complete;

            Logger.log("Quest", "TODO Give reward for " + info.name);

            return true;
        }
        return false;
    }

}
