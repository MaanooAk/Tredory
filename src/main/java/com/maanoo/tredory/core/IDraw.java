// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.utils.Rectangle;
import org.newdawn.slick.Graphics;

/**
 * @author MaanooAk
 */
public interface IDraw {

    void draw(Graphics g);

    default boolean needDraw(Rectangle view) {
        return true;
    }

}
