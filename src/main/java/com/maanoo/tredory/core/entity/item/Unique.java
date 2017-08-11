// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.item;

import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Point;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public abstract class Unique extends Item {

    private Player user;

    private Action action;

    public Unique(String name) {
        this(name, new Point());
    }

    public Unique(String name, Point location) {
        super(ItemType.Unique, location, name);

        user = null;
    }

    public abstract Action generateAction(Player user);

    public final void setUser(Player user) {
        this.user = user;

        action = generateAction(user);
        actions.add(action);

        onPick(user);
    }

    public final void removeUser() {
        onDrop(user);

        user = null;
        action = null;
        actions.clear();
    }

    @Override
    public void update(int d) {
        super.update(d);

        if (user != null) {

            if (onUpdate(user, d)) {
                startAction(action);
            }

        }
    }

    public void onPick(Player user) {

    }

    public boolean onUpdate(Player user, int d) {

        return action.canStart();
    }

    public void onDrop(Player user) {

    }

}
