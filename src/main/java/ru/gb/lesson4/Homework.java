package ru.gb.lesson4;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Homework {

  /**
   * Перенести структуру дз третьего урока на JPA:
   * 1. Описать сущности Student и Group
   * 2. Написать запросы: Find, Persist, Remove
   * 3. ... поупражняться с разными запросами ...
   */
  public static void main(String[] args) {
    Configuration configuration = new Configuration().configure();
    try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
      Student student = new Student();
      try (Session session = sessionFactory.openSession()){
        student.setId(1L);
        student.setFirstName("Andy");
        student.setSecondName("Cat");
        student.setGroup("A");
        Transaction transaction = session.beginTransaction();
        session.persist(student);
        transaction.commit();
      }
      try (Session session = sessionFactory.openSession()) {
        student = session.find(Student.class, 1);
        System.out.println(student);
        student.setGroup("B");
        Transaction transaction = session.beginTransaction();
        session.merge(student);
        transaction.commit();
      }
      try (Session session = sessionFactory.openSession()) {
        System.out.println(session.find(Student.class, 1));
      }
      try (Session session = sessionFactory.openSession()){
        Transaction transaction = session.beginTransaction();
        session.remove(student);
        transaction.commit();
        System.out.println(session.find(Student.class, 1));
      }

    }

  }

}
