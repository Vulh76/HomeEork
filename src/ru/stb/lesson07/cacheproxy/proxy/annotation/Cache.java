package ru.stb.lesson07.cacheproxy.proxy.annotation;

import ru.stb.lesson07.cacheproxy.proxy.utils.CacheType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    CacheType cacheType() default CacheType.FILE;
    String fileNamePrefix() default "data";
    int listLimit() default 1000;
    boolean zip() default false;
    Class<?>[] identityBy() default {};
}
