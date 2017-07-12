// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity.entities;

import java.util.ArrayList;

import com.maanoo.tredory.core.Core;
import com.maanoo.tredory.core.Stats;
import com.maanoo.tredory.core.Team;
import com.maanoo.tredory.core.entity.Entity;
import com.maanoo.tredory.core.entity.actions.AttackMelee;
import com.maanoo.tredory.core.entity.brains.AnimalBrain;
import com.maanoo.tredory.core.utils.Point;
import com.maanoo.tredory.face.SpriteBundleEntity;


/**
 * @author MaanooAk
 */
public class Animal extends Entity {

    private final float lspeed_base = 0.2f;
    public float lspeed;

    public final AnimalBrain brain;

    public Animal(Team team, Point location, float angle, SpriteBundleEntity sprites, float atackSpeedMul,
            float speedMul) {
        super(team, location, angle, sprites);

        actions.add(new AttackMelee(this, 400 / atackSpeedMul, 0, 200 / atackSpeedMul, 0, 50, 70));

        lspeed = lspeed_base * speedMul;

        brain = new AnimalBrain(this);
    }

    @Override
    public void update(int d) {
        super.update(d);

        brain.update(d);

    }

    @Override
    public void drops() {
        super.drops();

        Core.c.addItem(this, 0);
    }

    // TODO add the source of the damage
    @Override
    public void takeDamage() {
        super.takeDamage();

        // alert all near by allies when it takes damage
        // TODO find all allies not teammates
        final ArrayList<Entity> l = Core.c.findAll(this, team, 250);
        for (final Entity i : l) {
            i.alerted = true;
        }
    }

    @Override
    public void die() {
        super.die();

        // TODO credit the killing blow giver not the player
        if (team != Team.Good) {
            Stats.addKilled(this);

            // TODO give soul to the clossest soul collector
            Core.c.player.souls.addSoul();
        }
    }

}
