package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Многопользовательский чат, графическое окно
 * @author Василий
 */
public class ClientWindow extends JFrame{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String userName;
    private String message;
    private Socket clientSocket;
    private JTextField messageField; // поле ввода сообщения
    private JTextArea textArea;// поле сообщений
    private Message messageObject;

    /**
     * Конструктор - окно для чата, установка соединения с сервером
     * @param serverHost - ip сервера
     * @param serverPort - port сервер
     * @param userName - логин
     */
    public ClientWindow(String serverHost, int serverPort, String userName) {
        this.userName = userName;
        try {
            this.clientSocket = new Socket(serverHost, serverPort); // Создаем соединение с сервером
        } catch (IOException e) {
            System.out.println("Неверный Socket");
        }
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            messageObject = new Message(userName, "присоединился к чату!");
            out.writeObject(messageObject.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("123");
        setBounds(600, 300, 600, 500); // Размер окна
        setTitle("Чат");// Название окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        textArea.setEditable(false);// выключение возможности редактирования текста
        textArea.setLineWrap(true);// Длинные сообщения будут укладываться в строки, если размер текста выходит за рамки окна
        JScrollPane jsp = new JScrollPane(textArea); // Прокрутка поля сообщений
        add(jsp, BorderLayout.CENTER); // Размещаем по центру поле сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton buttonSend = new JButton("Отправить");
        bottomPanel.add(buttonSend, BorderLayout.EAST);
        messageField = new JTextField("Введите сообщение");
        bottomPanel.add(messageField, BorderLayout.CENTER);
        /**
         * При наведение на поле отправки сообщений, поле очищается
         * При нажатии клавиши enter - отправляем сообщение
         */
        buttonSend.addActionListener(e -> {
            if (!messageField.getText().trim().isEmpty()) {
                try {
                    sendMsg();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //sendMsg();
            }
        });
        messageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                messageField.setText("");
            }
        });
        messageField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.VK_ENTER == e.getKeyCode()) {
                    try {
                        sendMsg();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        /**
         * В отдельном потоке начинаем обмен сообщениями с сервером
         */
        new Thread(() -> {
            try {
                while (true) {
                    String inMessage = (String)in.readObject();
                    textArea.append(inMessage);// выводим сообщение
                    textArea.append("\n");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Какая-то ошибка со считыванием");
            }
        }).start();
        /**
         * обработчик события закрытия окна клиентского приложения(При закрытии, программа может работать в фоновом режиме)
         * */

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent l) {
                super.windowClosing(l);
                try {
                    // отправляем служебное сообщение, которое является признаком того, что клиент вышел из чата
                    messageObject = new Message(userName, "вышел из чата!");
                    out.writeObject(messageObject.toString());
                    out.flush();
                    out.close();
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Какая-то ошибка с выходом из чата!");
                }
            }
        });
        setVisible(true);
    }

    /**
     * Функция для отправки сообщения на сервер
     */
    public void sendMsg() throws IOException {
        message = messageField.getText();
        // Отправляем обьект сообщения на сервер
        messageObject = new Message(userName, message);
        out.writeObject(messageObject.toString());
        out.flush();
        messageField.setText("");
    }
}



