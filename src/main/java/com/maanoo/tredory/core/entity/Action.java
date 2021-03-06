// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.IUpdate;
import com.maanoo.tredory.core.utils.Ma;


/**
 * The base class for actions
 *
 * Action life cycle: start, perform, cool down (optional), end
 *
 * @author MaanooAk
 */
public abstract class Action implements IUpdate {

    public static enum State {
        Idle, Charging, Recharging, Ending, Cooling;
    }

    protected final Entity user;

    private final boolean recharge;
    private final boolean cooldown;

    protected final float charge_time;
    protected final float recharge_time;
    protected final float end_time;
    protected final float cooldown_time;

    // TODO change, to protected final
    public int max_perform_count;

    protected final boolean backgroud;

    protected State state;
    protected float state_time;
    protected float time_left;
    protected boolean stop_recharge;
    protected int perform_count;

    public Action(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time) {
        this(user, charge_time, recharge_time, end_time, cooldown_time, Integer.MAX_VALUE, false);
    }

    public Action(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time,
            boolean backgroud) {
        this(user, charge_time, recharge_time, end_time, cooldown_time, Integer.MAX_VALUE, backgroud);
    }

    public Action(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time,
            int max_perform_count) {
        this(user, charge_time, recharge_time, end_time, cooldown_time, max_perform_count, false);
    }

    public Action(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time,
            int max_perform_count, boolean backgroud) {
        this.user = user;

        recharge = recharge_time > 0;
        cooldown = cooldown_time > 0;

        this.charge_time = charge_time;
        this.recharge_time = recharge_time;
        this.end_time = end_time;
        this.cooldown_time = cooldown_time;

        this.max_perform_count = max_perform_count;

        this.backgroud = backgroud;

        state = State.Idle;
        state_time = 1;
        time_left = 0;

        stop_recharge = false;
        perform_count = 0;
    }

    public boolean canStart() {
        return state == State.Idle;
    }

    public boolean canStartBackground() {
        return canStart() && backgroud;
    }

    public void start() {
        changeState(State.Charging);

        stop_recharge = false;
        perform_count = 0;
    }

    public void stop() {
        changeState(cooldown ? State.Cooling : State.Idle);
    }

    public void stopRecharge() {
        stop_recharge = true;
    }

    public abstract void perform();

    public abstract void recharge();

    public abstract void end();

    public abstract void cooldown();

    private final void changeState(State new_state) {

        state = new_state;
        time_left = getStateTime(new_state);
        state_time = time_left;
    }

    public float getStateTime(State state) {
        switch (state) {
        case Charging:
            return charge_time;
        case Recharging:
            return recharge_time;
        case Ending:
            return end_time;
        case Cooling:
            return cooldown_time;
        default:
            return 0;
        }
    }

    @Override
    public void update(int d) {

        if (state == State.Idle) {
            return;
        }

        time_left -= d;

        if (time_left <= 0) {
            // the state has ended, find the next state

            switch (state) {
            case Recharging:

                recharge();
                if (perform_count > max_perform_count) {
                    stop_recharge = true;
                }

            case Charging:

                perform();
                perform_count += 1;

                changeState(recharge && !stop_recharge ? State.Recharging : State.Ending);
                stop_recharge = false;
                break;
            case Ending:

                end();
                changeState(cooldown ? State.Cooling : State.Idle);
                break;
            case Cooling:

                cooldown();
                changeState(State.Idle);
                break;
            default:

                // not reached
                changeState(State.Idle);
                break;
            }

        }

    }

    /**
     * Returns the animation frames where 0,1,2,3 are the charge frames and 4,5 are
     * the ending frames. TODO remove the limits
     */
    public int getAnimationFrame() {
        switch (state) {
        case Charging:
            return Ma.limit((int) (4 * (1f - time_left / state_time)), 0, 3);
        case Ending:
            return Ma.limit((int) (4 + 2 * (1f - time_left / state_time)), 4, 5);
        case Recharging:
            return (time_left / state_time) < 0.25 ? 2 : 3;
        default:
            return 0;
        }
    }

    public float getStateProgress() {
        if (state_time != 0) return 1f - time_left / state_time;
        return 1f;
    }

    public boolean isActive() {
        switch (state) {
        case Idle:
        case Cooling:
            return false;
        default:
            return true;
        }
    }

    public boolean isCooling() {
        return state == State.Cooling;
    }

    public boolean isRecharge() {
        return recharge && !stop_recharge;
    }

    public State getState() {
        return state;
    }

    public float getStateTime() {
        return state_time;
    }

    public float getStateTimeLeft() {
        return time_left;
    }

}
