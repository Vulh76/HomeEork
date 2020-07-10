package ru.stb.tools;

import java.util.Random;

public class HardTask {
    static public double randomTask(int maxIterations) {
        double result = 0;
        int random = new Random().nextInt(maxIterations) + 1;
        for (int i = 1; i < random; i++) {
            for (int j = random; j > 0; j--) {
                result += Math.sin((double) i / j) * Math.cos((double) j / i) * Math.tan((double) i / random);
            }
        }
        return result;
    }

    static public double burnTask(int iterations) {
        double result = 0;
        for (int i = 1; i < iterations; i++) {
            for (int j = iterations; j > 0; j--) {
                result += Math.sin((double) i / j) * Math.cos((double) j / i) * Math.tan((double) i / iterations);
            }
        }
        return result;
    }
}
