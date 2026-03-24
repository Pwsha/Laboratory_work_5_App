package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;
import org.example.program.*;

import java.time.LocalDateTime;

/**
 * Класс команды вывода информации о коллекции.
 * @author Pwsha
 * @version v1.3
 */
public class InfoCommand implements Command {
    private final CollectionManager manager;
    private final LocalDateTime startTime;

    public InfoCommand(CollectionManager manager, LocalDateTime startTime) {
        this.manager = manager;
        this.startTime = startTime;
    }

    @Override
    public String execute(StudyGroup group) {
        return String.format(
                "Тип коллекции: %s\nДата инициализации: %s\nКоличество элементов: %d",
                manager.getCollection().getClass().getSimpleName(),
                startTime,
                manager.getCollection().size()
        );
    }

    @Override
    public String getName() { return "info"; }

    @Override
    public String getDescription() { return "информация о коллекции"; }

    @Override
    public String getSyntax() { return "info"; }
}