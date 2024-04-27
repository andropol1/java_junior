package ru.gb.lesson1.hw;

import ru.gb.lesson1.StreamDemo;

import java.util.*;
import java.util.stream.Collectors;

public class Homework {

  /**
   * Используя классы Person и Department, реализовать методы ниже:
   */

  public static class Person {
    private String name;
    private int age;
    private double salary;
    private Department department;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public double getSalary() {
      return salary;
    }

    public void setSalary(double salary) {
      this.salary = salary;
    }

    public Department getDepartment() {
      return department;
    }

    public void setDepartment(Department department) {
      this.department = department;
    }

    @Override
    public String toString() {
      return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", salary=" + salary +
        ", department=" + department +
        '}';
    }
  }

  public static class Department {
    private String name;

    public Department(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "Department{" +
        "name='" + name + '\'' +
        '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Department that = (Department) o;
      return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name);
    }
  }

  /**
   * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
   */
  static int countPersons(List<Person> persons, int x, double d) {
    return (int) persons.stream()
            .filter(p -> p.getAge() > x && p.getSalary() > d)
            .count();
  }

  /**
   * Найти среднюю зарплату сотрудников, которые работают в департаменте X
   */
  static OptionalDouble averageSalary(List<Person> persons, int x) {
    return persons.stream()
            .filter(p -> p.getDepartment().getName().equals("Department #" + x))
            .mapToDouble(Person::getSalary)
            .average();
  }

  /**
   * Сгруппировать сотрудников по департаментам
   */
  static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
    return persons.stream()
            .collect(Collectors.groupingBy(Person::getDepartment));
  }

  /**
   * Найти максимальные зарплаты по отделам
   */
  static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(
                    Person::getDepartment,
                    Person::getSalary,
                    Double::max));
  }

  /**
   * ** Сгруппировать имена сотрудников по департаментам
   */
  static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
    return persons.stream()
            .collect(
                    Collectors.groupingBy(Person::getDepartment,
                            Collectors.mapping(
                                    Person::getName,
                                    Collectors.toList())));
  }

  /**
   * ** Найти сотрудников с минимальными зарплатами в своем отделе
   */
  static List<Person> minSalaryPersons(List<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(Person::getDepartment, p -> p,
                    (p1, p2) -> p1.getSalary() <= p2.getSalary() ? p1 : p2))
            .values()
            .stream()
            .toList();
  }

}
