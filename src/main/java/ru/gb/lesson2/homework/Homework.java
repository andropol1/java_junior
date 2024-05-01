package ru.gb.lesson2.homework;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Homework {

  /**
   *  1. Создать аннотацию RandomDate со следующими возможностями:
   *  1.1 Если параметры не заданы, то в поле должна вставляться рандомная дата в диапазоне min, max.
   *  1.2 Аннотация должна работать с полем типа java.util.Date.
   *  1.3 Должна генерить дату в диапазоне [min, max)
   *  1.4 ** Научиться работать с полями LocalDateTime, LocalDate, Instant, ... (классы java.time.*)
   *
   *  Реализовать класс RandomDateProcessor по аналогии с RandomIntegerProcessor, который обрабатывает аннотацию.
   */
  @RandomDate
  private static Date date1;
  @RandomDate(min = 946674000000L, max = 978210000000L)
  private static Date date2;
  @RandomDate(min = 946674000000L, max = 978210000000L)
  private static LocalDate date3;
  @RandomDate(min = 946674000000L, max = 978210000000L)
  private static LocalDateTime date4;
  @RandomDate(min = 946674000000L, max = 978210000000L)
  private static Instant date5;

  public static void main(String[] args) {
    Homework homework = new Homework();
    RandomDateProcessor.processRandomDate(homework);
    System.out.println(date1);
    System.out.println(date2);
    System.out.println(date3);
    System.out.println(date4);
    System.out.println(date5);
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  private static @interface RandomDate {

    // UNIX timestamp - количество милисекунд, прошедших с 1 января 1970 года по UTC-0
    long min() default 1704056400L; // 1 января 2024 UTC-3

    long max() default 1735592400L;

  }

  public static class RandomDateProcessor{
    private static final long MILLISECONDS_IN_DAY = 86400000L;
    private static final long MILLISECONDS_IN_SEC = 1000L;
    public static void processRandomDate(Object o){
      for (Field declaredField : o.getClass().getDeclaredFields()) {
        RandomDate annotation = declaredField.getAnnotation(RandomDate.class);
        if (annotation != null) {
          long min = annotation.min();
          long max = annotation.max();
          declaredField.setAccessible(true);
          try{
            if (declaredField.getType().equals(Date.class)) {
              declaredField.set(o, new Date(ThreadLocalRandom.current().nextLong(min, max)));
            } else if (declaredField.getType().equals(LocalDate.class)) {
              declaredField.set(o,
                      LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(min, max) / MILLISECONDS_IN_DAY));
            } else if (declaredField.getType().equals(LocalDateTime.class)) {
              declaredField.set(o, LocalDateTime.ofEpochSecond(
                      ThreadLocalRandom.current().nextLong(min, max) / MILLISECONDS_IN_SEC, 
                      0, ZoneOffset.ofHours(-3)));
            } else if (declaredField.getType().equals(Instant.class)) {
              declaredField.set(o, Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(min, max)));
            }
          } catch (IllegalAccessException e) {
            System.err.println("Не получилось установить рандомную дату: " + e);
          }
          declaredField.setAccessible(false);
        }
      }
    }
  }

}
