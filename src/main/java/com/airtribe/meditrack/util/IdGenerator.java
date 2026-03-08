package com.airtribe.meditrack.util;

public final class IdGenerator {
    private static volatile IdGenerator INSTANCE;
    private long seq = 0;

    private IdGenerator() {}
    public static IdGenerator getInstance() {
        if (INSTANCE == null) {
            synchronized (IdGenerator.class) {
                if (INSTANCE == null) INSTANCE = new IdGenerator();
            }
        }
        return INSTANCE;
    }

    public synchronized String next(String prefix) { return prefix + "-" + (++seq); }

    // Optional: adjust floor to avoid collisions after CSV load
    public synchronized void setFloor(long floor) {
        if (floor > seq) seq = floor;
    }
}