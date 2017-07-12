// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.entity;

import com.maanoo.tredory.core.IUpdate;

/**
 * @author MaanooAk
 */
public abstract class Actions implements IUpdate {

	public abstract void add(Action action);

	public abstract Action get();

	public abstract Action getReady();

	public abstract Action getActive();

}
