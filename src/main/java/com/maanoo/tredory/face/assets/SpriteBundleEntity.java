// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.utils.Ra;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

/**
 * @author MaanooAk
 */
public class SpriteBundleEntity extends SpriteBundle {

    private static final int[][] frames = new int[][]{
            {0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0},
            {0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1},
            {0, 2, 1, 2, 2, 2, 3, 2, 4, 2, 5, 2},
            {0, 3, 1, 3, 2, 3, 3, 3, 4, 3, 5, 3},
            {0, 4, 1, 4, 2, 4, 3, 4, 4, 4, 5, 4,
                    0, 5, 1, 5, 2, 5, 3, 5, 4, 5, 5, 5},
    };
    private static final int[] duration6i = new int[]{700, 100, 100, 100, 100, 100};
    private static final int[] duration6 = new int[]{100, 100, 100, 100, 100, 100};
    private static final int[] duration12 = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};

    public SpriteBundleEntity(SpriteSheet sheet) {

        idle = loadAnimation(sheet, frames[0], duration6i);
        move = loadAnimation(sheet, frames[1], duration6);
        attack = loadAnimation(sheet, frames[2], duration6);
        die = loadAnimation(sheet, frames[3], duration6);
        special = loadAnimation(sheet, frames[4], duration12);

        idle.setCurrentFrame(Ra.range(idle.getFrameCount()));
        attack.setLooping(false);
        die.setLooping(false);

    }

    private static Animation loadAnimation(SpriteSheet sheet, int[] frames, int[] duration) {
        Animation a = new Animation(sheet, frames, duration);
        a.setAutoUpdate(false);
        return a;
    }

    public SpriteBundleEntity(SpriteBundleEntity other) {
        this.idle = resetAnimation(other.idle.copy());
        this.move = resetAnimation(other.move.copy());
        this.attack = resetAnimation(other.attack.copy());
        this.die = resetAnimation(other.die.copy());
        this.special = resetAnimation(other.special.copy());

        idle.setCurrentFrame(Ra.range(idle.getFrameCount()));
    }

    public static Animation resetAnimation(Animation a) {
        a.restart();
        return a;
    }

    @Override
    public SpriteBundle copy() {
        return new SpriteBundleEntity(this);
    }
}
