package org.example;

import org.example.command.*;
import org.example.command.list.*;
import org.example.data.StudyGroupCsvParser;
import org.example.program.StudyGroup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Главный класс приложения
 * @author Pwsha
 * @version v1.3
 */
public class App {
    /** Поле коллекции */
    private final HashSet<StudyGroup> collection;
    /** Поле даты */
    private final LocalDateTime initializationDate;
    /** Поле парсера */
    private final StudyGroupCsvParser csvParser;
    /** Поле сканера */
    private final Scanner scanner;
    /** Поле для команды история */
    private final LinkedList<String> commandHistory;
    /** Поле команд*/
    private final Map<String, Command> commands;

    /**
     * Конструктор со всеми значениями
     * @param filename - файл
     */
    public App(String filename) {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
        this.csvParser = new StudyGroupCsvParser(filename);  // Изменено здесь
        this.scanner = new Scanner(System.in);
        this.commandHistory = new LinkedList<>();
        this.commands = new HashMap<>();

        initializeCommands();
    }

    /**
     * Метод инициализации команд
     */
    private void initializeCommands() {
        Map<String, Command> commandMap = Map.ofEntries(
                Map.entry("info", new InfoCommand(initializationDate)),
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
                Map.entry("count_greater_than_expelled_students", new CountGreaterThanExpelledStudentsCommand()),
                Map.entry("execute_script", new ExecuteScriptCommand())
        );

        commands.putAll(commandMap);

        commands.put("help", new HelpCommand(commands));
    }
    /**
     * Метод загрузки коллекции из файла
     */
    private void loadCollection() {
        try {
            if (!csvParser.isFileAccessible()) {
                System.out.println("Файл не найден, будет создана пустая коллекция");
                csvParser.createEmptyFile();
                return;
            }

            HashSet<StudyGroup> loaded = csvParser.loadFromFile();

            int validCount = 0;
            int invalidCount = 0;

            for (StudyGroup group : loaded) {
                if (group.isValid()) {
                    collection.add(group);
                    validCount++;
                } else {
                    invalidCount++;
                }
            }

            System.out.println("Загружено элементов: " + validCount);
            if (invalidCount > 0) {
                System.out.println("Пропущено некорректных элементов: " + invalidCount);
            }

        } catch (IOException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
        }
    }

    /**
     * Метод для удаления команд из истории
     * @param command
     */
    private void addToHistory(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > 5) {
            commandHistory.removeFirst();
        }
    }

    /**
     * Метод запуска
     */
    public void run() {
        loadCollection();
        System.out.println("Программа запущена. Введите 'help' для справки.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

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
                System.out.println("Ошибка выполнения команды: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите имя файла");
            System.out.println("Пример: java -cp . org.example.App data.csv");
            System.exit(1);
        }

        App app = new App(args[0]);
        app.run();
    }
}