// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.engine.Sprite;
import com.maanoo.tredory.engine.SpriteSheet;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class SpriteBunldeProgress extends SpriteBundle {

    private final SpriteSheet sheet;

    public SpriteBunldeProgress(SpriteSheet sheet) {
        this.sheet = sheet;
    }

    public Sprite get() {
        return sheet.getSprite(0, 0);
    }

    public Sprite get(int i, float progress) {
        return sheet.getSprite(Ma.round(progress * (sheet.getHCount() - 1)), i);
    }

    @Override
    public SpriteBundle copy() {
        // TODO Auto-generated method stub
        return new SpriteBunldeProgress(sheet);
    }

}
