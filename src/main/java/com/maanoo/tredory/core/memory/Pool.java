// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.memory;

import java.util.Stack;


/**
 * @author MaanooAk
 */
public class Pool<T extends Poolable> {

    private final Class<T> type;
    private final Stack<T> pool;

    public Pool(Class<T> type) {
        this.type = type;

        pool = new Stack<T>();
    }

    public T create() {
        try {
//            Logger.log("Pool", "Pool of " + type.getName() + ": create: " + pool.size());
            return type.newInstance();
        } catch (final Exception e) {
            return null;
        }
    }

    public T obtain() {
//        Logger.log("Pool", "Pool of " + type.getName() + ": obtain: " + pool.size());
        return pool.empty() ? create() : pool.pop();
    }

    public void give(T object) {
        pool.push(object);
//        Logger.log("Pool", "Pool of " + type.getName() + ": give: " + pool.size());
    }

}
