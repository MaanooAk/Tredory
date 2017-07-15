// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.map;

import com.maanoo.tredory.core.utils.Point;
import org.newdawn.slick.Color;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Spot extends Point {

    public final float r;
    public Color color = Color.darkGray;

    public Spot(float x, float y, float r) {
        super(x, y);
        this.r = r;
    }

}
