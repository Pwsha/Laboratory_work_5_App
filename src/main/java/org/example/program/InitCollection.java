package org.example.program;

import org.example.command.*;
import org.example.command.list.*;
import org.example.data.StudyGroupCsvParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Инициализация коллекции и команд
 * @author Pwsha
 * @version v1.3
 */
public class InitCollection {
    private final HashSet<StudyGroup> collection;
    private final LocalDateTime initializationDate;
    private final StudyGroupCsvParser csvParser;
    private final Scanner scanner;
    private final LinkedList<String> commandHistory;
    private final Map<String, Command> commands;

    public InitCollection(String filename) {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
        this.csvParser = new StudyGroupCsvParser(filename);
        this.scanner = new Scanner(System.in);
        this.commandHistory = new LinkedList<>();
        this.commands = new HashMap<>();

        initializeCommands();
        loadCollection();
    }

    private void initializeCommands() {
        Map<String, Command> commandMap = Map.ofEntries(
                Map.entry("info", new InfoCommand(initializationDate, this)),
                Map.entry("show", new ShowCommand()),
                Map.entry("add", new AddCommand()),
                Map.entry("update", new UpdateCommand()),
                Map.entry("remove_by_id", new RemoveByIdCommand()),
                Map.entry("clear", new ClearCommand()),
                Map.entry("save", new SaveCommand(csvParser)),
                Map.entry("exit", new ExitCommand()),
                Map.entry("add_if_max", new AddIfMaxCommand()),
                Map.entry("remove_greater", new RemoveGreaterCommand()),
                Map.entry("history", new HistoryCommand(commandHistory)),
                Map.entry("remove_any_by_students_count", new RemoveAnyByStudentsCountCommand()),
                Map.entry("min_by_semester_enum", new MinBySemesterEnumCommand()),
                Map.entry("count_greater_than_expelled_students", new CountGreaterThanExpelledStudentsCommand())
        );

        commands.putAll(commandMap);

        commands.put("execute_script", new ExecuteScriptCommand(commands));
        commands.put("help", new HelpCommand(commands));
    }

    private void loadCollection() {
        try {
            if (!csvParser.isFileAccessible()) {
                System.out.println("Файл не найден, будет создана пустая коллекция");
                csvParser.createEmptyFile();
                return;
            }

            HashSet<StudyGroup> loaded = csvParser.loadFromFile();
            collection.addAll(loaded);
            System.out.println("Загружено элементов: " + loaded.size());

        } catch (IOException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
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

            String[] parts = input.split("\\s+");
            String cmdName = parts[0].toLowerCase();
            String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);

            addToHistory(cmdName);

            Command command = commands.get(cmdName);
            if (command == null) {
                System.out.println("Неизвестная команда. Введите 'help'");
                continue;
            }

            try {
                String result = command.execute(cmdArgs, collection, scanner);
                System.out.println(result);

                if (cmdName.equals("exit")) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void addToHistory(String command) {
        if (commands.containsKey(command)) {
            commandHistory.add(command);
            if (commandHistory.size() > 5) {
                commandHistory.removeFirst();
            }
        }
    }

    public HashSet<StudyGroup> getCollection(){
        return collection;
    }

    public Scanner getScanner(){
        return scanner;
    }
}