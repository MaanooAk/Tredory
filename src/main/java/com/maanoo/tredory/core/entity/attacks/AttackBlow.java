// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.attacks;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Attack;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.AssetSet;
import com.maanoo.tredory.face.assets.Assets;
import org.newdawn.slick.SpriteSheet;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class AttackBlow extends Attack {

    private AttackProjectilesFlux attack;

    private int attacks_max = 80;
    private int attacks_left = 0;


    public AttackBlow(Team team, float attackspeed, AssetSet<SpriteSheet> sprites) {
        super(team, attackspeed);

        attack = new AttackProjectilesFlux(this.team, 1.5f, sprites, 1, 0, 0, 0.6f);
    }

    @Override
    public void activate(Core c, Point p, float angle, Effect effect) {
        super.activate(c, p, angle, effect);

        attack.attackspeed = 1.5f;
        attacks_left = attacks_max;
    }

    @Override
    public void update(int d) {
        super.update(d);

        attack.update(d);

        if (attacks_left > 0) {
            if (!attack.isActive()) {
                attack.perform(Core.c, ent, Core.c.player.ccomp.effect);

                attacks_left -= 1;
                attack.attackspeed = Ma.limit(attack.attackspeed * 1.15f, 0f, 20f);
                attack.projangleflux = (float) Math.sin(attacks_left * Math.PI / (attacks_max * 1.2)) * 40f;
            }
        }

    }

    @Override
    public boolean isActive() {
        return attacks_left > 0;
    }
}
