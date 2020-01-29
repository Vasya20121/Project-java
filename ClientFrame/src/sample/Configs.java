package sample;

/**
 * Класс-конфиг для подключения к серверу и к базе данных
 */
public class Configs {
    /**Адрес базы даных*/
    protected String dbHost = "localhost";
    /**Порт на которой запущен сервер базы данных */
    protected String dbPort = "3306";
    /**Логин для базы данных */
    protected String dbUser = "root";
    /**Пароль для базы данных*/
    protected String dbPass = "12345";
    /**Название базы данных*/
    protected String dbName = "chat";
}
