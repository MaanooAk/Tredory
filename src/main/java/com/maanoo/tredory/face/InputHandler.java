// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face;

import org.newdawn.slick.Input;

import com.maanoo.tredory.Op.Keys;
import com.maanoo.tredory.core.utils.Point;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public final class InputHandler {

    /** The mouse position with origin the top left corner of the screen. */
    public final Point mouse;

    /** The mouse position with origin the center of the screen. */
    public final Point mouseC;

    public float mouseAngle;

    public final Point direction;

    // TODO support multiple actions at the same time

    public boolean isSelectedAction;
    public int selectedActionGroup;
    public int selectedAction;

    public InputHandler() {

        mouse = new Point();
        mouseC = new Point();
        mouseAngle = 0;
        direction = new Point();

        isSelectedAction = false;

    }

    public void process(final Input in, int w, int h) {

        // mouse

        mouse.init(in.getMouseX(), in.getMouseY());
        mouseC.init(mouse.x - w / 2, mouse.y - h / 2);
        mouseAngle = mouseC.angle();

        // direction

        if (!in.isKeyDown(Keys.PickUp)) {
            // create the direction vector
            direction.init((in.isKeyDown(Keys.MoveR) ? 1 : 0) - (in.isKeyDown(Keys.MoveL) ? 1 : 0),
                    (in.isKeyDown(Keys.MoveD) ? 1 : 0) - (in.isKeyDown(Keys.MoveU) ? 1 : 0));
        } else {
            direction.init();
        }

        // select attack

        isSelectedAction = false;

        if (in.isKeyDown(Keys.Attack5)) selectAttack(0, 4);
        else if (in.isMouseButtonDown(Keys.Attack1)) selectAttack(0, 0);
        else if (in.isMouseButtonDown(Keys.Attack2)) selectAttack(0, 1);
        else if (in.isMouseButtonDown(Keys.Attack3)) selectAttack(0, 2);
        else if (in.isKeyDown(Keys.Attack4)) selectAttack(0, 3);
        else {
            for (int i = 0; i < Keys.Spell.length; i++)
                if (in.isKeyDown(Keys.Spell[i])) selectAttack(1, i);
        }

    }

    private void selectAttack(int group, int index) {
        isSelectedAction = true;
        selectedActionGroup = group;
        selectedAction = index;
    }

}
