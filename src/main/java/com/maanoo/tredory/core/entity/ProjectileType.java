// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import org.newdawn.slick.SpriteSheet;

import com.maanoo.tredory.face.assets.AssetSet;

/**
 * @author MaanooAk
 */
public final class ProjectileType {

	private final AssetSet<SpriteSheet> sprites;

	public ProjectileType(AssetSet<SpriteSheet> sprites) {
		this.sprites = sprites;
	}
	
	public SpriteSheet getSpriteSheet() {
		return sprites.get();
	}
	
}
