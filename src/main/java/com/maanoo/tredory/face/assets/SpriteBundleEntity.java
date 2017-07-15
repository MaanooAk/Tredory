// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.engine.Animation;
import com.maanoo.tredory.engine.SpriteSheet;


/**
 * @author MaanooAk
 */
public class SpriteBundleEntity extends SpriteBundle {

    private static final int[][] frames = new int[][] { { 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0 },
            { 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1 }, { 0, 2, 1, 2, 2, 2, 3, 2, 4, 2, 5, 2 },
            { 0, 3, 1, 3, 2, 3, 3, 3, 4, 3, 5, 3 },
            { 0, 4, 1, 4, 2, 4, 3, 4, 4, 4, 5, 4, 0, 5, 1, 5, 2, 5, 3, 5, 4, 5, 5, 5 }, };
    private static final float[] duration6i = new float[] { 700, 100, 100, 100, 100, 100 };
    private static final float[] duration6 = new float[] { 100, 100, 100, 100, 100, 100 };
    private static final float[] duration12 = new float[] { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
            100 };

    public SpriteBundleEntity(SpriteSheet sheet) {

        idle = new Animation(sheet, frames[0], duration6i, true);
        move = new Animation(sheet, frames[1], duration6, true);
        attack = new Animation(sheet, frames[2], duration6, false);
        die = new Animation(sheet, frames[3], duration6, false);
        special = new Animation(sheet, frames[4], duration12, true);

        idle.setProgress(Ra.unit());

    }

    public SpriteBundleEntity(SpriteBundleEntity other) {
        this.idle = resetAnimation(other.idle.copy());
        this.move = resetAnimation(other.move.copy());
        this.attack = resetAnimation(other.attack.copy());
        this.die = resetAnimation(other.die.copy());
        this.special = resetAnimation(other.special.copy());

        idle.setProgress(Ra.unit());
    }

    public static Animation resetAnimation(Animation a) {
        a.reset();
        return a;
    }

    @Override
    public SpriteBundle copy() {
        return new SpriteBundleEntity(this);
    }
}
