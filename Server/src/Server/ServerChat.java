package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Класс устанавливающий соединение с клиентом в отдельном потоке
 */
public class ServerChat extends Thread{
    /**Сокет, через который сервер и клиент связаны и будут общаться */
    private Socket socket; // Сокет, через который сервер и клиент связаны и могут общаться
    /**Поток чтения из сокета */
    private ObjectInputStream in;// Поток чтения из сокета
    /**Поток записи в сокет */
    private ObjectOutputStream out;// Поток записи в сокет

    /**
     * Конструктор - Запуск работы с новым клиентом:
     * Передача последних 10 сообщений и работа передачи сообщений от других клиентов, включая его
     * @param socket - сокет клиента
     * @throws IOException
     */

    public ServerChat(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println(socket.getInetAddress()); // ip addr Клиента
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        Server.story.printStory(out);// поток вывода передаётся для передачи истории последних 10 сообщений новому подключению
        start();// вызываем run()
    }
    /** Запуск потока.
     * */
    @Override
    public void run() {
        try {
            while (true) {
                String message = (String)in.readObject();
                System.out.println("Echoing: " + message.toString());
                Server.story.addStoryEl(message);
                for (ServerChat vr : Server.serverList) {
                    vr.send(message);// отcылаем принятое сообщение с привязанного клиента всем остальным, влючая его
                }
            }
        } catch (NullPointerException ignored) {this.downService();} catch (IOException e){
            this.downService();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция отправки сообщения клиента всем остальным подключенным клиентам, включая его
     * @param msg - сообщение
     */
    private void send(String msg) {
        try {
            out.writeObject(msg + "\n");
            /**flush() нужен для выталкивания оставшихся данных если такие есть, и очистки потока для дальнейших нужд*/
            out.flush();
        } catch (IOException ignored) {}

    }

    /**
     * закрытие сокета
     * прерывание и удаление из списка нитей(потоков)
     */
    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerChat vr : Server.serverList) {
                    if(vr.equals(this.socket)) vr.interrupt();
                    Server.serverList.remove(this.socket);
                }
            }
        } catch (IOException ignored) {}
    }
}