package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

/**
 * Класс команды отображения коллекции
 * @author Pwsha
 * @version v1.3
 */
public class ShowCommand implements Command {
    private final CollectionManager manager;

    public ShowCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        if (manager.getCollection().isEmpty()) {
            return "Коллекция пуста";
        }

        StringBuilder sb = new StringBuilder("Элементы коллекции:\n");
        manager.getCollection().stream()
                .sorted()
                .forEach(g -> sb.append("  ").append(g).append("\n"));
        return sb.toString();
    }

    @Override
    public String getName() { return "show"; }

    @Override
    public String getDescription() { return "показать все элементы"; }

    @Override
    public String getSyntax() { return "show"; }
}