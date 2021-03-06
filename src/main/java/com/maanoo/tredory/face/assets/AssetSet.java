// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import java.util.ArrayList;

import com.maanoo.tredory.core.utils.Ra;


/**
 * @author MaanooAk
 */
public class AssetSet<T> {

    private final ArrayList<T> l;

    public AssetSet() {
        this.l = new ArrayList<>();
    }

    public void add(T item) {
        l.add(item);
    }

    public T get() {
        return l.get(Ra.global.range(l.size()));
    }

    public T get(int index) {
        return l.get(index);
    }

    public int getCount() {
        return l.size();
    }
}
