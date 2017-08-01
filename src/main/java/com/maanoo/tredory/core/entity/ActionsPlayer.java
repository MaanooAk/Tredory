// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

/**
 * TODO doc
 *
 * Actions are stored in this order: primary x5, secondary x7, special...
 *
 * @author MaanooAk
 */
public class ActionsPlayer extends ActionsSimple {

    public enum Group {

        Primary(0), Secondary(5), Soul(5 + 7);

        final int index;

        Group(int index) {
            this.index = index;
        }
    }

    public ActionsPlayer(Action... actions) {
        super(actions);
    }

    public Action get(Group group, int index) {
        return actions.get(group.index + index);
    }

    // TODO remove, use the above function
    public Action get(int group, int index) {
        switch (group) {
        default:
            return actions.get(index);
        case 1:
            return actions.get(5 + index);
        case 2:
            return actions.get(5 + 7 + index);
        }
    }

}
