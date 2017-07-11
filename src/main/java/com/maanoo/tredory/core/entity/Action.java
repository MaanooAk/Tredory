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
public abstract class Action implements IUpdate{

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

	protected State state;
	private float state_time;
	private float time_left;
	private boolean stop_recharge;
	protected int perform_count;
	
	public Action(Entity user, float charge_time, float recharge_time, float end_time, float cooldown_time) {
		this.user = user;
		
		recharge = recharge_time > 0;
		cooldown = cooldown_time > 0;
		
		this.charge_time = charge_time;
		this.recharge_time = recharge_time;
		this.end_time = end_time;
		this.cooldown_time = cooldown_time;
		
		state = State.Idle;
		state_time = 1;
		time_left = 0;
		
		stop_recharge = false;
		perform_count = 0;
	}

	public boolean canStart() {
		return state == State.Idle;
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
	
	public abstract void end();
	
	public abstract void cooldown();
	
	
	private final void changeState(State new_state) {
		
		state = new_state;
		time_left = getStateTime(new_state);
		state_time = time_left;
	}
	
	public float getStateTime(State state) {
		switch(state) {
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
			case Charging:
			case Recharging:
				
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
	 * the ending frames.
	 * TODO remove the limits 
	 */
	public int getAnimationFrame() {
		switch(state) {
		case Charging:
			return Ma.limit((int) (4 * (1f - time_left / state_time)), 0, 3);
		case Ending:
			return Ma.limit((int) (4 + 2 * (1f - time_left / state_time)), 4, 5);
		case Recharging:
			return 3; //Ma.limit((int) (2 + 2 * (1f - time_left / state_time)), 2, 3);
		default:
			return 0;
		}
	}

	public float getCooldownProgress() {
		return 1f - time_left / cooldown_time;
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
	
	public boolean isRecharge() {
		return recharge && !stop_recharge;
	}
	
	public State getState() {
		return state;
	}
	
}
