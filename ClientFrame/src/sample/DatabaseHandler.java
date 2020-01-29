package sample;

import javafx.scene.control.DialogPane;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Класс для работы с базой данных
 */
public class    DatabaseHandler extends Configs {
    Connection dbConnection;

    /**
     * Функция для установки соединения с базой даных
     * @return соединения с базой даных
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;

        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);

        return dbConnection;
    }

    /**
     * Функция для регистрации нового пользователя многопользовательского чата:
     * добавление новой записи в таблицу
     * @param user - обьект User(логин, пароль)
     */
    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" +
                "VALUES(?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            prSt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Неверный логин/Логин занят!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выбираем логин и пароль из базы данных
     * @param user
     * @return
     */
    public ResultSet getUser(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getUserName());
            prSt.setString(2, user.getPassword());

            resSet = prSt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resSet;
    }
}
