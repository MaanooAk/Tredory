// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.engine.SpriteSheet;
import com.maanoo.tredory.face.assets.AssetSet;
import com.maanoo.tredory.face.assets.SpriteBundleEntity;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class PlayerSetup {

    public final SpriteSheet body;

    public final AssetSet<SpriteSheet> projectile;

    public PlayerSetup(SpriteSheet body, AssetSet<SpriteSheet> projectile) {
        this.body = body;
        this.projectile = projectile;
    }

    public Player createPlayer() {
        final Player p = new Player(Team.Good, new Point(), 180, new SpriteBundleEntity(body));
        p.projectile = projectile;
        return p;
    }

}
