package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

/**
 * Класс команды подсчёта отчисленных студентов
 * @author Pwsha
 * @version v1.3
 */
public class CountGreaterThanExpelledStudentsCommand implements Command {
    private final CollectionManager manager;

    public CountGreaterThanExpelledStudentsCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        return "Ошибка: используйте метод с числом";
    }

    public String execute(Integer expelledStudents) {
        long count = manager.getCollection().stream()
                .filter(g -> g.getExpelledStudents() > expelledStudents)
                .count();

        return "Количество элементов с expelledStudents > " + expelledStudents + ": " + count;
    }

    @Override
    public String getName() { return "count_greater_than_expelled_students"; }

    @Override
    public String getDescription() { return "количество элементов с expelledStudents > заданного"; }

    @Override
    public String getSyntax() { return "count_greater_than_expelled_students expelledStudents"; }
}