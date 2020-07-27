package ru.stb.lesson12.proxy;

import ru.stb.lesson12.proxy.annotation.Cache;
import ru.stb.lesson12.proxy.utils.CacheKey;
import ru.stb.lesson12.proxy.utils.CacheMap;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class CacheHandler implements InvocationHandler {
    private final Object original;
    private final String cacheDir;
    private ConcurrentHashMap<CacheKey, Object> cacheData;

    /**
     * Конструктор принимает общие параметры кэширования
     *
     * @param original обект, для которого создается прокси
     * @param cacheDir каталог, в тором будут создаваться файлы кэша, если выбрано сохраниений кэша в файл
     * @param cacheSize максимальный размер кэша
     */
    public CacheHandler(Object original, String cacheDir, int cacheSize) {
        this.original = original;
        this.cacheDir = cacheDir;
        this.cacheData = new ConcurrentHashMap<>(cacheSize);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(!method.isAnnotationPresent(Cache.class))
            return method.invoke(original, args);

        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        CacheKey cacheKey = new CacheKey(method.getName(), args);
        Object cacheValue = cacheData.get(cacheKey);
        if(cacheValue != null) return cacheValue;
        Object result = method.invoke(original, args);

        if(result instanceof List<?> && cacheAnnotation.listLimit() < Integer.MAX_VALUE)
            cacheData.put(cacheKey, ((List<?>) result).subList(0, cacheAnnotation.listLimit()));
        else
            cacheData.put(cacheKey, result);

        //saveCache(method.getName() + ".cache");
        //loadCache(method.getName() + ".cache");

        return result;
    }

    public void saveCache(String fileName) throws IOException {
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            out.writeObject(cacheData);
        }
    }

    public void loadCache(String fileName) throws IOException, ClassNotFoundException {
        try(
                FileInputStream fileInputStream = new  FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileInputStream)
        ) {
            cacheData = (ConcurrentHashMap<CacheKey, Object>) in.readObject();
        }
    }
}
