package org.example.command.list;

import org.example.command.Command;
import org.example.program.StudyGroup;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Команда для удаления элемента по количеству студентов.
 * @author Pwsha
 * @version v1.3
 */
public class RemoveAnyByStudentsCountCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        if (args.length == 0) {
            return "Ошибка: укажите studentsCount";
        }

        try {
            long count = Long.parseLong(args[0]);
            StudyGroup toRemove = collection.stream()
                    .filter(g -> g.getStudentsCount() == count)
                    .findFirst()
                    .orElse(null);

            if (toRemove != null) {
                collection.remove(toRemove);
                return "Элемент с studentsCount=" + count + " удален (id=" + toRemove.getId() + ")";
            } else {
                return "Элемент с studentsCount=" + count + " не найден";
            }
        } catch (NumberFormatException e) {
            return "Ошибка: studentsCount должен быть числом";
        }
    }

    @Override
    public String getName() { return "remove_any_by_students_count"; }

    @Override
    public String getDescription() { return "удалить элемент по studentsCount"; }

    @Override
    public String getSyntax() { return "remove_any_by_students_count studentsCount"; }
}