// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.memory;

/**
 * @author MaanooAk
 */
public class Inspector {

    public static Inspector instance;

    static {
        instance = new Inspector();
    }

    private final Runtime runtime;

    private long lastFreeMemory;

    /**
     * Number of garbage collections performed.
     */
    private long gcs;

    private Inspector() {
        runtime = Runtime.getRuntime();
        lastFreeMemory = runtime.freeMemory();
        gcs = 0;
    }

    public float getAllocatedMemory() {
        return (100L * runtime.freeMemory() / runtime.totalMemory()) / 100f;
    }

    public boolean didGarbageCollect() {
        boolean did = runtime.freeMemory() > lastFreeMemory;
        if (did) gcs += 1;
        lastFreeMemory = runtime.freeMemory();
        return did;
    }

    public long getGcs() {
        return gcs;
    }

}
