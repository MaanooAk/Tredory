// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.utils;

import com.maanoo.tredory.core.memory.Pool;
import com.maanoo.tredory.core.memory.Poolable;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Str {

    private static final Pool<StrBuilder> pool = new Pool<>(StrBuilder.class);

    public static final class StrBuilder implements Poolable {

        private final StringBuilder sb;

        public StrBuilder() {
            sb = new StringBuilder();
        }

        public StrBuilder init() {
            sb.setLength(0);
            return this;
        }

        public StrBuilder append(String str) {
            sb.append(str);
            return this;
        }

        @Override
        public String toString() {
            return sb.toString();
        }

    }

    public static String create(String... l) {
        final StrBuilder sb = pool.obtain().init();
        for (final String i : l) {
            sb.append(i);
        }
        final String ret = sb.toString();
        pool.give(sb);
        return ret;
    }

    public static String create(Object... l) {
        final StrBuilder sb = pool.obtain().init();
        for (final Object i : l) {
            sb.append(i.toString());
        }
        final String ret = sb.toString();
        pool.give(sb);
        return ret;
    }

    public static String repeat(String text, int repeat) {
        final StrBuilder sb = pool.obtain().init();
        while (repeat-- > 0) {
            sb.append(text);
        }
        final String ret = sb.toString();
        pool.give(sb);
        return ret;
    }
}
