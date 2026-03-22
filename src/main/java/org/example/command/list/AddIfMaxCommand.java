package org.example.command.list;

import org.example.command.*;
import org.example.init.StudyGroup;

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
        StudyGroup newGroup;

        if (args.length == 0) {
            newGroup = CommandHelper.readStudyGroup(scanner, collection);
        } else if (args.length == 1) {
            String input = String.join(" ", args);
            if (input.startsWith("{") && input.endsWith("}")) {
                input = input.substring(1, input.length() - 1);
            }
            newGroup = GroupParser.parseFromString(input, collection);
        } else {
            return "Oшибка: введено неверное количество аргументов";
        }

        if (collection.isEmpty()) {
            collection.add(newGroup);
            return "Элемент добавлен (коллекция была пуста) с id: " + newGroup.getId();
        }

        StudyGroup max = collection.stream()
                .max(StudyGroup::compareTo)
                .orElse(null);

        if (newGroup.compareTo(max) > 0) {
            collection.add(newGroup);
            return "Элемент добавлен с id: " + newGroup.getId() + " (превышает максимальный)";
        } else {
            return "Элемент не добавлен: не превышает максимальный элемент коллекции";
        }
    }

    @Override
    public String getName() { return "add_if_max {element}"; }

    @Override
    public String getDescription() { return "добавить элемент, если он превышает максимальный"; }

    @Override
    public String getSyntax() { return "add_if_max"; }
}