// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

/**
 * @author MaanooAk
 */
public enum EntityState {

    Idle() {

    },

    Move() {

        @Override
        public boolean canMove() {
            return true;
        }
    },

    Action() {

        @Override
        public boolean canInterrupt() {
            return false;
        }

    },

    Work() {

    },

    Die() {

        @Override
        public boolean canInterrupt() {
            return false;
        }

    },

    Special() {

        @Override
        public boolean canInterrupt() {
            return false;
        }

    };

    public boolean canMove() {
        return false;
    };

    public boolean canInterrupt() {
        return true;
    };
}
