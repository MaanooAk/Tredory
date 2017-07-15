// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.imageout.ImageOut;

import com.maanoo.tredory.Op;
import com.maanoo.tredory.Res;
import com.maanoo.tredory.core.entity.entities.ItemType;
import com.maanoo.tredory.engine.Logger;
import com.maanoo.tredory.engine.SpriteSheet;
import com.maanoo.tredory.engine.SpriteSheetMasked;
import com.maanoo.tredory.engine.SpriteSheetSingle;
import com.maanoo.tredory.face.assets.atlas.Atlas;


/**
 * @author MaanooAk
 */
public class Assets {

    private static final boolean USE_ATLAS = true;

    public static Atlas atlas;

    private static ArrayList<String> imagePaths;

    public static Image icon;
    public static SpriteSheetSingle cursor;
    public static SpriteSheetFont font1;

    public static AssetSet<SpriteSheet> chara;
    public static AssetSet<SpriteSheet> box;
    public static AssetSet<SpriteSheet> boxg;
    public static AssetSet<SpriteSheet> altar;
    public static AssetSet<SpriteSheet> portal;

    public static AssetSet<SpriteSheet> fireball;
    public static AssetSet<SpriteSheet> invball;
    public static AssetSet<SpriteSheet> toxicball;

    public static AssetSet<SpriteSheet> flameblast;

    public static ArrayList<Color> colors;

    public static AssetSet<SpriteSheet> axeman;
    public static AssetSet<SpriteSheet> maceman;
    public static AssetSet<SpriteSheet> swordman;

    public static AssetSet<SpriteSheet> slime;

    public static SpriteSheetSingle items_sheet;
    public static AssetSet<Image> items[];

    public static AssetSet<Image> terrain_small;
    public static AssetSet<Image> terrain_glyphs;
    public static AssetSet<Image> terrain_steps;

    public static SpriteSheetSingle effects_sheet;
    public static Image effect_arrow;
    public static Image effect_frenzy;

    // ===

    public static SoundBundle trans;
    public static SoundBundle hit;
    public static SoundBundle bam;
    public static SoundBundle pick;

    public static void load1() {

        atlas = new Atlas();

        // TODO automate;
        imagePaths = new ArrayList<>();
        imagePaths.add("data/icon.png");
        imagePaths.add("data/cursor.png");
        imagePaths.add("data/fonts/font1.png");
        imagePaths.add("data/sprites/items.png");
        imagePaths.add("data/sprites/terrain/small.png");
        imagePaths.add("data/sprites/terrain/glyphs.png");
        imagePaths.add("data/sprites/terrain/steps.png");
        imagePaths.add("data/sprites/effects.png");
        imagePaths.add("data/sprites/entities/char1.png");
        imagePaths.add("data/sprites/entities/fireball1.png");
        imagePaths.add("data/sprites/entities/box1.png");
        imagePaths.add("data/sprites/entities/char1.png");
        imagePaths.add("data/sprites/entities/box1.png");
        imagePaths.add("data/sprites/entities/box2.png");
        imagePaths.add("data/sprites/entities/box3.png");
        imagePaths.add("data/sprites/entities/boxg1.png");
        imagePaths.add("data/sprites/entities/altar1.png");
        imagePaths.add("data/sprites/entities/portal1.png");
        imagePaths.add("data/sprites/entities/slime1.png");
        imagePaths.add("data/sprites/entities/fireball1.png");
        imagePaths.add("data/sprites/entities/toxicball1.png");
        imagePaths.add("data/sprites/entities/flameblast1.png");
        imagePaths.add("data/sprites/entities/axeman1.png");
        imagePaths.add("data/sprites/entities/axeman1m.png");
        imagePaths.add("data/sprites/entities/axeman2.png");
        imagePaths.add("data/sprites/entities/axeman2m.png");
        imagePaths.add("data/sprites/entities/maceman1.png");
        imagePaths.add("data/sprites/entities/maceman1m.png");
        imagePaths.add("data/sprites/entities/maceman2.png");
        imagePaths.add("data/sprites/entities/maceman2m.png");
        imagePaths.add("data/sprites/entities/swordman1.png");
        imagePaths.add("data/sprites/entities/swordman1m.png");

    }

