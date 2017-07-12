// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.IUpdate;


/**
 * @author MaanooAk
 */
public abstract class Brain implements IUpdate {

    protected float intelligence;

    public Brain(float intelligence) {
        this.intelligence = intelligence;
    }

}
