// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.memory;

import java.util.HashMap;

import com.maanoo.tredory.core.entity.entities.Projectile;


/**
 * TODO improve
 *
 * @author MaanooAk
 */
public class Pools {

    // TODO change hashmap with something faster

    private static HashMap<Class<?>, Pool> map = new HashMap<>();

    public static void load() {
        for (int i = 0; i < 40; i++) {
            give(new Projectile());
        }
    }

    public static <T extends Poolable> T obtain(Class<T> type) {
        map.putIfAbsent(type, new Pool(type));
        return (T) map.get(type).obtain();
    }

    public static <T extends Poolable> void give(T object) {
        map.putIfAbsent(object.getClass(), new Pool(object.getClass()));
        map.get(object.getClass()).give(object);
    }

    public static void clear() {
        map.clear();
    }

}
