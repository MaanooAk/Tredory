// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine.profile;

import java.util.HashMap;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Profile {

    public static final Profile global = new Profile();

    private static long now() {
        return System.nanoTime();
    }

    private static final class Duration {

        private long start = 0;
        private long duration = 1;

        public void start() {
            start = now();
        }

        public void end() {
            duration = now() - start;
        }

        public long get() {
            return duration;
        }

    }

    private final HashMap<String, Duration> durations;

    public Profile() {
        durations = new HashMap<>();
    }

    public void add(String name) {
        durations.put(name, new Duration());
    }

    public void start(String name) {
        durations.get(name).start();
    }

    public void end(String name) {
        durations.get(name).end();
    }

    public long get(String name) {
        return durations.get(name).get();
    }

    public long getInt(String name) {
        return (int) durations.get(name).get();
    }

}
