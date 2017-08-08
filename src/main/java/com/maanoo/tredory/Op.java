// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory;

import org.json.JSONObject;
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
        public static transient int Move[];

        public static int Attack1 = Input.MOUSE_LEFT_BUTTON;
        public static int Attack2 = Input.MOUSE_RIGHT_BUTTON;
        public static int Attack3 = Input.MOUSE_MIDDLE_BUTTON;
        public static int Attack4 = Input.KEY_Q;
        public static int Attack5 = Input.KEY_SPACE;
        public static transient int Attack[];

        public static int Spell1 = Input.KEY_Z;
        public static int Spell2 = Input.KEY_X;
        public static int Spell3 = Input.KEY_C;
        public static int Spell4 = Input.KEY_V;
        public static int Spell5 = Input.KEY_B;
        public static int Spell6 = Input.KEY_N;
        public static int Spell7 = Input.KEY_M;
        public static transient int Spell[];

        public static int PickUp = Input.KEY_E;
        public static int Activate = Input.KEY_E; // TODO use
        public static int Information = Input.KEY_TAB;

        public static int Screenshot = Input.KEY_F;

        public static void updateArrays() {
            Move = new int[] { MoveU, MoveD, MoveR, MoveL };
            Attack = new int[] { Attack1, Attack2, Attack3, Attack4, Attack5 };
            Spell = new int[] { Spell1, Spell2, Spell3, Spell4, Spell5, Spell6, Spell7 };
        }

        static {
            updateArrays();
        }
    }

    // Debug
    public static boolean debug = false;
    public static boolean debugBare = false;
    public static boolean second = false;

    // Store system

    public static JSONObject store() {
        return OpStorager.instance.store();
    }

    public static void load(JSONObject o) {
        OpStorager.instance.load(o);
    }

    private static class OpStorager implements IStore {

        public static final OpStorager instance = new OpStorager();

        private OpStorager() {}

        @Override
        public JSONObject store() {
            final JSONObject o = IStore.storeStaticObject(Op.class);
            o.put("keys", IStore.storeStaticObject(Op.Keys.class));
            return o;
        }

        @Override
        public void load(JSONObject o) {

            IStore.storeStaticObject(o, Op.class);
            if (o.has("keys")) {
                IStore.storeStaticObject(o.getJSONObject("keys"), Op.Keys.class);
            }

        }

    }

}
