// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.Sprite;


/**
 * @author MaanooAk
 */
public abstract class SpriteBundleEntity extends SpriteBundle {

    public Animation idle, move, action, die, special;

    public abstract Animation getAnimation(EntityState state);

    public abstract Sprite getStaticSprite();

    @Override
    public abstract SpriteBundleEntity copy();

}
