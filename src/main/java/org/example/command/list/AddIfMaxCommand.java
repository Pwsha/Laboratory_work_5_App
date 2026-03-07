package org.example.command.list;

import org.example.command.Command;
import org.example.command.CommandHelper;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды добавления объекта с большим id
 * @author Pwsha
 * @version v1.3
 */
public class AddIfMaxCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        StudyGroup newGroup = CommandHelper.readStudyGroup(scanner, collection);

        StudyGroup max = collection.stream().max(StudyGroup::compareTo).orElse(null);

        if (max == null || newGroup.compareTo(max) > 0) {
            collection.add(newGroup);
            return "Элемент добавлен с id: " + newGroup.getId();
        } else {
            return "Элемент не добавлен (не превышает максимальный)";
        }
    }

    @Override
    public String getName() { return "add_if_max"; }

    @Override
    public String getDescription() { return "добавить, если превышает максимальный"; }

    @Override
    public String getSyntax() { return "add_if_max"; }
}