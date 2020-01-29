package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Класс для обработки событий(нажимания клавиш)
 */

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSigInButton;

    @FXML
    private Button loginSignUpButton;

    /**
     * Функция для обработки нажатия клавиш
     * Проверка на корректность данных пользователя при входе(верен ли логин\пароль)
     * Переход в окно авторизации
     */
    @FXML
    void initialize() {

        authSigInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals(""))
                loginUser(loginText, loginPassword);
            else
                System.out.println("Login and password is empty");
        });

        loginSignUpButton.setOnAction(event -> {
            openNewScene("/sample/signUp.fxml");
        });
    }

    /**
     * Функция обработки события для клавиши авторизации пользователя(вход в многопользовательский чат)
     * Извлечение информации из базы данных по установленным значениям
     * @param loginText - логин
     * @param loginPassword - пароль
     */
    private void loginUser(String loginText, String loginPassword) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;

        try {
            while(result.next()) {
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(counter >= 1) {
            new ClientWindow("localhost", 1234, user.getUserName());
            loginSignUpButton.getScene().getWindow().hide();
        } else {
            JOptionPane.showMessageDialog(null, "Неверный логин или пароль пользователя!");
        }
    }

    /**
     * Функция для открытия сцены из названия файла fxml
     * @param window - название fxml файла
     */
    public void openNewScene(String window) {
        //loginSignUpButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
