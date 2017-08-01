// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basic;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicArc;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicCircle;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicLine1;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicSoul1;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicSoul2;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicSoul3;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.basicSoul4;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.fireballCyclone1;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.fireballCyclone2;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellChannel1;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellChannel2;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellHoming;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellPullCoins;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellPush;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellSwap;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellTeleport;

import com.maanoo.tredory.core.entity.ActionsPlayerSetup;
import com.maanoo.tredory.face.assets.Assets;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class PlayerSetups {

    public static final PlayerSetup all[] = new PlayerSetup[] {

            new PlayerSetup(InfoText.create("Fire"), Assets.chara.get(0), Assets.fireball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, basic,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4)),

            new PlayerSetup(InfoText.create("Toxic"), Assets.chara.get(0), Assets.toxicball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, basic,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4)),

            new PlayerSetup(InfoText.create("Inv"), Assets.chara.get(0), Assets.invball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, basic,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4)),

            new PlayerSetup(InfoText.create("Speed"), Assets.chara.get(0), Assets.fireball, new ActionsPlayerSetup(
                    // primary
                    basic, basicLine1, basicCircle, fireballCyclone1, basic,
                    // secondary
                    spellTeleport, spellPullCoins, spellChannel1, spellChannel2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4)),

            new PlayerSetup(InfoText.create("Singleton"), Assets.chara.get(0), Assets.fireball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, basic,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4))

    };

}
