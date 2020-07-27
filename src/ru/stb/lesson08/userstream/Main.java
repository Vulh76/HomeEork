package ru.stb.lesson08.userstream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Main {

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Вася", 33));
        persons.add(new Person("Петя", 34));
        persons.add(new Person("Андрей", 43));
        persons.add(new Person("Черезтризабораногузадерищенко", 23));

        IntStream stream;

        Map<String, Person> map1 = persons.stream()
                .filter(p -> p.getAge() > 20)
                .map(p -> new Person(p.getName(), p.getAge() + 30))
                .collect(toMap(p -> p.getName(), p -> p));

        Map<String, Person> map2 = Streams.of(persons)
                .filter(p -> p.getAge() > 20)
                .transform( p -> new Person(p.getName(),p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p);

    }
}
