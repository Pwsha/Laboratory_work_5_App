package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

/**
 * Класс команды удаления по id
 * @author Pwsha
 * @version v1.3
 */
public class RemoveByIdCommand implements Command {
    private final CollectionManager manager;

    public RemoveByIdCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        return "Ошибка: используйте метод с id";
    }

    public String execute(Long id) {
        boolean removed = manager.removeById(id);
        if (removed) {
            return "Элемент с id " + id + " удален";
        } else {
            return "Элемент с id " + id + " не найден";
        }
    }

    @Override
    public String getName() { return "remove_by_id"; }

    @Override
    public String getDescription() { return "удалить элемент по id"; }

    @Override
    public String getSyntax() { return "remove_by_id id"; }
}