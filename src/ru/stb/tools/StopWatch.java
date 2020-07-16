package ru.stb.tools;

public class StopWatch {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    // Elapsed time in nanoseconds
    public long getElapsedTimeNano() {
        if (running) {
            return System.nanoTime() - startTime;
        } else {
            return stopTime - startTime;
        }
    }

    // Elapsed time in milliseconds
    public long getElapsedTimeMillis() {
        return getElapsedTimeNano() / 1000;
    }

    // Elapsed time in seconds
    public long getElapsedTimeSecs() {
        return getElapsedTimeNano() / 1_000_000;
    }
}
