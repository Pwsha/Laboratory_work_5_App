package org.example.command.list;

import org.example.command.*;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды добавления объекта
 * @author Pwsha
 * @version v1.3
 */
public class AddCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        StudyGroup group;
        if (args.length == 0) {
            group = CommandHelper.readStudyGroup(scanner, collection);
        } else if (args.length == 1) {
            String input = String.join(" ", args);
            if (input.startsWith("{") && input.endsWith("}")) {
                input = input.substring(1, input.length() - 1);
            }
            group = StringArguments.parseFromString(input, collection);
        } else {
            return "Введено неверное количество аргументов";
        }

        collection.add(group);
        return "Элемент успешно добавлен с id: " + group.getId();
    }

    @Override
    public String getName() { return "add"; }

    @Override
    public String getDescription() { return "добавить новый элемент"; }

    @Override
    public String getSyntax() { return "add"; }
}