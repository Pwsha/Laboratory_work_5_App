package org.example.command.list;

import org.example.command.Command;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

public class ClearCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        int size = collection.size();
        collection.clear();
        return "Коллекция очищена. Удалено элементов: " + size;
    }

    @Override
    public String getName() { return "clear"; }

    @Override
    public String getDescription() { return "очистить коллекцию"; }

    @Override
    public String getSyntax() { return "clear"; }
}