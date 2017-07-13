// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory;

import org.newdawn.slick.Input;


/**
 * Options
 *
 * @author MaanooAk
 */
public class Op {

    // Window
    public static boolean fullscreen = false;
    public static int w = 800;
    public static int h = 600;

    // Video
    public static int fps = 60;
    public static boolean vsync = true;
    public static boolean alwaysRender = true;
    public static int scale = 2; // TODO use

    // Audio
    public static float music = 0.05f;
    public static float sound = 0.2f;
    public static float dialog = 0.5f;

    // Keys
    public static final class Keys {

        public static int Select = Input.KEY_SPACE;
        public static int Back = Input.KEY_ESCAPE;

        public static int MoveU = Input.KEY_W;
        public static int MoveD = Input.KEY_S;
        public static int MoveR = Input.KEY_D;
        public static int MoveL = Input.KEY_A;

        public static int Attack1 = Input.MOUSE_LEFT_BUTTON;
        public static int Attack2 = Input.MOUSE_RIGHT_BUTTON;
        public static int Attack3 = Input.MOUSE_MIDDLE_BUTTON;
        public static int Attack4 = Input.KEY_Q;
        public static int Attack[] = new int[] { Attack1, Attack2, Attack3, Attack4 };

        public static int Spell1 = Input.KEY_Z;
        public static int Spell2 = Input.KEY_X;
        public static int Spell3 = Input.KEY_C;
        public static int Spell4 = Input.KEY_V;
        public static int Spell5 = Input.KEY_B;
        public static int Spell6 = Input.KEY_N;
        public static int Spell7 = Input.KEY_M;
        public static int Spell[] = new int[] { Spell1, Spell2, Spell3, Spell4, Spell5, Spell6, Spell7 };

        public static int PickUp = Input.KEY_E;
        public static int Activate = Input.KEY_E; // TODO use
        public static int Information = Input.KEY_TAB;
    }

    // Debug
    public static boolean debug = false;
    public static boolean debugBare = false;

    public static void load(String[] args) {

        // TODO load options from a file

        for (int i = 0; i < args.length; i++) {
            final String arg = args[i];

            if (arg.equals("-d") || arg.equals("--debug")) {
                Op.debug = true;
            }
        }

    }

}
