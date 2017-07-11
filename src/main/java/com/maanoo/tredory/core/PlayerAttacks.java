// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import com.maanoo.tredory.core.entity.Action;
import com.maanoo.tredory.core.entity.AttackOld;
import com.maanoo.tredory.core.entity.Effect;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.ProjectileType;
import com.maanoo.tredory.core.entity.Spell;
import com.maanoo.tredory.core.entity.attacks.AttackProjectileArc;
import com.maanoo.tredory.core.entity.attacks.AttackProjectileCyclone;
import com.maanoo.tredory.core.entity.entities.Player;
import com.maanoo.tredory.core.utils.Ma;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.core.utils.Points;
import com.maanoo.tredory.face.assets.AssetSet;
import org.newdawn.slick.SpriteSheet;

/**
 * @author MaanooAk
 */
public class PlayerAttacks implements IUpdate {

	private ProjectileType projectile;

    public Action spellFireball1;
    public Action spellFireball2;
    public Action spellFireball3;
    public Action spellFireballCyclone1;
    public Action spellFireballCyclone2;
    public Action spellTeleport;
    public Action spellSwap;
    public Action spellChannel;
    public Action spellPush;

    public PlayerAttacks(Player player, AssetSet<SpriteSheet> projectile) {
        this.projectile = new ProjectileType(projectile);

        this.spellFireball1 = new AttackProjectileArc(player, 400 / 1.5f, 0, 250 / 1.5f, 0, this.projectile, 0.6f, 1, 0);
        this.spellFireball2 = new AttackProjectileArc(player, 400 / 0.9f, 0, 250 / 0.9f, 0, this.projectile, 0.6f, 5, 40);
        this.spellFireball3 = new AttackProjectileArc(player, 400 / 0.6f, 0, 250 / 0.6f, 0, this.projectile, 0.6f, 12, 360);
        this.spellFireballCyclone1 = new AttackProjectileCyclone(player, 400 / 2.0f, 0, 250 / 2.0f, 0, this.projectile, 0.45f, 3, 0.0f, 160, 0.0f);
        this.spellFireballCyclone2 = new AttackProjectileCyclone(player, 400 / 4.0f, 0, 250 / 4.0f, 0, this.projectile, 0.3f, 16, 1.0f, 320, 0.25f);

        //this.spellChannel = new AttackBlow(Team.Good, 0.4f, projectile);
        this.spellChannel = new AttackProjectileArc(player, 400 / 0.9f, 650 / 0.9f / 2, 250 / 0.9f, 0, this.projectile, 0.6f, 3, 20);
        
        this.spellTeleport = new Spell(player, 400 * 0.6f, 0, 200 * 0.6f, 0) {
            @Override
            public void perform() {

            	user.location.add(new Point(user.angle).mul(250));
            }
        };
        
        this.spellSwap = new Spell(player, 400 * 0.6f, 0, 200 * 0.6f, 0) {
            @Override
            public void perform() {

                Entity ent = Core.c.findClossest(user, Team.Bad); // TODO change enemy team selection

                if (ent != null) {
                	user.location.swap(ent.location);
                }
            }
        };
        
        this.spellPush = new Spell(player, 400 * 2f, 0, 200 * 2f, 0) {

			@Override
			public void perform() {
				
				Point vec = Points.create();

		        for (Entity i : Core.c.findAll(user, Team.Bad, 200)) { // TODO change enemy team selection

		            if (!i.movable) continue;

		            vec.init(i.location).sub(user.location);
		            vec.mul(10000 / Ma.pow2(vec.len()));

		            i.location.add(vec);

		        }

		        Points.dispose(vec);
			}
        	
        };

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
