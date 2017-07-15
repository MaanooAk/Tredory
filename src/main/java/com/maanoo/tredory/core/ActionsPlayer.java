// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.Actions;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.actions.AttackProjectileArc;
import com.maanoo.tredory.core.entity.actions.AttackProjectileBlow;
import com.maanoo.tredory.core.entity.actions.AttackProjectileCyclone;
import com.maanoo.tredory.core.entity.actions.AttackProjectileHoming;
import com.maanoo.tredory.core.entity.actions.AttackProjectileLine;
import com.maanoo.tredory.core.entity.actions.Spell;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.engine.SpriteSheet;
import com.maanoo.tredory.face.assets.AssetSet;


/**
 * @author MaanooAk
 */
public class ActionsPlayer extends Actions {

    private ProjectileType projectile;

    public Action spellFireball1;
    public Action spellFireball2;
    public Action spellFireball3;
    public Action spellFireballCyclone1;
    public Action spellFireballCyclone2;
    public Action spellTeleport;
    public Action spellSwap;
    public Action spellChannel1;
    public Action spellChannel2;
    public Action spellPush;
    public Action spellHoming;

    public Action[] soulAttacks;

    public ActionsPlayer(Player player, AssetSet<SpriteSheet> projectile_sprite) {
        this.projectile = new ProjectileType(projectile_sprite);

        this.spellFireball1 = new AttackProjectileArc(player, 400 / 1.5f, 0, 250 / 1.5f, 0, projectile, 0.6f, 1, 0);
        this.spellFireball2 = new AttackProjectileArc(player, 400 / 0.9f, 0, 250 / 0.9f, 0, projectile, 0.6f, 5, 40);
        this.spellFireball3 = new AttackProjectileArc(player, 400 / 0.5f, 400 / 4.0f, 250 / 0.6f, 0, projectile, 0.6f,
                16, 360) {

            @Override
            public void recharge() {
                super.recharge();
                if (perform_count >= 2) stopRecharge();
            }
        };

        this.spellFireballCyclone1 = new AttackProjectileCyclone(player, 400 / 2.0f, 0, 250 / 2.0f, 0, projectile,
                0.45f, 3, 0.0f, 160, 0.0f);
        this.spellFireballCyclone2 = new AttackProjectileCyclone(player, 400 / 4.0f, 0, 250 / 4.0f, 0, projectile, 0.3f,
                16, 1.0f, 320, 0.25f);

        this.spellChannel1 = new AttackProjectileBlow(player, 400 / 1.0f, 400 / 1.0f, 250 / 0.9f, 0, projectile, 0.6f,
                5, 40);
        this.spellChannel2 = new AttackProjectileLine(player, 400 / 0.9f, 0, 250 / 0.9f, 0, projectile, 0.6f, 5, 100);

        this.spellTeleport = new Spell(player, 400 / 1.0f, 0, 200 / 2.2f, 0) {

            private boolean active = false;
            private float angle;
            private float speed;

            @Override
            public void perform() {

                active = true;
                angle = user.angle;
                speed = 270f / getStateTime(Action.State.Ending);
            }

            @Override
            public void end() {
                super.end();

                active = false;
            }

            @Override
            public void update(int d) {
                super.update(d);

                if (active) user.location.addAngled(angle, speed * d);
            }

        };

        this.spellSwap = new Spell(player, 400 * 0.6f, 0, 200 * 0.6f, 0) {

            @Override
            public void perform() {

                final Entity ent = Core.c.findClossestEnemy(user, player.team); // TODO change enemy team selection

                if (ent != null) {
                    user.location.swap(ent.location);
                }
            }
        };

        this.spellPush = new Spell(player, 400 * 2f, 0, 200 * 2f, 0) {

            @Override
            public void perform() {

                final Point vec = Points.create();

                for (final Entity i : Core.c.findAll(user, Team.Bad, 200)) { // TODO change enemy team selection

                    if (!i.movable) continue;

                    vec.init(i.location).sub(user.location);
                    vec.mul(10000 / Ma.pow2(vec.len()));

                    i.location.add(vec);

                }

                Points.dispose(vec);
            }

        };

        spellHoming = new AttackProjectileHoming(player, 400 / 1.5f, 400 / 10.5f, 250 / 1.5f, 0, projectile, 0.6f, 10,
                0.35f);

        soulAttacks = new Action[4];
        soulAttacks[0] = new AttackProjectileHoming(player, 0, 0, 0, 0, projectile, 0.6f, 3, 0.25f);
        soulAttacks[1] = new AttackProjectileHoming(player, 0, 0, 0, 0, projectile, 0.6f, 6, 0.28f);
        soulAttacks[2] = new AttackProjectileHoming(player, 0, 0, 0, 0, projectile, 0.6f, 9, 0.31f);
        soulAttacks[3] = new AttackProjectileHoming(player, 0, 0, 0, 0, projectile, 0.6f, 11, 0.34f);

    }

    public Action getAttack(int group, int index) {
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
                return spellChannel1;
            case 2:
                return spellChannel2;
            case 3:
                return spellPush;
            case 4:
                return spellFireballCyclone2;
            case 5:
                return spellSwap;
            case 6:
                return spellHoming;
            }
            return null;
        }
        return null;
    }

    @Override
    public void update(int d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Action action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Action get() {
        return spellFireball1;
    }

    @Override
    public Action getReady() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Action getActive() {
        throw new UnsupportedOperationException();
    }

}
