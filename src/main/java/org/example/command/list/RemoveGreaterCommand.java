package org.example.command.list;

import org.example.command.Command;
import org.example.command.CommandHelper;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

public class RemoveGreaterCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        StudyGroup reference = CommandHelper.readStudyGroup(scanner, collection);

        int initialSize = collection.size();
        collection.removeIf(g -> g.compareTo(reference) > 0);
        int removed = initialSize - collection.size();

        return "Удалено элементов: " + removed;
    }

    @Override
    public String getName() { return "remove_greater"; }

    @Override
    public String getDescription() { return "удалить элементы, превышающие заданный"; }

    @Override
    public String getSyntax() { return "remove_greater"; }
}