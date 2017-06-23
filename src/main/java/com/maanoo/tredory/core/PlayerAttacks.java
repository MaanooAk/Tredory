// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.Attack;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.attacks.*;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.assets.AssetSet;
import com.maanoo.tredory.face.assets.Assets;
import org.newdawn.slick.SpriteSheet;

/**
 * @author MaanooAk
 */
public class PlayerAttacks implements IUpdate {

    private AssetSet<SpriteSheet> projectile;

    public Attack spellFireball1;
    public Attack spellFireball2;
    public Attack spellFireball3;
    public Attack spellFireballCyclone1;
    public Attack spellTeleport;
    public Attack spellSwap;
    public Attack spellChannel;
    public Attack spellPush;
    public Attack spellFireballCyclone2;

    public PlayerAttacks(Player player, AssetSet<SpriteSheet> projectile) {
        this.projectile = projectile;

        this.spellFireball1 = new AttackProjectiles(Team.Good, 1.5f, projectile, 1, 0, 0.6f);
        this.spellFireball2 = new AttackProjectiles(Team.Good, 0.9f, projectile, 5, 40, 0.6f);
        this.spellFireball3 = new AttackProjectiles(Team.Good, 0.6f, projectile, 12, 360, 0.6f);
        this.spellFireballCyclone1 = new AttackCyclone(Team.Good, 2f, projectile, 3, 360, 0.45f);

        this.spellTeleport = new Spell(Team.Good, 0.6f) {
            @Override
            public void spell(Core c, Point p, float angle, Effect effect) {

                c.player.location.add(new Point(angle).mul(250));
            }
        };
        this.spellSwap = new Spell(Team.Good, 0.6f) {
            @Override
            public void spell(Core c, Point p, float angle, Effect effect) {

                Entity ent = c.findClossest(player, Team.Bad);

                if (ent != null) {
                    c.player.location.swap(ent.location);
                }
            }
        };

        this.spellChannel = new AttackBlow(Team.Good, 0.4f, projectile);

        this.spellPush = new SpellPush(Team.Good, 2f);

        this.spellFireballCyclone2 = new AttackCyclone2(Team.Good, 4f, projectile, 16, 0.30f);

    }

    public Attack getAttack(int group, int index) {
        switch (group) {
        case 0:
            switch (index) {
            case 0:
                return spellFireball1;
            case 1:
                return spellFireball2;
            case 2:
                return spellFireball3;
            case 3:
                return spellFireballCyclone1;
            }
            return null;
        case 1:
            switch (index) {
            case 0:
                return spellTeleport;
            case 1:
                return spellSwap;
            case 2:
                return spellChannel;
            case 3:
                return spellPush;
            case 4:
                return spellFireballCyclone2;
            }
            return null;
        }
        return null;
    }

    @Override
    public void update(int d) {
        // TODO why is this commented ?
        //spellFireball1.update(d);
        //spellFireball2.update(d);
        //spellFireball3.update(d);
        //spellFireballCyclone1.update(d);
//        spellTeleport.update(d);
//        spellSwap.update(d);
//        spellChannel.update(d);
    }

}
