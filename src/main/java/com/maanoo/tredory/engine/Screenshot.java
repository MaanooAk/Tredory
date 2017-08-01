// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class Screenshot {

    private Screenshot() {}

    public static void capture(GameContainer gc) {
        try {

            final Image image = new Image(gc.getWidth(), gc.getHeight());
            gc.getGraphics().copyArea(image, 0, 0);

            final String name = Long.toString(System.currentTimeMillis()) + ".png";

            ImageOut.write(image, name);

            Logger.log("Screenshot", "Captured at " + name);

        } catch (final SlickException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
