package org.example.command.list;

import org.example.command.Command;
import org.example.program.StudyGroup;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды info
 * @author Pwsha
 * @version v1.3
 */
public class InfoCommand implements Command {
    private final LocalDateTime initializationDate;

    /**
     * Конструктор со значением даты
     * @param initializationDate
     */
    public InfoCommand(LocalDateTime initializationDate) {
        this.initializationDate = initializationDate;
    }

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        return String.format(
                "Тип коллекции: %s\nДата инициализации: %s\nКоличество элементов: %d",
                collection.getClass().getSimpleName(),
                initializationDate,
                collection.size()
        );
    }

    @Override
    public String getName() { return "info"; }

    @Override
    public String getDescription() { return "информация о коллекции"; }

    @Override
    public String getSyntax() { return "info"; }
}