    public static void load2() {

        for (final String i : imagePaths) {
            atlas.add(i);
        }

        if (USE_ATLAS) {
            try {
                final Image atlas_image = atlas.getAtlas();
                if (Op.debug) ImageOut.write(atlas_image, "atlas.png");
            } catch (final SlickException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void load3() {

        icon = atlas.get("data/icon.png");
        cursor = new SpriteSheetSingle(atlas.get("data/cursor.png"), 16, 16);
        font1 = new SpriteSheetFont(new org.newdawn.slick.SpriteSheet(atlas.get("data/fonts/font1.png"), 8, 12), '\0');

        // ===

        chara = loadSpriteSheetSet("char");
        box = loadSpriteSheetSet("box");
        boxg = loadSpriteSheetSet("boxg");
        altar = loadSpriteSheetSet("altar");
        portal = loadSpriteSheetSet("portal");
        slime = loadSpriteSheetSet("slime");

        fireball = loadSpriteSheetSet("fireball");
        invball = loadSpriteSheetSet("fireinvball");
        toxicball = loadSpriteSheetSet("toxicball");

        flameblast = loadSpriteSheetSet("flameblast");

        items_sheet = new SpriteSheetSingle(atlas.get("data/sprites/items.png"), 16, 16);
        items = new AssetSet[ItemType.values().length];
        for (int i = 0; i < items.length; i++)
            items[i] = new AssetSet<>();

        final ItemType grid[][] = new ItemType[16][16];
        grid[0][0] = ItemType.Copper;
        grid[1][0] = ItemType.Gold;
        grid[2][0] = grid[2][1] = grid[2][2] = ItemType.Shield0;
        grid[3][0] = grid[3][1] = grid[3][2] = ItemType.Shield1;
        grid[4][0] = grid[4][1] = grid[4][2] = ItemType.Shield2;
        grid[5][0] = grid[5][1] = grid[5][2] = ItemType.Shield3;
        grid[6][0] = grid[6][1] = grid[6][2] = ItemType.Shield4;
        grid[8][0] = grid[8][1] = grid[8][2] = grid[8][3] = grid[8][4] = ItemType.Crystal1;
        grid[9][0] = grid[9][1] = grid[9][2] = grid[9][3] = grid[9][4] = ItemType.Crystal2;
        grid[10][0] = grid[10][1] = grid[10][2] = grid[10][3] = grid[10][4] = ItemType.Crystal3;
        grid[14][0] = ItemType.Stone;

        for (int i1 = 0; i1 < grid.length; i1++) {
            for (int i2 = 0; i2 < grid[i1].length; i2++) {
                if (grid[i1][i2] == null) continue;

                items[grid[i1][i2].ordinal()].add(items_sheet.getImage(i1, i2));
            }
        }

        {
            final SpriteSheetSingle sheet = new SpriteSheetSingle(atlas.get("data/sprites/terrain/small.png"), 32, 32);
            terrain_small = new AssetSet<>();
            for (int i = 0; i < 4; i++) {
                terrain_small.add(sheet.getImage(0, i));
                terrain_small.add(sheet.getImage(1, i));
                terrain_small.add(sheet.getImage(2, i));
            }
        }
        {
            final SpriteSheetSingle sheet = new SpriteSheetSingle(atlas.get("data/sprites/terrain/glyphs.png"), 32, 32);
            terrain_glyphs = new AssetSet<>();
            for (int i1 = 0; i1 < 6; i1++) {
                for (int i2 = 0; i2 < 1; i2++) {
                    terrain_glyphs.add(sheet.getImage(i1, i2));
                }
            }
        }
        {
            final SpriteSheetSingle sheet = new SpriteSheetSingle(atlas.get("data/sprites/terrain/steps.png"), 32, 32);
            terrain_steps = new AssetSet<>();
            for (int i1 = 0; i1 < 6; i1++) {
                for (int i2 = 0; i2 < 6; i2++) {
                    terrain_steps.add(sheet.getImage(i1, i2));
                }
            }
        }

        {
            effects_sheet = new SpriteSheetSingle(atlas.get("data/sprites/effects.png"), 96, 96);
            effect_arrow = effects_sheet.getImage(0, 0);
            effect_frenzy = effects_sheet.getImage(2, 0);
        }

        // TODO move to load3 when the masks are fixed

        colors = new ArrayList<>();
        colors.add(new Color(0x715CCE));
        colors.add(new Color(0xB770FF));
        colors.add(new Color(0xFF70CF));
        colors.add(new Color(0xFF7070));
        colors.add(new Color(0x5DD37A));
        // TODO add more colors
        // TODO laod from file
        // TODO colors based on function

        axeman = loadSpriteSheetSet("axeman");
        maceman = loadSpriteSheetSet("maceman");
        swordman = loadSpriteSheetSet("swordman");

        trans = loadSoundSet("trans");
        hit = loadSoundSet("hit");
        bam = loadSoundSet("bam");
        pick = loadSoundSet("pick");
    }

    public static boolean existsRes(String path) {
        return Res.class.getResource(path) != null;
    }

    public static InputStream loadRes(String path) {
        return Res.class.getResourceAsStream(path);
    }

    public static InputStream loadResBuffered(String path) {
        return new BufferedInputStream(Res.class.getResourceAsStream(path));
    }

    public static Image loadImage(String path) throws SlickException {
        Logger.log("Assets", path + " loading");
        return new Image(loadRes(path), path, false, Image.FILTER_NEAREST);
    }

    public static Sound loadSound(String path) throws SlickException {
        Logger.log("Assets", path + " loading");
        return new Sound(loadResBuffered(path), path);
    }

    public static AssetSet<Image> getItem(ItemType type) {
        return items[type.ordinal()];
    }

    // ===

    private static AssetSet<SpriteSheet> loadSpriteSheetSet(String name) {
        final AssetSet<SpriteSheet> set = new AssetSet<>();

        for (int i = 1;; i += 1) {
            final String path = "data/sprites/entities/" + name + i + ".png";
            if (!existsRes(path)) break;

            final String pathm = "data/sprites/entities/" + name + i + "m.png";
            if (!existsRes(pathm)) {

                final Image img = atlas.get(path);

                set.add(new SpriteSheetSingle(img, img.getWidth() / 6, img.getHeight() / 6));

            } else {

                final Image img = atlas.get(path);
                final Image imgm = atlas.get(pathm);

                final SpriteSheetSingle sheet = new SpriteSheetSingle(img, img.getWidth() / 6, img.getHeight() / 6);
                final SpriteSheetSingle sheetm = new SpriteSheetSingle(imgm, imgm.getWidth() / 6, imgm.getHeight() / 6);

                for (final Color color : colors) {
                    set.add(new SpriteSheetMasked(sheet, sheetm, color));
                }
            }
        }

        return set;
    }

    private static SoundBundle loadSoundSet(String name) {
        final AssetSet<Sound> set = new AssetSet<>();

        for (int i = 1;; i += 1) {
            final String path = "data/sounds/" + name + i + ".wav";

            if (!existsRes(path)) break;

            try {
                set.add(loadSound(path));
            } catch (final SlickException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return new SoundBundle(set);
    }
}
