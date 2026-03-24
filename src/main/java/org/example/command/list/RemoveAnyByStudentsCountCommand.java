package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

import java.util.Optional;

/**
 * Команда для удаления элемента по количеству студентов.
 * @author Pwsha
 * @version v1.3
 */
public class RemoveAnyByStudentsCountCommand implements Command {
    private final CollectionManager manager;

    public RemoveAnyByStudentsCountCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        return "Ошибка: используйте метод с studentsCount";
    }

    public String execute(Long studentsCount) {
        Optional<StudyGroup> toRemove = manager.getCollection().stream()
                .filter(g -> g.getStudentsCount() == studentsCount)
                .findFirst();

        if (toRemove.isPresent()) {
            manager.removeById(toRemove.get().getId());
            return "Элемент с studentsCount=" + studentsCount + " удален";
        } else {
            return "Элемент с studentsCount=" + studentsCount + " не найден";
        }
    }

    @Override
    public String getName() { return "remove_any_by_students_count"; }

    @Override
    public String getDescription() { return "удалить элемент по studentsCount"; }

    @Override
    public String getSyntax() { return "remove_any_by_students_count studentsCount"; }
}