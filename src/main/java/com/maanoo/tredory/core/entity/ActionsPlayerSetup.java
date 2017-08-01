// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.ISetup;
import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * Actions are stored in this order: primary x5, secondary x7, special...
 *
 * @author MaanooAk
 */
public class ActionsPlayerSetup implements ISetup<ActionsPlayer> {

    @FunctionalInterface
    public abstract static interface ActionSuppilier {

        Action get(Player t);
    }

    public final ActionSuppilier[] suppliers;

    public ActionsPlayerSetup(ActionSuppilier... suppliers) {
        this.suppliers = suppliers;
    }

    public ActionsPlayer create(Player player) {
        final Action[] actions = new Action[suppliers.length];

        for (int i = 0; i < suppliers.length; i++) {
            actions[i] = suppliers[i].get(player);
        }

        return new ActionsPlayer(actions);
    }

}
