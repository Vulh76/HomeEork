package ru.stb.lesson12;

import ru.stb.lesson12.proxy.CacheProxy;
import ru.stb.lesson12.service.Service;
import ru.stb.lesson12.service.impl.ServiceImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
        try {
            CacheProxy cacheProxy = new CacheProxy(".");
            Service serviceProxy = cacheProxy.cache(new ServiceImpl());
            List<String> strings = null;

            try {
                strings = Files.readAllLines(Paths.get("src\\ru\\stb\\lesson12\\resources\\test.txt"));

            } catch (IOException e) {
                e.printStackTrace();
            }

            final String string = strings.toString();
            List<Future<List<String>>> futures = new ArrayList<>();
            ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            //CountDownLatch latch = new CountDownLatch(1);

            futures.add(pool.submit(() -> serviceProxy.init(string, " ", 10_000, new Date())));
            futures.add(pool.submit(() -> serviceProxy.work(string, " ", 333)));
            futures.add(pool.submit(() -> serviceProxy.work(string, " ", 10_000)));
            futures.add(pool.submit(() -> serviceProxy.work(string, " ", 12_030)));
            futures.add(pool.submit(() -> serviceProxy.work(string, " ", 110)));
            futures.add(pool.submit(() -> serviceProxy.work(string, ",", 3000)));
            futures.add(pool.submit(() -> serviceProxy.work(string, ",", 400)));
            futures.add(pool.submit(() -> serviceProxy.work(string, " ", 1500)));
            futures.add(pool.submit(() -> serviceProxy.work(string, " ", 10_195)));

            //latch.countDown();
            pool.shutdown();

            List<List<String>> results = new ArrayList<>();
            for (Future<List<String>> result : futures) {
                try {
                    results.add(result.get());
                } catch (InterruptedException ignore) {
                } catch (ExecutionException e) {
                    e.getCause().printStackTrace();
                }
            }

            for (List<String> result : results) {
                System.out.println(result.size());
            }

            serviceProxy.clear();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
