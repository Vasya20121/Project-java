package Server;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
//import static package.clientFrame;

/**
 * Точка запуску многопользовательского чата(серверная часть)
 *
 * @author Василий
 *
 */


public class Server {
    /**Порт, по которому будет установлено соединение */
    public static final int PORT = 1234; // наш порт
    /**Связной список из установленных соединений(список клиентов) */
    public static LinkedList<ServerChat> serverList = new LinkedList<>(); //список подключений
    /**Обьект класса Story
     * @see Story */
    public static Story story;
    /**Запускаем сервер на указанном порту, с к-во подключениями
     *  */
    public static void main(String[] args) throws IOException {
        InetAddress ia = InetAddress.getByName("localhost"); // наш ip
        ServerSocket server = new ServerSocket(PORT, 10, ia); // Открываем сервер, с указанными портом, к-во подключений
        story = new Story();
        System.out.println("Server Started");
        try {
            while (true) {
                //Ждем подключение
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerChat(socket)); // Добавляем новое соединение в список
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}




