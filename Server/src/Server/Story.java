package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 * Класс для истории сообщений
 */
public class Story {
    /**Связной список для хранения истории сообщений */
    private LinkedList<String> story = new LinkedList<>();

    /**
     * Функция добавления нового элемента в список
     * Если сообщений больше 10, удаляем первое и добавляем новое в конец, иначе просто добавляем
     * @param el - сообщение
     */
    public void addStoryEl(String el) {
        // если сообщений больше 10, удаляем первое и добавляем новое, иначе просто добавить
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(el);
        } else {
            story.add(el);
        }
    }

    /**
     * Функция для отправки сообщений из списка в поток вывода, для дальнейшей передачи подключённым клиентам
     * @param writer - поток вывода
     */
    public void printStory(ObjectOutputStream writer) {

        if(story.size() > 0) {
            try {
                for (String vr : story) {
                    writer.writeObject(vr + "\n");
                }
                writer.writeObject("...." + "\n");
                writer.flush();
            } catch (IOException ignored) {}

        }

    }
}
