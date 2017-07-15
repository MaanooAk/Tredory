// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core.memory;

import com.maanoo.tredory.engine.Logger;


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
        Logger.log("Inspector", "runtime.totalMemory: " + runtime.totalMemory());
    }

    public float getAllocatedMemory() {
        return (1000L * runtime.freeMemory() / runtime.totalMemory()) / 1000f;
    }

    public boolean didGarbageCollect() {
        final boolean did = runtime.freeMemory() > lastFreeMemory;
        if (did) {
            gcs += 1;
            Logger.log("Inspector", "gcs: " + gcs);
        }
        lastFreeMemory = runtime.freeMemory();
        return did;
    }

    public long getGcs() {
        return gcs;
    }

}
