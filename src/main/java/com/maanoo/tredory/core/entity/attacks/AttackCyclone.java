// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Attack;
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
 * @author MaanooAk
 */
public class AttackCyclone extends Attack {

    public final AssetSet<SpriteSheet> sprites;

    public final float projcount;
    public final float projangle;
    public final float projspeed;

    public AttackCyclone(Team team, float attackspeed, AssetSet<SpriteSheet> sprites, float projcount, float projangle, float projspeed) {
        super(team, attackspeed);
        this.sprites = sprites;
        this.projcount = projcount;
        this.projangle = projangle;
        this.projspeed = projspeed;
    }

    @Override
    @SuppressWarnings("LocalVariableHidesMemberVariable")
    public void perform(Core c, Entity ent, Effect e) {
        super.perform(c, ent, e);

        float angle = Ra.angle();

        final float attackspeed = e.attackspeed.apply(this.attackspeed);
        final float projcount = e.projcount.apply(this.projcount);
        float projangle = e.projangle.apply(this.projangle);
        final float projspeed = e.projspeed.apply(this.projspeed);

        if (projcount == 1) {

            Point start = ent.location.clone().add(new Point(angle).mul(32));

            c.ltoadd.add(Pools.obtain(Projectile.class)
                    .init(team, start, angle + 90, new SpriteBundleEntity(sprites.get()),
                            projspeed, 0.6f, attackspeed, 500));

        } else {

            if (projangle == 360) projangle -= projangle / projcount;

            float toxo = projangle;
            for (int i = 0; i < projcount; i += 1) {

                float iangle = angle + (i / (projcount - 1)) * toxo - toxo / 2;
                Point start = ent.location.clone().add(new Point(iangle).mul(32));

                c.ltoadd.add(Pools.obtain(Projectile.class)
                        .init(team, start, iangle + 90, new SpriteBundleEntity(sprites.get()),
                        projspeed, 0.6f, attackspeed, 500));

            }
        }
    }

}
