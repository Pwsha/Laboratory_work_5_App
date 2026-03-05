package org.example;

import org.example.command.*;
import org.example.command.list.*;
import org.example.data.StudyGroupCsvParser;
import org.example.program.StudyGroup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class App {
    private final HashSet<StudyGroup> collection;
    private final LocalDateTime initializationDate;
    private final StudyGroupCsvParser csvParser;
    private final Scanner scanner;
    private final LinkedList<String> commandHistory;
    private final Map<String, Command> commands;

    public App(String filename) {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
        this.csvParser = new StudyGroupCsvParser(filename);  // Изменено здесь
        this.scanner = new Scanner(System.in);
        this.commandHistory = new LinkedList<>();
        this.commands = new HashMap<>();

        initializeCommands();
    }

    private void initializeCommands() {
        // Создаем команды
        Command info = new InfoCommand(initializationDate);
        Command show = new ShowCommand();
        Command add = new AddCommand();
        Command update = new UpdateCommand();
        Command removeById = new RemoveByIdCommand();
        Command clear = new ClearCommand();
        Command save = new SaveCommand(csvParser);
        Command exit = new ExitCommand();
        Command addIfMax = new AddIfMaxCommand();
        Command removeGreater = new RemoveGreaterCommand();
        Command history = new HistoryCommand(commandHistory);
        Command removeByStudentsCount = new RemoveAnyByStudentsCountCommand();
        Command minBySemester = new MinBySemesterEnumCommand();
        Command countGreater = new CountGreaterThanExpelledStudentsCommand();
        Command executeScript = new ExecuteScriptCommand();

        // Регистрируем команды
        commands.put(info.getName(), info);
        commands.put(show.getName(), show);
        commands.put(add.getName(), add);
        commands.put(update.getName(), update);
        commands.put(removeById.getName(), removeById);
        commands.put(clear.getName(), clear);
        commands.put(save.getName(), save);
        commands.put(exit.getName(), exit);
        commands.put(addIfMax.getName(), addIfMax);
        commands.put(removeGreater.getName(), removeGreater);
        commands.put(history.getName(), history);
        commands.put(removeByStudentsCount.getName(), removeByStudentsCount);
        commands.put(minBySemester.getName(), minBySemester);
        commands.put(countGreater.getName(), countGreater);
        commands.put(executeScript.getName(), executeScript);

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


    private void addToHistory(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > 5) {
            commandHistory.removeFirst();
        }
    }


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