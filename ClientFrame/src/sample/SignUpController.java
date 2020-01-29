package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Клас для обработки клавиш в окне регистрации
 */
public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button SignUpButton;

    @FXML
    void initialize() {

        SignUpButton.setOnAction(event -> {
            signUpNewUser();
            openNewScene("/sample/sample.fxml");

        });
    }

    /**
     * Функция для регистрации пользователя(создания новой записи в таблице базы данных)
     * Достаем значения из полей графического окна: "Логин" и "Пароль"
     * вызов функции dbHandler.signUpUser(user)
     * @see DatabaseHandler
     */
    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String userName = login_field.getText();
        String password = password_field.getText();

        User user = new User(userName, password);
        dbHandler.signUpUser(user);
    }

    /**
     * Функция открытия окна(возвращение на окно авторизации)
     * @param window - название fxml файла для авторизации
     */
    public void openNewScene(String window) {
        SignUpButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Parent root = loader.getRoot();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.showAndWait();
    }

}
