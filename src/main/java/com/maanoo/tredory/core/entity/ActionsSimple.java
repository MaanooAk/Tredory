// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author MaanooAk
 */
public class ActionsSimple extends Actions {

	private final ArrayList<Action> actions;

	public ActionsSimple(Action... actions) {

		this.actions = new ArrayList<>();
		Collections.addAll(this.actions, actions);
	}

	@Override
	public void add(Action action) {
		actions.add(action);
	}

	@Override
	public Action get() {
		return actions.get(0);
	}

	@Override
	public Action getActive() {
		for (Action i : actions) {
			if (i.isActive()) return i;
		}
		return null;
	}

	@Override
	public void update(int d) {

		for (Action i : actions) {
			i.update(d);
		}
	}

	public Action get(int index) {
		return actions.get(index);
	}

}
