package ru.gb.lesson1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class StreamDemo {

  public static void main(String[] args) throws IOException {
    int[] array = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(1000))
      .map(it -> it + 1)
      .map(it -> it * 5)
      .filter(it -> it > 10)
      .limit(100)
      .toArray();


    List<Department> departments = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      departments.add(new Department("Department #" + i));
    }

    List<Person> persons = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
      Department department = departments.get(randomDepartmentIndex);

      Person person = new Person();
      person.setName("Person #" + i);
      person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
      person.setSalary(ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0);
      person.setDepartment(department);

      persons.add(person);
    }

    printPersons(persons);
    Optional<Person> bestPerson = findBestPerson(persons);
  }

  // Вывести имена сотрудников, старше 40 лет
  static void printPersons(List<Person> persons) {
    persons.stream()
      .filter(it1 -> it1.getAge() > 40)
      .map(it1 -> it1.getName()) // Person -> String
      .forEach(it -> System.out.println(it));
  }

  // Найти сотрудника с самой высокой зарплатой в 5 департаменте
  static Optional<Person> findBestPerson(List<Person> persons) {
    return persons.stream()
      .filter(it -> it.getDepartment().getName().contains("#5"))
      .max((o1, o2) -> Double.compare(o1.getSalary(), o2.getSalary()));
  }

  private static class Person {
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

  private static class Department {
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
  }

}
