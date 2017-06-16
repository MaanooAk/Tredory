// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.face.assets;

import com.maanoo.tredory.core.utils.Ra;

import java.util.ArrayList;

/**
 * @author MaanooAk
 */
public class AssetSet<T> {

    private final ArrayList<T> l;

    public int count;

    public AssetSet() {
        l = new ArrayList<>();
        count = 0;
    }

    public void add(T item) {
        l.add(item);
        count += 1;
    }

    public T get() {
        return l.get(Ra.range(count));
    }

    public T get(int index) {
        return l.get(index);
    }


}
