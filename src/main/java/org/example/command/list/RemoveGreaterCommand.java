package org.example.command.list;

import org.example.command.*;
import org.example.init.StudyGroup;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды удаления всех элементов больше заданного
 * @author Pwsha
 * @version v1.3
 */
public class RemoveGreaterCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }

        StudyGroup reference;

        if (args.length == 0) {
            System.out.println("Введите эталонный элемент:");
            reference = CommandHelper.readStudyGroup(scanner, collection);
        } else if (args.length == 1) {
            String input = String.join(" ", args);
            if (input.startsWith("{") && input.endsWith("}")) {
                input = input.substring(1, input.length() - 1);
            }
            reference = GroupParser.parseFromString(input, collection);
        } else {
            return "Ошибка: введено неверное количество аргументов";
        }

        int initialSize = collection.size();

        collection.removeIf(group -> group.compareTo(reference) > 0);

        int removed = initialSize - collection.size();

        if (removed == 0) {
            return "Нет элементов, превышающих заданный";
        } else {
            return "Удалено элементов: " + removed;
        }
    }

    @Override
    public String getName() { return "remove_greater {element}"; }

    @Override
    public String getDescription() { return "удалить элементы, превышающие заданный"; }

    @Override
    public String getSyntax() { return "remove_greater"; }
}