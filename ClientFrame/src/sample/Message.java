package sample;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Сереализированый обьект для формирования сообщений
 */

public class Message implements Serializable {
    private static final long serialVersionUID = 32712036601950306L;
    private String message;
    private String userName;
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;

    /**
     * Конструктор - получение текущего времени, передача логина и сообщения
     * @param userName - логин
     * @param message - сообщение
     */
    Message(String userName, String message){
        this.message = message;
        this.userName = userName;
        time = new Date();//время отправки
        dt1 = new SimpleDateFormat("HH:mm:ss"); // шаблон
        dtime = dt1.format(time);// форматируем дату по шаблону
    }
    @Override
    public String toString() {
        return "(" + dtime + ") " + userName + ": " + message;
    }
}
