package ru.stb.lesson07.cacheproxy.service;

import ru.stb.lesson07.cacheproxy.proxy.annotation.Cache;
import ru.stb.lesson07.cacheproxy.proxy.utils.CacheType;

import java.util.Date;
import java.util.List;

public interface Service {
    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "cache", listLimit = 10_000, zip = true,
            identityBy = {String.class, int.class})
    List<String> init(String text, String div, int limit, Date date);
    @Cache (cacheType = CacheType.MEMORY)
    List<String> work(String text, String div, int limit);
    void clear ();
}
