// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets.atlas;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class AtlasUtils {

    private static int getMaxTextureSize_value = -1;

    public static int getMaxTextureSize() {
        if (getMaxTextureSize_value == -1) {
            getMaxTextureSize_value = calcMaxTextureSize();
        }
        return getMaxTextureSize_value;
    }

    private static int calcMaxTextureSize() {
        final IntBuffer temp = BufferUtils.createIntBuffer(16);
        Renderer.get().glGetInteger(SGL.GL_MAX_TEXTURE_SIZE, temp);
        return temp.get(0);
    }

}
