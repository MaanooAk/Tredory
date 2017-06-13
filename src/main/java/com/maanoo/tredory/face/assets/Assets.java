package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.Res;
import com.maanoo.tredory.core.entities.ItemType;
import com.maanoo.tredory.face.SoundBundle;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SpriteSheetFont;
import org.newdawn.slick.util.Log;

/**
 *
 * @author Akritas
 */
public class Assets {
    
    public static Image icon;
    public static SpriteSheet cursor;
    public static SpriteSheetFont font1;
    
    // ===
    
    public static AssetSet<SpriteSheet> chara;
    public static AssetSet<SpriteSheet> box;
    public static AssetSet<SpriteSheet> boxg;
    public static AssetSet<SpriteSheet> altar;
    public static AssetSet<SpriteSheet> portal;
    public static AssetSet<SpriteSheet> fireball;
    public static AssetSet<SpriteSheet> flameblast;
    
    public static ArrayList<Color> colors;
    
    public static AssetSet<SpriteSheet> axeman;
    public static AssetSet<SpriteSheet> maceman;
    public static AssetSet<SpriteSheet> swordman;
    
    public static AssetSet<SpriteSheet> slime;
    
    public static SpriteSheet items_sheet;
    public static AssetSet<Image> items[]; 
    
    public static AssetSet<Image> terrain_small;
    public static AssetSet<Image> terrain_glyphs;
    public static AssetSet<Image> terrain_steps;    
    
    public static SpriteSheet effects_sheet;
    public static Image effect_arrow;
    public static Image effect_frenzy;
    
    
    // ===
    
    public static SoundBundle trans;
    public static SoundBundle hit;
    public static SoundBundle bam;
    public static SoundBundle pick;
    
    
    public static void load() throws SlickException {
        
        icon = loadImage("data/icon.png");
        cursor = new SpriteSheet(loadImage("data/cursor.png"), 16, 16);
        font1 = new SpriteSheetFont(new SpriteSheet(loadImage("data/fonts/font1.png"), 8, 12), '\0');
        
        // ===
        
        chara = loadSpriteSheetSet("char", 32, 32);
        box = loadSpriteSheetSet("box", 32, 32);
        boxg = loadSpriteSheetSet("boxg", 32, 32);
        altar = loadSpriteSheetSet("altar", 32, 32);
        portal = loadSpriteSheetSet("portal", 32, 32);
        slime = loadSpriteSheetSet("slime", 32, 32);
        fireball = loadSpriteSheetSet("fireball", 32, 32);
        flameblast = loadSpriteSheetSet("flameblast", 96, 96);
        
        colors = new ArrayList<>();
        colors.add(new Color(0x715CCE));
        colors.add(new Color(0xB770FF));
        colors.add(new Color(0xFF70CF));
        colors.add(new Color(0xFF7070));
        colors.add(new Color(0x5DD37A));
        
        axeman = loadSpriteSheetSet("axeman", 32, 32);
        maceman = loadSpriteSheetSet("maceman", 32, 32);
        swordman = loadSpriteSheetSet("swordman", 32, 32);
        
        items_sheet = new SpriteSheet(loadImage("data/sprites/items.png"), 16, 16);
        items = new AssetSet[ItemType.values().length];
        for(int i=0; i<items.length; i++) items[i] = new AssetSet<>();
        
        ItemType grid[][] = new ItemType[16][16];
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
        
        for(int i1=0; i1<grid.length; i1++) {
            for(int i2=0; i2<grid[i1].length; i2++) {
                if(grid[i1][i2] == null) continue;
                
                items[grid[i1][i2].ordinal()].add(items_sheet.getSubImage(i1, i2));
            }
        }
        
        
        {
            SpriteSheet sheet = new SpriteSheet(loadImage("data/sprites/terrain/small.png"), 32, 32);
            terrain_small = new AssetSet<>();
            for(int i=0; i<4; i++) {
                terrain_small.add(sheet.getSubImage(0, i));
                terrain_small.add(sheet.getSubImage(1, i));
                terrain_small.add(sheet.getSubImage(2, i));                
            }
        }
        {
            SpriteSheet sheet = new SpriteSheet(loadImage("data/sprites/terrain/glyphs.png"), 32, 32);
            terrain_glyphs = new AssetSet<>();
            for(int i1=0; i1<6; i1++) {
                for(int i2=0; i2<1; i2++) {
                    terrain_glyphs.add(sheet.getSubImage(i1, i2));            
                }
            }
        }
        {
            SpriteSheet sheet = new SpriteSheet(loadImage("data/sprites/terrain/steps.png"), 32, 32);
            terrain_steps = new AssetSet<>();
            for(int i1=0; i1<6; i1++) {
                for(int i2=0; i2<6; i2++) {
                    terrain_steps.add(sheet.getSubImage(i1, i2));            
                }
            }
        }
        
        {
            effects_sheet = new SpriteSheet(loadImage("data/sprites/effects.png"), 96, 96);
            effect_arrow = effects_sheet.getSubImage(0, 0);
            effect_frenzy = effects_sheet.getSubImage(2, 0);            
        }
        
        
        
        // ===
                
        trans = loadSoundSet("trans");
        hit = loadSoundSet("hit");
        bam = loadSoundSet("bam");        
        pick = loadSoundSet("pick");        
    }
    
    private static boolean existsRes(String path) {
        return Res.class.getResource(path) != null;
    }
    private static InputStream loadRes(String path) {
        return Res.class.getResourceAsStream(path);
    }
    private static InputStream loadResBuffered(String path) {
        return new BufferedInputStream(Res.class.getResourceAsStream(path));
    }
    
    private static Image loadImage(String path) throws SlickException {
        Log.info("Assets: " + path + " loading");
        return new Image(loadRes(path), path, false, Image.FILTER_NEAREST);
    }
    
    private static Sound loadSound(String path) throws SlickException {
        Log.info("Assets: " + path + " loading");
        return new Sound(loadResBuffered(path), path);
    }
    
    public static AssetSet<Image> getItem(ItemType type) {
        return items[type.ordinal()];
    }
    
    // ===
    
    private static AssetSet<SpriteSheet> loadSpriteSheetSet(String name, int tw, int th) throws SlickException {        
        AssetSet<SpriteSheet> set = new AssetSet<>();
        
        for(int i=1; ; i+=1) {            
            String path = "data/sprites/entities/" + name + i + ".png";
            if(!existsRes(path)) break;
            
            String pathm = "data/sprites/entities/" + name + i + "m.png";
            if(!existsRes(pathm)) {
            
                set.add(new SpriteSheet(loadImage(path), tw, th));
            
            }else{
                
                Image img = loadImage(path);
                Image imgm = loadImage(pathm);
                
                for(Color color : colors) {
                    Image image = img.copy();
                    
                    Graphics g = image.getGraphics();
                    g.setAntiAlias(false);
                    
                    g.drawImage(imgm, 0, 0, color);
                    
                    g.flush();
                    image.setFilter(Image.FILTER_NEAREST);
                    
                    set.add(new SpriteSheet(image, tw, th));
                }
            }
        }
        
        return set;
    }
    
    private static SoundBundle loadSoundSet(String name) throws SlickException {        
        AssetSet<Sound> set = new AssetSet<>();
        
        for(int i=1; ; i+=1) {            
            String path = "data/sounds/" + name + i + ".wav";
            
            if(!existsRes(path)) break;
            
            set.add(loadSound(path));
        }
        
        return new SoundBundle(set);
    }
}
