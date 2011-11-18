package com.bloodredsun.util;

public class MemoryStats {

    public static final int mb = 1024 * 1024;

    long usedMemory;
    long freeMemory;
    long totalMemory;
    long maxMemory;


    public MemoryStats(Runtime runtime) {
        this.usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / mb;
        this.freeMemory = runtime.freeMemory() / mb;
        this.totalMemory = runtime.totalMemory() / mb;
        this.maxMemory = runtime.maxMemory() / mb;
    }

    public String toString() {
        return "Used Memory:" + usedMemory + "\t" +
                "Free Memory:" + freeMemory + "\t" +
                "Total Memory:" + totalMemory + "\t" +
                "Max Memory:" + maxMemory;
    }

}