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
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.instantPush;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.signletonBasic;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.signletonCircle;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.speedBoost1;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.speedBoost2;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.speedBoost3;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.speedBoost4;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellChannel1;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellChannel2;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellHoming;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellPullCoins;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellPush;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellSwap;
import static com.maanoo.tredory.core.entity.ActionsPlayerSetups.spellTeleport;
import static com.maanoo.tredory.core.entity.item.ItemType.CrystalB;
import static com.maanoo.tredory.core.entity.item.ItemType.CrystalG;
import static com.maanoo.tredory.core.entity.item.ItemType.CrystalR;
import static com.maanoo.tredory.core.entity.item.ItemType.CrystalW;
import static com.maanoo.tredory.core.entity.item.ItemType.Shield0;
import static com.maanoo.tredory.core.entity.item.ItemType.Shield2;

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
                    basic, basicArc, basicCircle, fireballCyclone1, instantPush,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4),
                    // items
                    Shield0, Shield0, CrystalR, CrystalG, CrystalB),

            new PlayerSetup(InfoText.create("Toxic"), Assets.chara.get(0), Assets.toxicball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, instantPush,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4),
                    // items
                    Shield0, Shield0, CrystalR, CrystalG, CrystalB),

            new PlayerSetup(InfoText.create("Inv"), Assets.chara.get(0), Assets.invball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, instantPush,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4),
                    // items
                    Shield0, Shield0, CrystalR, CrystalG, CrystalB),

            new PlayerSetup(InfoText.create("Speed"), Assets.chara.get(0), Assets.fireball, new ActionsPlayerSetup(
                    // primary
                    basic, basicLine1, basicCircle, fireballCyclone1, instantPush,
                    // secondary
                    spellTeleport, spellPullCoins, spellChannel1, spellChannel2, spellSwap, spellPush, spellHoming,
                    // soul
                    speedBoost1, speedBoost2, speedBoost3, speedBoost4),
                    // items
                    Shield0, Shield0, CrystalG, CrystalG, CrystalG),

            new PlayerSetup(InfoText.create("Singleton"), Assets.chara.get(0), Assets.fireball, new ActionsPlayerSetup(
                    // primary
                    basic, signletonBasic, signletonCircle, fireballCyclone1, instantPush,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4),
                    // items
                    Shield2, Shield2, CrystalW, CrystalW),

            new PlayerSetup(InfoText.create("Heva"), Assets.chara.get(0), Assets.fireball, new ActionsPlayerSetup(
                    // primary
                    basic, basicArc, basicCircle, fireballCyclone1, instantPush,
                    // secondary
                    spellTeleport, spellChannel1, spellChannel2, fireballCyclone2, spellSwap, spellPush, spellHoming,
                    // soul
                    basicSoul1, basicSoul2, basicSoul3, basicSoul4),
                    // items
                    Shield2, Shield2, Shield2, CrystalR, CrystalR, CrystalR),

    };

}
