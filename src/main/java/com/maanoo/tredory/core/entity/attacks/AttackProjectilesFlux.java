// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.AttackOld;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.entities.Projectile;
import com.maanoo.tredory.core.memory.Pools;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Ra;
import com.maanoo.tredory.face.SpriteBundleEntity;
import com.maanoo.tredory.face.assets.AssetSet;
import org.newdawn.slick.SpriteSheet;

/**
 * TODO move extend to the new Action base class
 *
 * @author MaanooAk
 */
public class AttackProjectilesFlux extends AttackOld {

    public final AssetSet<SpriteSheet> sprites;

    public float projcount;
    public float projangle;
    public float projangleflux;
    public float projspeed;

    public AttackProjectilesFlux(Team team, float attackspeed, AssetSet<SpriteSheet> sprites, float projcount, float projangle, float projangleflux, float projspeed) {
        super(team, attackspeed);
        this.sprites = sprites;
        this.projcount = projcount;
        this.projangle = projangle;
        this.projangleflux = projangleflux;
        this.projspeed = projspeed;
    }

    @Override
    @SuppressWarnings("LocalVariableHidesMemberVariable")
    public void start(Entity ent, Effect e) {
        super.start(ent, e);

        final float attackspeed = e.attackspeed.apply(this.attackspeed);
        final float projcount = e.projcount.apply(this.projcount);
        float projangle = e.projangle.apply(this.projangle);
        final float projspeed = e.projspeed.apply(this.projspeed);

        if (projcount == 1) {

            float angle = ent.angle + Ra.range(-projangleflux, projangleflux);

            Point start = ent.location.clone().add(new Point(angle).mul(32));

            Core.addEntity(Pools.obtain(Projectile.class)
                    .init(team, start, angle, new SpriteBundleEntity(sprites.get()),
                    projspeed, 0, attackspeed, 1500));

        } else {

            if (projangle == 360) projangle -= projangle / projcount;

            float toxo = projangle;
            for (int i = 0; i < projcount; i += 1) {

                float angle = ent.angle + Ra.range(-projangleflux, projangleflux);

                float iangle = angle + (i / (projcount - 1)) * toxo - toxo / 2;
                Point start = ent.location.clone().add(new Point(iangle).mul(32));

                Core.addEntity(Pools.obtain(Projectile.class)
                        .init(team, start, iangle, new SpriteBundleEntity(sprites.get()),
                        projspeed, 0, attackspeed, 1500));

            }
        }
    }


}
