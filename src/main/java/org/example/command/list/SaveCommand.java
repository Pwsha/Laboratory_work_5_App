package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

import java.io.IOException;

/**
 * Класс команды сохранения файла
 * @author Pwsha
 * @version v1.3
 */
public class SaveCommand implements Command {
    private final CollectionManager manager;

    public SaveCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        try {
            manager.save();
            return "Коллекция сохранена в файл";
        } catch (IOException e) {
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