package org.example.command.list;

import org.example.command.Command;
import org.example.data.StudyGroupCsvParser;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

public class SaveCommand implements Command {
    private final StudyGroupCsvParser parser;

    public SaveCommand(StudyGroupCsvParser parser) {
        this.parser = parser;
    }

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        try {
            parser.saveToFile(collection);
            return "Коллекция сохранена в файл " + parser.getFilename();
        } catch (Exception e) {
            return "Ошибка сохранения: " + e.getMessage();
        }
    }

    @Override
    public String getName() { return "save"; }

    @Override
    public String getDescription() { return "сохранить коллекцию в файл"; }

    @Override
    public String getSyntax() { return "save"; }
}