// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.effect;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class Effects {

    private Effects() {}

    public static final Effect speedBoost = new Effect();

    static {

        speedBoost.speed.mul = 1.2f;

    }

}
