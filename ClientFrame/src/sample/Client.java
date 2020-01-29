package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Точка запуску многопользовательского чата(клиентская часть)
 */
public class Client extends Application {

    /**
     * Создание окна авторизации
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Вход");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);//Вызов start()
        //new Login();
    }
}

