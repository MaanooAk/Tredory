package com.maanoo.tredory;

import org.newdawn.slick.Input;

/**
 * Options
 * 
 * @author Akritas
 */
public class Op {

    public static boolean fullscreen = false;
    public static int w = 800;
    public static int h = 600;
    
    public static int fps = 60;    
    public static boolean vsync = true;
    public static boolean alwaysren = true;
    
    public static float music = 0.05f;
    public static float sound = 0.05f;
    public static float dialog = 0.05f;
    
    
    public static final class Keys {
        public static final int Select = Input.KEY_SPACE;
        public static final int Back = Input.KEY_ESCAPE;
        
        public static int Attack1 = Input.MOUSE_LEFT_BUTTON;
        public static int Attack2 = Input.MOUSE_RIGHT_BUTTON;
        public static int Attack3 = Input.MOUSE_MIDDLE_BUTTON;
        public static int Attack4 = Input.KEY_Q;
        public static int Attack[] = new int[] {Attack1, Attack2, Attack3, Attack4};
        
        public static int Spell1 = Input.KEY_Z;
        public static int Spell2 = Input.KEY_X;
        public static int Spell3 = Input.KEY_C;
        public static int Spell4 = Input.KEY_V;
        public static int Spell5 = Input.KEY_B;
        public static int Spell6 = Input.KEY_N;
        public static int Spell7 = Input.KEY_M;
        public static int Spell[] = new int[] {Spell1, Spell2, Spell3, Spell4, Spell5, Spell6, Spell7};
        
        public static int PickUp = Input.KEY_E;
    }
    
    
    public static int scale = 2;
    
    public static boolean debug = false;
    
}
