package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.CollectionManager;

import java.util.Comparator;

/**
 * Класс команды для поиска объекта с минимальным семестром
 * @author Pwsha
 * @version v1.3
 */
public class MinBySemesterEnumCommand implements Command {
    private final CollectionManager manager;

    public MinBySemesterEnumCommand(CollectionManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(StudyGroup group) {
        if (manager.getCollection().isEmpty()) {
            return "Коллекция пуста";
        }

        StudyGroup min = manager.getCollection().stream()
                .min(Comparator.comparing(StudyGroup::getSemesterEnum))
                .orElse(null);

        return "Элемент с минимальным semesterEnum:\n" + min;
    }


    @Override
    public String getName() { return "min_by_semester_enum"; }

    @Override
    public String getDescription() { return "элемент с минимальным semesterEnum"; }

    @Override
    public String getSyntax() { return "min_by_semester_enum"; }
}