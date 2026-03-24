package org.example.command.list;

import org.example.command.*;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;


/**
 * Класс команды добавления элемента если он больше других
 * @author Pwsha
 * @version v1.3
 */
public class AddIfMaxCommand implements Command {
    private final CollectionManager manager;

    public AddIfMaxCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        if (manager.getCollection().isEmpty()) {
            manager.add(group);
            return "Элемент добавлен (коллекция была пуста) c id" + group.getId();
        }

        StudyGroup max = manager.getCollection().stream()
                .max(StudyGroup::compareTo)
                .orElse(null);

        if (group.compareTo(max) > 0) {
            manager.add(group);
            return "Элемент добавлен (превышает максимальный) c id" + group.getId();
        }

        return "Элемент не добавлен (не превышает максимальный)";
    }


    @Override
    public String getName() { return "add_if_max {element}"; }

    @Override
    public String getDescription() { return "добавить элемент, если он превышает максимальный"; }

    @Override
    public String getSyntax() { return "add_if_max"; }
}