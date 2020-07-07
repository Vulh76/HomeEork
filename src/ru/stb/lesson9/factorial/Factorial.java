package ru.stb.lesson9.factorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Factorial {
    public void calc(String fileName) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
        List<Integer> values = reader.lines()
                .map(s -> s.split(","))
                .flatMap(Arrays::stream)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int value : values) {
            //Thread thread = new Thread(new Worker(value));
            Thread thread = new Thread(() -> {
                BigInteger result = factorial(value);
                System.out.printf("%d!\t= %d%n", value, result);
            });
            thread.start();
        }

    }

    class Worker implements Runnable {
        private int value;

        public Worker(int value) {
            this.value = value;
        }

        @Override
        public void run() {
            BigInteger result = factorial(value);
            System.out.printf("%d!\t= %d%n", value, result);
        }
    }

    public static BigInteger factorial(int n)
    {
        BigInteger ret = BigInteger.ONE;
        for (int i = 1; i <= n; ++i)
            ret = ret.multiply(BigInteger.valueOf(i));
        return ret;
    }
}
