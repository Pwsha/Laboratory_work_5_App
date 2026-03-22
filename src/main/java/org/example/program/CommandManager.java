package org.example.program;

import org.example.command.Command;
import org.example.command.list.*;
import org.example.init.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс управления командами приложения
 * @author Pwsha
 * @version v1.3
 */
public class CommandManager {
    private final Map<String, Command> commands;
    private final LinkedList<String> commandHistory;
    private final LocalDateTime startTime;
    private final CollectionManager collectionManager;
    private final Scanner scanner;

    public CommandManager(CollectionManager collectionManager, Scanner scanner) {
        this.commands = new HashMap<>();
        this.commandHistory = new LinkedList<>();
        this.startTime = LocalDateTime.now();
        this.collectionManager = collectionManager;
        this.scanner = scanner;

        initializeCommands();
    }

    private void initializeCommands() {
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(startTime, collectionManager));
        commands.put("show", new ShowCommand());
        commands.put("add", new AddCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_by_id", new RemoveByIdCommand());
        commands.put("clear", new ClearCommand());
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("exit", new ExitCommand());
        commands.put("add_if_max", new AddIfMaxCommand());
        commands.put("remove_greater", new RemoveGreaterCommand());
        commands.put("history", new HistoryCommand(commandHistory));
        commands.put("remove_any_by_students_count", new RemoveAnyByStudentsCountCommand());
        commands.put("min_by_semester_enum", new MinBySemesterEnumCommand());
        commands.put("count_greater_than_expelled_students", new CountGreaterThanExpelledStudentsCommand());
        commands.put("execute_script", new ExecuteScriptCommand(commands));
    }

    public void run() {
        System.out.println("Программа запущена. Введите 'help' для справки.");

        while (true) {
            System.out.print("> ");
            String input;

            try {
                input = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("\nЗавершение программы...");
                break;
            }

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String cmdName = parts[0].toLowerCase();
            String cmdArgs = parts.length > 1 ? parts[1] : "";

            addToHistory(cmdName);

            Command command = commands.get(cmdName);
            if (command == null) {
                System.out.println("Неизвестная команда. Введите 'help'");
                continue;
            }

            String result = command.execute(new String[]{cmdArgs}, collectionManager.getCollection(), scanner);
            System.out.println(result);

        }
    }

    private void addToHistory(String command) {
        if (commands.containsKey(command)) {
            commandHistory.add(command);
            if (commandHistory.size() > 5) {
                commandHistory.removeFirst();
            }
        }
    }
}