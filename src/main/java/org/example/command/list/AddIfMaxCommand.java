package org.example.command.list;

import org.example.command.*;
import org.example.data.ComparatorCollection;
import org.example.program.StudyGroup;

import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды добавления элемента если он больше других
 * @author Pwsha
 * @version v1.3
 */
public class AddIfMaxCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        StudyGroup newGroup = getNewElement(args, collection, scanner);
        if (newGroup == null) return "Ошибка создания элемента";

        if (collection.isEmpty()) {
            collection.add(newGroup);
            return "Элемент добавлен (коллекция была пуста) с id: " + newGroup.getId();
        }

        StudyGroup max = ComparatorCollection.findMax(collection);

        if (ComparatorCollection.isGreater(newGroup, max)) {
            collection.add(newGroup);
            return "Элемент добавлен с id: " + newGroup.getId() + " (превышает максимальный)";
        } else {
            return "Элемент не добавлен: не превышает максимальный";
        }
    }

    private StudyGroup getNewElement(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        try {
            if (args.length == 0) {
                return CommandHelper.readStudyGroup(scanner, collection);
            } else if (args.length == 1) {
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
    public String getName() { return "add_if_max {element}"; }

    @Override
    public String getDescription() { return "добавить элемент, если он превышает максимальный"; }

    @Override
    public String getSyntax() { return "add_if_max"; }
}