package ru.stb.tools;

public class StopWatch {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    // Elapsed time in milliseconds
    public long getElapsedTime() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return stopTime - startTime;
        }
    }

    // Elapsed time in seconds
    public long getElapsedTimeSecs() {
        if (running) {
            return (System.currentTimeMillis() - startTime) / 1000;
        } else {
            return (stopTime - startTime) / 1000;
        }
    }
}
