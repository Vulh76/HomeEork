package ru.stb.lesson07.cacheproxy;

import ru.stb.lesson07.cacheproxy.proxy.CacheProxy;
import ru.stb.lesson07.cacheproxy.service.Service;
import ru.stb.lesson07.cacheproxy.service.impl.ServiceImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            CacheProxy cacheProxy = new CacheProxy(".");
            Service serviceProxy = cacheProxy.cache(new ServiceImpl());
            List<String> strings = null;

            try {
                strings = Files.readAllLines(Paths.get("src\\ru\\stb\\resources\\test.txt"));

            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> result;

            result = serviceProxy.init(strings.toString(), " ", 10_000, new Date());
            result = serviceProxy.work(strings.toString(), " ", 1000);
            result = serviceProxy.work(strings.toString(), " ", 100);
            result = serviceProxy.work(strings.toString(), " ", 1000);
            result = serviceProxy.work(strings.toString(), ",", 1000);
            serviceProxy.clear();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
