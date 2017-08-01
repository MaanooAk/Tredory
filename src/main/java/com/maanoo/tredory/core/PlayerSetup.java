// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.ActionsPlayerSetup;
import com.maanoo.tredory.core.entity.ProjectileType;
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
public class PlayerSetup implements ISetup<Player> {

    public final InfoText info;

    public final SpriteSheet body;

    public final AssetSet<SpriteSheet> projectile;

    public final ActionsPlayerSetup actions;

    public PlayerSetup(InfoText info, SpriteSheet body, AssetSet<SpriteSheet> projectile, ActionsPlayerSetup actions) {
        this.info = info;
        this.body = body;
        this.projectile = projectile;
        this.actions = actions;
    }

    public Player create() {
        final Player p = new Player(Team.Good, new Point(), 180, new SpriteBundleEntity(body));
        p.projectile = new ProjectileType(projectile);
        p.actions = actions.create(p);
        return p;
    }

}