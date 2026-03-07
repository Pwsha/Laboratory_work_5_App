package org.example.command.list;

import org.example.command.Command;
import org.example.command.CommandHelper;
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
        StudyGroup group = CommandHelper.readStudyGroup(scanner, collection);
        collection.add(group);
        return "Элемент добавлен с id: " + group.getId();
    }

    @Override
    public String getName() { return "add"; }

    @Override
    public String getDescription() { return "добавить новый элемент"; }

    @Override
    public String getSyntax() { return "add"; }
}