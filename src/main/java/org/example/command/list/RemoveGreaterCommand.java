package org.example.command.list;

import org.example.command.*;
import org.example.data.ComparatorCollection;
import org.example.program.StudyGroup;

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

        StudyGroup reference = getReferenceElement(args, collection, scanner);
        if (reference == null) return "Ошибка создания эталонного элемента";

        int initialSize = collection.size();

        collection.removeIf(group -> ComparatorCollection.isGreater(group, reference));

        int removed = initialSize - collection.size();

        return removed == 0 ? "Нет элементов, превышающих заданный" : "Удалено элементов: " + removed;
    }

    private StudyGroup getReferenceElement(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        try {
            if (args.length == 0) {
                System.out.println("Введите эталонный элемент:");
                return CommandHelper.readStudyGroup(scanner, collection);
            } else if (args.length == 1){
                String input = String.join(" ", args);
                if (input.startsWith("{") && input.endsWith("}")) {
                    input = input.substring(1, input.length() - 1);
                }
                return StringArguments.parseFromString(input, collection);
            } else {
                System.out.println("Ошибка: введено неверное количество аргументов");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public String getName() { return "remove_greater {element}"; }

    @Override
    public String getDescription() { return "удалить элементы, превышающие заданный"; }

    @Override
    public String getSyntax() { return "remove_greater"; }
}