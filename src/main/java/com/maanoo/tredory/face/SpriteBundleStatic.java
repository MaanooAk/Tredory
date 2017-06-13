package com.maanoo.tredory.face;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 *
 * @author Akritas
 */
public class SpriteBundleStatic extends SpriteBundle {

    public final Image image;
    
    public SpriteBundleStatic(Image image) {
        
        this.image = image;
        
        this.idle = new Animation(new Image[]{image}, 1000);
        
    }

    @Override
    public SpriteBundle copy() {
        return new SpriteBundleStatic(image);
    }

}
