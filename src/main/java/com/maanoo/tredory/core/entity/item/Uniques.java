// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.item;

import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.actions.AttackProjectileHoming;
import com.maanoo.tredory.core.entity.actions.Spell;
import com.maanoo.tredory.core.entity.entities.Player;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Uniques {

    public static class Heart extends Unique {

        public Heart() {
            super("Heart");
        }

        @Override
        public Action generateAction(Player user) {
            return new AttackProjectileHoming(user, 400, 0, 200, 4000, user.projectile, 0.6f, 2, 0.7f, true) {

                {
                    affected_by_effects = false;
                    projectile_lifetime = 4000;
                }
            };
        }

    }

    public static class EnergyShield extends Unique {

        public EnergyShield() {
            super("EnergyShield");
        }

        @Override
        public Action generateAction(Player user) {
            return new Spell(user, 400, 0, 200, 4000) {

                @Override
                public void perform() {
                    super.perform();

                    final Player user = (Player) this.user;

                    if (user.canTakeShiled()) {
                        user.takeItem(new Item(ItemType.Shield0));
                    }
                }

            };
        }

    }

}
