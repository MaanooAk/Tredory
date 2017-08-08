// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.json.JSONObject;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public interface IStore {

    JSONObject store();

    void load(JSONObject o);

    public static <T extends IStore> T load(JSONObject o, Class<T> c) {
        try {
            final T instacne = c.newInstance();
            instacne.load(o);
            return instacne;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends IStore> T load(String json, Class<T> c) {
        return IStore.<T>load(new JSONObject(json), c);
    }

    public static JSONObject storeStaticObject(Class<?> c) {
        final JSONObject o = new JSONObject();

        for (final Field i : c.getFields()) {
            if (Modifier.isTransient(i.getModifiers())) continue;
            try {
                o.put(i.getName(), i.get(null));
            } catch (final Exception e) {}
        }

        return o;
    }

    public static void storeStaticObject(JSONObject o, Class<?> c) {

        for (final Field i : c.getFields()) {
            if (Modifier.isTransient(i.getModifiers())) continue;
            try {
                if (o.has(i.getName())) {
                    i.set(null, o.get(i.getName()));
                }
            } catch (final Exception e) {}
        }
    }
}
