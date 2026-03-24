package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;


/**
 * Класс команды очищения коллекции
 * @author Pwsha
 * @version v1.3
 */
public class ClearCommand implements Command {
    private final CollectionManager manager;

    public ClearCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        int size = manager.getCollection().size();
        manager.clear();
        return "Коллекция очищена. Удалено элементов: " + size;
    }

    @Override
    public String getName() { return "clear"; }

    @Override
    public String getDescription() { return "очистить коллекцию"; }

    @Override
    public String getSyntax() { return "clear"; }
}