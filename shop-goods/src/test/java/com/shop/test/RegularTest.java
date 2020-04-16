package com.shop.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class RegularTest {
    public static void main(String[] args) {
       /* Pattern pattern = Pattern.compile("/.*息1//.*");
        Matcher matcher = pattern.matcher("消息12313");
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            System.out.println("123133");
        }
        Pattern pattern2 = Pattern.compile("\\.*息1\\.*");
        Matcher matcher2 = pattern2.matcher("消息12313");
        while (matcher2.find()){
            int start = matcher2.start();
            int end = matcher2.end();
            System.out.println("1231");
        }
        LongStream longStream = LongStream.rangeClosed(0,10L);
        long sum = longStream.sum();
        System.out.println(sum);
        List<Person> people = Arrays.asList(new Person(10, "ads"), new Person(13, "ikads"),
                new Person(21, "512f"), new Person(8, "fu12")
        );
        Stream<Person> stream = people.stream();
        Stream<Person> sorted = stream.sorted((p1, p2) -> {
            return p2.age - p1.age;
        });
        sorted.forEach((p1)->{
            System.out.println(p1);
        });*/
        Per.sss();

    }
}
class Person {
    public int age;
    public String name;


    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }


}

class Per {
    public int age;
    public String name;
    private Per(){
        System.out.println("per实列");
    }
    public static void sss(){
        System.out.println("sss");
    }
    public static Per getP(){
        System.out.println("instant");
        return PerConstractor.ins;
    }
    static class PerConstractor{
        private final static Per ins = new Per();
        {
            System.out.println("111312");
        }
        public static void sqy(){
            System.out.println("sqy");
        }

    }
}