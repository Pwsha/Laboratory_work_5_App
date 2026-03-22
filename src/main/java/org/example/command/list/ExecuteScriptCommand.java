package org.example.command.list;

import org.example.command.Command;
import org.example.init.StudyGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Команда для выполнения скрипта из файла.
 * @author Pwsha
 * @version v1.3
 */
public class ExecuteScriptCommand implements Command {

    private final Map<String, Command> commands;
    private static Set<String> executingScripts = new HashSet<>();

    public ExecuteScriptCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner mainScanner) {
        if (args.length == 0) {
            return "Ошибка: укажите имя файла со скриптом";
        } else if (args.length > 1) {
            return "Ошибка: указано больше одного аргумента";
        }


        String filename = args[0];
        File scriptFile = new File(filename);

        if (!scriptFile.exists()) {
            return "Ошибка: файл не найден: " + filename;
        }

        String absolutePath = scriptFile.getAbsolutePath();
        if (executingScripts.contains(absolutePath)) {
            return "Ошибка: обнаружен рекурсивный вызов скрипта " + filename;
        }

        executingScripts.add(absolutePath);
        StringBuilder result = new StringBuilder();
        result.append("Выполнение скрипта: ").append(filename).append("\n");

        try (Scanner scriptScanner = new Scanner(scriptFile)) {
            int lineNumber = 0;
            int executedCommands = 0;

            while (scriptScanner.hasNextLine()) {
                lineNumber++;
                String line = scriptScanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                result.append("[").append(lineNumber).append("] ").append(line).append("\n");

                String[] parts = line.split("\\s+", 2);
                String cmdName = parts[0].toLowerCase();
                String cmdArgs = parts.length > 1 ? parts[1] : "";

                Command command = commands.get(cmdName);
                if (command == null) {
                    result.append("  Ошибка: неизвестная команда '").append(cmdName).append("'\n");
                    continue;
                }

                try {
                    String cmdResult;

                    if (cmdArgs.startsWith("{")) {
                        String[] argArray = new String[]{cmdArgs};
                        cmdResult = command.execute(argArray, collection, mainScanner);
                    } else {
                        String[] argArray = cmdArgs.isEmpty() ? new String[0] : cmdArgs.split("\\s+");
                        cmdResult = command.execute(argArray, collection, mainScanner);
                    }

                    result.append("  ").append(cmdResult).append("\n");
                    executedCommands++;

                } catch (Exception e) {
                    result.append("  Ошибка: ").append(e.getMessage()).append("\n");
                }
            }
            result.append("Скрипт выполнен. Выполнено команд: ").append(executedCommands);

        } catch (FileNotFoundException e) {
            result.append("Ошибка при чтении файла: ").append(e.getMessage());
        } finally {
            executingScripts.remove(absolutePath);
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла";
    }

    @Override
    public String getSyntax() {
        return "execute_script file_name";
    }
}