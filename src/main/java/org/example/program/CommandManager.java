package org.example.program;

import org.example.command.Command;
import org.example.command.CommandStatus;
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
    private final CommandExecute executor;

    public CommandManager(CollectionManager collectionManager, Scanner scanner) {
        this.commands = new HashMap<>();
        this.commandHistory = new LinkedList<>();
        this.startTime = LocalDateTime.now();
        this.collectionManager = collectionManager;
        this.scanner = scanner;
        this.executor = new CommandExecute(collectionManager, scanner);

        initializeCommands();
    }

    private void initializeCommands() {
        commands.put("help", new HelpCommand(commands));
        commands.put("info", new InfoCommand(collectionManager, startTime));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager));
        commands.put("update", new UpdateCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager));
        commands.put("exit", new ExitCommand());
        commands.put("add_if_max", new AddIfMaxCommand(collectionManager));
        commands.put("remove_greater", new RemoveGreaterCommand(collectionManager));
        commands.put("history", new HistoryCommand(commandHistory));
        commands.put("remove_any_by_students_count", new RemoveAnyByStudentsCountCommand(collectionManager));
        commands.put("min_by_semester_enum", new MinBySemesterEnumCommand(collectionManager));
        commands.put("count_greater_than_expelled_students", new CountGreaterThanExpelledStudentsCommand(collectionManager));
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

            try {
                String result = executor.execute(command, cmdName, cmdArgs);
                System.out.println(result);
                if (cmdName.equals("exit")) break;
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
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