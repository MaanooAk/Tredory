// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entities.Player;
import com.maanoo.tredory.face.assets.Assets;

/**
 * @author MaanooAk
 */
public class PlayerAttacks implements IUpdate {

    public AttackProjectiles spellFireball1;
    public AttackProjectiles spellFireball2;
    public AttackProjectiles spellFireball3;
    public AttackCyclone spellFireballCyclone1;
    public AttackUtil spellTeleport;
    public AttackUtil spellSwap;

    public PlayerAttacks(Player player) {

        this.spellFireball1 = new AttackProjectiles(Team.Good, 1.5f, Assets.fireball, 1, 0, 0.6f);
        this.spellFireball2 = new AttackProjectiles(Team.Good, 0.9f, Assets.fireball, 5, 40, 0.6f);
        this.spellFireball3 = new AttackProjectiles(Team.Good, 0.6f, Assets.fireball, 12, 360, 0.6f);
        this.spellFireballCyclone1 = new AttackCyclone(Team.Good, 2f, Assets.fireball, 3, 360, 0.45f);

        this.spellTeleport = new AttackUtil(Team.Good, 0.6f) {
            @Override
            public void activate(Core c, Point p, float angle, Effect effect) {
                super.activate(c, p, angle, effect);

                c.player.location.add(new Point(angle).mul(250));
            }
        };
        this.spellSwap = new AttackUtil(Team.Good, 0.6f) {
            @Override
            public void activate(Core c, Point p, float angle, Effect effect) {
                super.activate(c, p, angle, effect);

                Entity ent = c.findClossest(player, Team.Bad);

                if (ent != null) {
                    c.player.location.swap(ent.location);
                }
            }
        };


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
            }
            return null;
        }
        return null;
    }

    @Override
    public void update(int d) {
        //spellFireball1.update(d);
        //spellFireball2.update(d);
        //spellFireball3.update(d);    
        //spellFireballCyclone1.update(d);
        spellTeleport.update(d);
        spellSwap.update(d);
    }

}
