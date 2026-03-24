package org.example.program;

import org.example.command.Command;
import org.example.command.CommandStatus;
import org.example.command.list.*;
import org.example.init.StudyGroup;

import java.util.Scanner;

/**
 * Класс вызова команд
 * @author Pwsha
 * @version v1.1
 */
public class CommandExecute {

    private final CollectionManager collectionManager;
    private final Scanner scanner;

    public CommandExecute(CollectionManager collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    public String execute(Command command, String cmdName, String cmdArgs) {
        switch (cmdName) {
            case "add":
            case "add_if_max":
            case "remove_greater":
                StudyGroup group = cmdArgs.isEmpty()
                        ? CommandStatus.fromConsole(scanner, collectionManager.getCollection())
                        : CommandStatus.fromString(cmdArgs, collectionManager.getCollection());
                return command.execute(group);

            case "update":
                String[] updateArgs = cmdArgs.split("\\s+", 2);
                if (updateArgs.length < 2) {
                    return "Ошибка: формат команды update id {element}";
                }
                Long id = Long.parseLong(updateArgs[0]);
                StudyGroup newGroup = CommandStatus.fromString(updateArgs[1], collectionManager.getCollection());
                return ((UpdateCommand) command).execute(id, newGroup);

            case "remove_by_id":
                if (cmdArgs.isEmpty()) {
                    return "Ошибка: укажите id";
                }
                return ((RemoveByIdCommand) command).execute(Long.parseLong(cmdArgs));

            case "remove_any_by_students_count":
                if (cmdArgs.isEmpty()) {
                    return "Ошибка: укажите количество студентов";
                }
                return ((RemoveAnyByStudentsCountCommand) command).execute(Long.parseLong(cmdArgs));

            case "count_greater_than_expelled_students":
                if (cmdArgs.isEmpty()) {
                    return "Ошибка: укажите количество отчисленных";
                }
                return ((CountGreaterThanExpelledStudentsCommand) command).execute(Integer.parseInt(cmdArgs));

            case "execute_script":
                if (cmdArgs.isEmpty()) {
                    return "Ошибка: укажите имя файла";
                }
                return ((ExecuteScriptCommand) command).execute(cmdArgs, collectionManager.getCollection(), scanner, collectionManager);
            default:
                return command.execute((StudyGroup) null);
        }
    }
}