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
import com.maanoo.tredory.core.entity.actions.AttackProjectileSigleton;
import com.maanoo.tredory.core.entity.actions.EffectGiver;
import com.maanoo.tredory.core.entity.actions.Instant;
import com.maanoo.tredory.core.entity.actions.Spell;
import com.maanoo.tredory.core.entity.effect.Effects;
import com.maanoo.tredory.core.utils.Ma;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class ActionsPlayerSetups {

    // TODO change, tmp

    public static final ActionSuppilier basic, basicArc, basicCircle, fireballCyclone1, fireballCyclone2, spellChannel1,
            spellChannel2, basicLine1, spellTeleport, spellSwap, spellPush, spellHoming, basicSoul1, basicSoul2,
            basicSoul3, basicSoul4, spellPullCoins, speedBoost1, speedBoost2, speedBoost3, speedBoost4, instantPush,
            signletonBasic, signletonCircle;

    static {

        basic = (p) -> new AttackProjectileArc(p, 400 / 1.5f, 0, 250 / 1.5f, 0, p.projectile, 0.6f, 1, 0);

        basicArc = (p) -> new AttackProjectileArc(p, 400 / 0.9f, 0, 250 / 0.9f, 0, p.projectile, 0.6f, 5, 40);
        basicLine1 = (p) -> new AttackProjectileLine(p, 400 / 0.9f, 0, 250 / 0.9f, 0, p.projectile, 0.6f, 5, 100);

        basicCircle = (p) -> new AttackProjectileArc(p, 400 / 0.5f, 400 / 4.0f, 250 / 0.4f, 1000, p.projectile, 0.6f,
                16, 360) {

            @Override
            public void recharge() {
                super.recharge();
                if (perform_count >= 2) stopRecharge();
            }
        };

        fireballCyclone1 = (p) -> new AttackProjectileCyclone(p, 400 / 2.0f, 400 / 2.0f, 250 / 2.0f, 0, p.projectile,
                0.45f, 3, 0.0f, 160, 0.0f);
        fireballCyclone2 = (p) -> new AttackProjectileCyclone(p, 400 / 2.0f, 400 / 4.0f, 250 / 4.0f, 4000, p.projectile,
                0.3f, 16, 1.0f, 320, 0.25f);

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

        spellSwap = (p) -> new Spell(p, 400 * 0.6f, 0, 200 * 0.6f, 3000) {

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

                Core.c.findAll(user, Team.Bad, 200, (i) -> { // TODO change enemy team selection

                    if (!i.movable) return;

                    final float dis = p.location.distance(i.location);
                    i.location.addAngled(i.location.distanceAngle(p.location), 10000 / Ma.pow2(dis));

                });

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

                    Core.c.findItems(p.location, Integer.MAX_VALUE, (i) -> {

                        if (!i.pickable) return;

                        final float dis = p.location.distance(i.location);

                        if (dis > speed * d) {
                            i.location.addAngled(p.location.distanceAngle(i.location), speed * d);
                        } else if (dis > 52) {
                            i.location.addAngled(p.location.distanceAngle(i.location), 52 - dis + 3);
                        }

                    });

                }
            }

        };

        spellHoming = (p) -> new AttackProjectileHoming(p, 400 / 1.5f, 400 / 10.5f, 250 / 1.5f, 0, p.projectile, 0.6f,
                10, 0.35f, true);

        basicSoul1 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 3, 0.25f, false);
        basicSoul2 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 6, 0.28f, false);
        basicSoul3 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 9, 0.31f, false);
        basicSoul4 = (p) -> new AttackProjectileHoming(p, 0, 0, 0, 0, p.projectile, 0.6f, 11, 0.34f, false);

        speedBoost1 = (p) -> new EffectGiver(p, Effects.speedBoost, 1000);
        speedBoost2 = (p) -> new EffectGiver(p, Effects.speedBoost, 1500);
        speedBoost3 = (p) -> new EffectGiver(p, Effects.speedBoost, 2000);
        speedBoost4 = (p) -> new EffectGiver(p, Effects.speedBoost, 1500);

        instantPush = (p) -> new Instant(p, 10_000) {

            private static final float effect_distance = 78;
            private static final float effect_duration = 600;

            private float duration_left = 0;

            @Override
            public void perform() {

                duration_left = effect_duration;
            }

            @Override
            public void update(int d) {
                super.update(d);

                if (duration_left > 0) {

                    Core.c.findAll(user, Team.Bad, effect_distance, (i) -> {

                        if (!i.movable) return;

                        final float dis = p.location.distance(i.location);
                        i.location.addAngled(i.location.distanceAngle(p.location), effect_distance - dis);

                    });

                    duration_left -= d;
                }

            }

        };

        signletonBasic = (p) -> new AttackProjectileSigleton(p, 400 / 1.5f, 400 / 8f, 250 / 1.5f, 0, p.projectile, 0.6f,
                1, 0, 3);
        signletonCircle = (p) -> new AttackProjectileSigleton(p, 400 / 1.5f, 400 / 8f, 250 / 1.5f, 1000, p.projectile,
                0.6f, 1, 0, 8);

    }

}
