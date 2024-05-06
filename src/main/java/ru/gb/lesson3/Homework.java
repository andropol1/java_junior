package ru.gb.lesson3;

import java.sql.*;

public class Homework {
    private static final String URL = "jdbc:h2:mem:myDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

  /**
   * Повторить все, что было на семниаре на таблице Student с полями
   * 1. id - bigint
   * 2. first_name - varchar(256)
   * 3. second_name - varchar(256)
   * 4. group - varchar(128)
   *
   * Написать запросы:
   * 1. Создать таблицу
   * 2. Наполнить таблицу данными (insert)
   * 3. Поиск всех студентов
   * 4. Поиск всех студентов по имени группы
   *
   * Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...
   */
  public static void main(String[] args) {
      try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
          acceptConnection(con);
      } catch (SQLException e){
          System.err.println("Не удалось подключиться к базе данных: " + e.getMessage());
      }
  }
  private static void acceptConnection(Connection con){
      try (Statement stat = con.createStatement()) {
          stat.execute("""
                  create table students
                  (
                    id bigint primary key auto_increment,
                    first_name varchar(256),
                    second_name varchar(256),
                    `group` varchar(128)
                  )
                  """);
          int count = stat.executeUpdate("""
                  insert into students(first_name, second_name, `group`) values
                  ('Andy', 'Stone', 'A'),
                  ('Jack', 'Farell', 'B'),
                  ('Tom', 'Cruz', 'C')
                  """);
          System.out.println("Количество вставленных строк: " + count);
          ResultSet resultSet = stat.executeQuery("""
                  select id, first_name, second_name, `group` from students
                  """);
          while(resultSet.next()){
              int id = resultSet.getInt("id");
              String fn = resultSet.getString("first_name");
              String sn = resultSet.getString("second_name");
              String gr = resultSet.getString("group");
              System.out.println("Получена строка: " + String.format("%s,%s,%s,%s",id, fn, sn, gr));
          }
          System.out.println("---".repeat(5));
          ResultSet set = stat.executeQuery("""
                  select id, first_name, second_name, `group` from students where `group` = 'A'
                  """);
          while (set.next()){
              int id = set.getInt("id");
              String fn = set.getString("first_name");
              String sn = set.getString("second_name");
              String gr = set.getString("group");
              System.out.println("Получена строка: " + String.format("%s,%s,%s,%s",id, fn, sn, gr));
          }
      }catch (SQLException e){
          System.err.println("Не удалось отправить запрос в базу данных: " + e.getMessage());
      }
  }
}
