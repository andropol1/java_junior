package ru.gb.lesson1.hw;

import org.w3c.dom.ls.LSOutput;
import ru.gb.lesson1.StreamDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static ru.gb.lesson1.hw.Homework.*;

public class Main {
    public static void main(String[] args) {
        List<Homework.Department> departments = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            departments.add(new Homework.Department("Department #" + i));
        }
        List<Homework.Person> persons = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Homework.Department department = departments.get(randomDepartmentIndex);

            Homework.Person person = new Homework.Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
            person.setSalary(ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0);
            person.setDepartment(department);

            persons.add(person);
        }
        System.out.println(persons.toString());
        System.out.println(countPersons(persons, 30, 50000));
        if (averageSalary(persons, 1).isPresent()){
            System.out.println(averageSalary(persons, 1).getAsDouble());
        }
        Map<Homework.Department, List<Homework.Person>> personsByDepartment = groupByDepartment(persons);
        for (Map.Entry<Department, List<Person>> departmentListEntry : personsByDepartment.entrySet()) {
            System.out.println(departmentListEntry.getKey());
            for (Person person : departmentListEntry.getValue()) {
                System.out.println(person.getName());
            }
            System.out.println();
        }
        for (var entry : maxSalaryByDepartment(persons).entrySet()) {
            System.out.print(entry.getKey().getName() + ": ");
            System.out.println(entry.getValue());
        }
        System.out.println();
        for (var entry : groupPersonNamesByDepartment(persons).entrySet()) {
            System.out.print(entry.getKey().getName() + ": ");
            System.out.println(entry.getValue());
        }
        System.out.println();
        minSalaryPersons(persons)
                .forEach(p -> System.out.println(p.getDepartment().getName() + ": " + p));
    }
}
