// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.ActionsPlayerSetup.ActionSuppilier;
import com.maanoo.tredory.core.entity.actions.AttackProjectileArc;
import com.maanoo.tredory.core.entity.actions.AttackProjectileBlow;
import com.maanoo.tredory.core.entity.actions.AttackProjectileCyclone;
import com.maanoo.tredory.core.entity.actions.AttackProjectileHoming;
import com.maanoo.tredory.core.entity.actions.AttackProjectileLine;
import com.maanoo.tredory.core.entity.actions.Spell;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class ActionsPlayerSetups {

    // TODO change, tmp

    public static final ActionSuppilier basic, basicArc, basicCircle, fireballCyclone1, fireballCyclone2, spellChannel1,
            spellChannel2, basicLine1, spellTeleport, spellSwap, spellPush, spellHoming, basicSoul1, basicSoul2,
            basicSoul3, basicSoul4, spellPullCoins;

    static {

        basic = (p) -> new AttackProjectileArc(p, 400 / 1.5f, 0, 250 / 1.5f, 0, p.projectile, 0.6f, 1, 0);

        basicArc = (p) -> new AttackProjectileArc(p, 400 / 0.9f, 0, 250 / 0.9f, 0, p.projectile, 0.6f, 5, 40);
        basicLine1 = (p) -> new AttackProjectileLine(p, 400 / 0.9f, 0, 250 / 0.9f, 0, p.projectile, 0.6f, 5, 100);

        basicCircle = (p) -> new AttackProjectileArc(p, 400 / 0.5f, 400 / 4.0f, 250 / 0.6f, 0, p.projectile, 0.6f, 16,
                360) {

            @Override
            public void recharge() {
                super.recharge();
                if (perform_count >= 2) stopRecharge();
            }
        };

        fireballCyclone1 = (p) -> new AttackProjectileCyclone(p, 400 / 2.0f, 0, 250 / 2.0f, 0, p.projectile, 0.45f, 3,
                0.0f, 160, 0.0f);
        fireballCyclone2 = (p) -> new AttackProjectileCyclone(p, 400 / 4.0f, 0, 250 / 4.0f, 0, p.projectile, 0.3f, 16,
                1.0f, 320, 0.25f);

        spellChannel1 = (p) -> new AttackProjectileBlow(p, 400 / 1.0f, 400 / 1.0f, 250 / 0.9f, 0, p.projectile, 0.6f, 5,
                0.34f, 40, 0);

        spellChannel2 = (p) -> new AttackProjectileBlow(p, 400 / 1.0f, 400 / 1.0f, 250 / 0.9f, 0, p.projectile, 0.6f, 1,
                0.5f, 0, 15);

        spellTeleport = (p) -> new Spell(p, 400 / 1.0f, 0, 200 / 2.2f, 0) {

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

        spellSwap = (p) -> new Spell(p, 400 * 0.6f, 0, 200 * 0.6f, 0) {

            @Override
            public void perform() {

                final Entity ent = Core.c.findClossestEnemy(user, p.team); // TODO change enemy team selection

                if (ent != null) {
                    user.location.swap(ent.location);
                }
            }
        };

        spellPush = (p) -> new Spell(p, 400 * 2f, 0, 200 * 2f, 0) {

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

        spellPullCoins = (p) -> new Spell(p, 400 * 3f, 400 * 0.5f, 200 * 1f, 0) {

            private float speed;

            @Override
            public void perform() {
                super.perform();
                speed = 1;
            }

            @Override
            public void update(int d) {
                super.update(d);

                if (active) {

                    for (final Entity i : Core.c.findItems(p.location, Integer.MAX_VALUE)) {

                        if (!i.pickable) continue;

                        final float dis = p.location.distance(i.location);

                        if (dis > speed * d) {
                            i.location.addAngled(p.location.distanceAngle(i.location), speed * d);
                        } else if (dis > 52) {
                            i.location.addAngled(p.location.distanceAngle(i.location), 52 - dis + 3);
                        }

                    }

                }
            }

        };

        spellHoming = (p) -> new AttackProjectileHoming(p, 400 / 1.5f, 400 / 10.5f, 250 / 1.5f, 0, p.projectile, 0.6f,
                10, 0.35f);

        basicSoul1 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 3, 0.25f);
        basicSoul2 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 6, 0.28f);
        basicSoul3 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 9, 0.31f);
        basicSoul4 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 11, 0.34f);

    }

}
