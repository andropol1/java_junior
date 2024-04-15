package ru.gb.lesson1;

import java.util.Random;
import java.util.function.*;

public class Lambdas {

  public static void main(String[] args) {
    // () -> ()
    Runnable object = () -> System.out.println("Hello, world!");
    object.run();

    // arg -> ()
//    Consumer<String> stringPrinter = arg -> System.out.println(arg);
    Consumer<String> stringPrinter = System.out::println;
    stringPrinter.accept("Hello from consumer!");

    // () -> result
    Supplier<Integer> randomIntegerGenerator = () -> new Random().nextInt(0, 1000);
    System.out.println(randomIntegerGenerator.get());
    System.out.println(randomIntegerGenerator.get());
    System.out.println(randomIntegerGenerator.get());
    System.out.println(randomIntegerGenerator.get());
    System.out.println(randomIntegerGenerator.get());

    // arg -> result
//    Function<String, Integer> stringLengthExtractor = x -> x.length();
    Function<String, Integer> stringLengthExtractor = String::length;
    System.out.println(stringLengthExtractor.apply("Python"));
    System.out.println(stringLengthExtractor.apply("Java"));
    System.out.println(stringLengthExtractor.apply("Kotlin"));

    // (arg1, arg2) -> result
    BiFunction<Integer, Integer, Integer> adder = Integer::sum;
    System.out.println(adder.apply(5, 7));

    Predicate<Integer> isEvenTester = x -> x % 2 == 0;
    System.out.println(isEvenTester.test(2));
    System.out.println(isEvenTester.test(7));
    System.out.println(isEvenTester.test(0));

    Predicate<String> isJava = "java"::equals;
//    Predicate<String> isJava = arg -> "java".equals(arg);
    System.out.println(isJava.test("java"));
    System.out.println(isJava.test("c++"));

    Function<String, User> userCreator = User::new;
    User name = userCreator.apply("name");

    Supplier<User> unnamedUserGenerator = User::new; // () -> User
    BiFunction<String, Integer, User> a = User::new;

  }

  private static class User {
    private String name;

    public User() {
      this("unnamed");
    }

    public User(String name) {
      this.name = name;
    }

    public User(String name, int age) {

    }
  }

}
