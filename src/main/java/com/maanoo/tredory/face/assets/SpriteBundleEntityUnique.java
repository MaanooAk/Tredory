// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.entity.EntityState;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.Sprite;
import com.maanoo.tredory.engine.SpriteSheet;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteBundleEntityUnique extends SpriteBundleEntity {

    private static final int[][] frames = new int[][] {

            { 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0 },

            { 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1 },

            { 0, 2, 1, 2, 2, 2, 3, 2, 4, 2, 5, 2 }, };

    private static final float[] duration6i = new float[] { 700, 100, 100, 100, 100, 100 };
    private static final float[] duration6 = new float[] { 100, 100, 100, 100, 100, 100 };

    public final SpriteSheet sheet;

    public SpriteBundleEntityUnique(SpriteSheet sheet) {
        this.sheet = sheet;

        idle = new Animation(sheet, frames[0], duration6i, true);
        action = new Animation(sheet, frames[1], duration6, true);
        special = new Animation(sheet, frames[2], duration6, false);
        move = null;
        die = null;

        idle.setProgress(Ra.global.unit());
    }

    @Override
    public Animation getAnimation(EntityState state) {
        switch (state) {
        case Idle:
            return idle;
        case Action:
            return action;
        case Special:
            return special;
        default:
            return null;
        }
    }

    @Override
    public Sprite getStaticSprite() {
        return idle.getSprite(0);
    }

    @Override
    public SpriteBundleEntity copy() {
        return new SpriteBundleEntityUnique(sheet);
    }

}
