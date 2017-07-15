// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Animation {

    public static Image[] framesFromSpriteSheet(SpriteSheet sheet, int[] frames) {
        final Image array[] = new Image[frames.length / 2];
        for (int i = 0; i < array.length; i++) {
            array[i] = sheet.getSubImage(frames[2 * i], frames[2 * i + 1]);
        }
        return array;
    }

    private final Image[] sprites;
    private final float[] durations;
    private final boolean loop;

    private final float total_duration;
    private final float[] progresses;

    private boolean active;
    private int sprite;
    private float duration_left;
    private int loops;

    public Animation(SpriteSheet sheet, int[] frames, float[] durations, boolean loop) {
        this(framesFromSpriteSheet(sheet, frames), durations, loop);
    }

    public Animation(Image[] sprites, float[] durations, boolean loop) {
        this.sprites = sprites;
        this.durations = durations;
        this.loop = loop;

        // calculate total_duration
        float sum = 0;
        for (int i = 0; i < durations.length; i++) {
            sum += durations[i];
        }
        this.total_duration = sum;

        // calculate progresses
        this.progresses = new float[sprites.length];
        float partsum = 0;
        for (int i = 0; i < durations.length; i++) {
            progresses[i] = partsum / total_duration;
            partsum += durations[i];
        }

        reset();
    }

    public Animation(Animation other) {
        this.sprites = other.sprites;
        this.durations = other.durations;
        this.loop = other.loop;
        this.total_duration = other.total_duration;
        this.progresses = other.progresses;

        reset();
    }

    public Animation copy() {
        return new Animation(this);
    }

    public void reset() {
        active = true;
        loops = 0;
        setSprite(0);
    }

    public void setSprite(int sprite) {
        if (sprite >= sprites.length && !loop) {
            active = false;
            return;
        }

        this.sprite = sprite % sprites.length;
        duration_left = durations[this.sprite];
    }

    public void setProgress(float progress) {
        for (int i = 0; i < progresses.length; i++) {
            if (progresses[i] >= progress) {
                setSprite(i);
                break;
            }
        }
    }

    public void addProgress(float duration) {
        duration_left -= duration;
        while (duration_left <= 0 && active) {
            final float overflow = duration_left;
            setSprite(sprite + 1);
            if (sprite == 0) loops += 1;
            duration_left += overflow;
        }
    }

    public Image getSprite() {
        return sprites[sprite];
    }

    public Image getSprite(int index) {
        return sprites[index];
    }

    public Image[] getSprites() {
        return sprites;
    }

    public boolean isActive() {
        return active;
    }

    // Getters

    public int getFrame() {
        return sprite;
    }

    public float getTotalDuration() {
        return total_duration;
    }

    public float getDurationLeft() {
        return duration_left;
    }

    public int getLoops() {
        return loops;
    }

}
