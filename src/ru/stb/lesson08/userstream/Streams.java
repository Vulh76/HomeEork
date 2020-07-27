package ru.stb.lesson08.userstream;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private Collection<T> collection;
    private Iterator<T> iterator;

    public static <T> Streams<T> of(Collection<T> collection) {
        return new Streams<T>(collection);
    }

    public Streams(Collection<T> collection) {
        this.collection = collection;
        this.iterator = collection.iterator();
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        while (iterator.hasNext()) {
            if(predicate.test(iterator.next()))
                break;
        }
        return this;
    }

    public <R> Streams<R> transform(Function<? super T, ? extends R> transformer) {

        return null;
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {

        return null;
    }
}
