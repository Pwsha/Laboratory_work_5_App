package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

/**
 * Класс команды обновления элемента по id
 * @author Pwsha
 * @version v1.3
 */
public class UpdateCommand implements Command {
    private final CollectionManager manager;

    public UpdateCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        return "Ошибка: используйте метод с id и group";
    }

    public String execute(Long id, StudyGroup group) {
        manager.removeById(id);
        manager.add(group);
        return "Элемент с id " + id + " успешно обновлен";
    }

    @Override
    public String getName() { return "update"; }

    @Override
    public String getDescription() { return "обновить элемент по id"; }

    @Override
    public String getSyntax() { return "update id"; }
}