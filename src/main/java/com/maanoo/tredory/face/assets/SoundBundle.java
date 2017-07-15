// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import org.newdawn.slick.Sound;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.utils.Point;


/**
 * @author MaanooAk
 */
public class SoundBundle {

    public final AssetSet<Sound> sound;

    public SoundBundle(AssetSet<Sound> sound) {
        this.sound = sound;
    }

    public void play() {
        sound.get().play(1f, Op.sound);
    }

    public void playAt(Point p) {
        final float dx = p.x - Core.c.camera.x, dy = p.y - Core.c.camera.y;
        final float a = limit(1f - sigma(abs(dx) + abs(dy)), 0, 1);
        final float x = sigma(dx), y = sigma(dy), z = a * 1;

        sound.get().playAt(1f, a * Op.sound, x, y, z);
    }

    private static float sigma(float x) {
        x = x / 64f;
        return x / (1f + abs(x));
    }

    private static float abs(float x) {
        return x < 0 ? -x : x;
    }

    private static float limit(float x, float a, float b) {
        return x < a ? a : x > b ? b : x;
    }

}
