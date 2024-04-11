package com.example.main_db;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class Database {
    private static final String host = "jdbc:mysql://localhost:3306/practic_apr";
    private static final String user = "root";
    private static final String password = "1703Chizy+";

    private static Connection connect;
    public static Connection getConnect() {
        return connect;
    }

    static {
        try {
            connect = DriverManager.getConnection(host, user, password);
            // TODO log and indicate
            System.out.println(connect.isValid(1));
            System.out.println("Подключено к базе данных!");

        } catch (SQLException e) {
            System.out.println("Невозможно подключиться к базе данных!");

            throw new RuntimeException(e);
        }
    }
    public static void connected() {
        try {
            connect = DriverManager.getConnection(host, user, password);
            // TODO log and indicate
            System.out.println(connect.isValid(1));
            System.out.println("Подключено к базе данных!");

        } catch (SQLException e) {
            System.out.println("Невозможно подключиться к базе данных!");

            throw new RuntimeException(e);
        }
    }
    //    private static Connection connect;
//    private static Statement stat;
    private static final ObservableList<User> listUser = FXCollections.observableArrayList();
    private static final ObservableList<Integer> ids = FXCollections.observableArrayList();

    public static class User {
        private final int id;
        private String login;
        private String pass;
        private String fio;

        User(int id, String login, String pass, String fio) {
            this.id = id;
            this.login = login;
            this.pass = pass;
            this.fio = fio;
        }

        public int getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getPass() {
            return pass;
        }

        public String getFio() {
            return fio;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setFio(String fio) {
            this.fio = fio;
        }

        @Override
        public String toString() {
            return "{ " +
                    "Login: " + login + "," + System.lineSeparator() +
                    "  Pass: " + pass + "," + System.lineSeparator() +
                    "  FIO: " + fio +
                    " }" + System.lineSeparator();
        }
    }
    public static ObservableList<User> getAllUsers() {
        try {
            PreparedStatement stat = connect.prepareStatement("SELECT * FROM user_info");
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                User user = new User(Integer.parseInt(result.getString("id")), result.getString("login"), result.getString("password"), result.getString("fio"));
                listUser.add(user);
            }
            return listUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<User> getFemaleUsers() {
        try {
            PreparedStatement stat = connect.prepareStatement("SELECT * FROM user_info WHERE sex = 'Женский' ");
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                User user = new User(result.getInt("id"), result.getString("login"), result.getString("password"), result.getString("fio"));
                listUser.add(user);
            }
            return listUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<User> getMaleUsers() {
        try {
            PreparedStatement stat = connect.prepareStatement("SELECT * FROM user_info WHERE sex = 'Мужской' ");
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                User user = new User(Integer.parseInt(result.getString("id")), result.getString("login"), result.getString("password"), result.getString("fio"));
                listUser.add(user);
            }
            return listUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Integer getLogin(String login) {
        try {
            PreparedStatement stat = connect.prepareStatement("SELECT id FROM user_info WHERE login = '" + login + "'");
            ResultSet result = stat.executeQuery();
            int id = 0;
            while (result.next()) {
                id = result.getInt("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void insertUser(String login, String pass, String fio, String sex) {
        try {
            PreparedStatement stat = connect.prepareStatement("INSERT INTO user_info(login, password, fio, sex) VALUES ('"
                    + login + "','" + pass + "','" + fio + "','" + sex + "')");
            int result = stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<Integer> getIdUser() {
        try {
            ids.clear();
            PreparedStatement stat = connect.prepareStatement("SELECT id FROM user_info");
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                ids.add(id);
            }
            return ids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteUser(int id) {
        try {
            PreparedStatement stat = connect.prepareStatement("DELETE FROM user_info WHERE id = '" + id + "' ");
            int result = stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateFioUser(int id, String fio) {
        try {
            PreparedStatement stat = connect.prepareStatement("UPDATE user_info SET fio = '" + fio + "' WHERE id = '" + id + "' ");
            int result = stat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getFio(Integer id) {
        try {
            PreparedStatement stat = connect.prepareStatement("SELECT fio FROM user_info WHERE id = '" + id + "'");
            ResultSet result = stat.executeQuery();
            String fio = null;
            while (result.next()) {
                fio = result.getString("fio");
            }
            return fio;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
