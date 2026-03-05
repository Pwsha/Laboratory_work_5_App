package org.example.command.list;

import org.example.command.Command;
import org.example.program.StudyGroup;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class ExecuteScriptCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        if (args.length == 0) {
            return "Ошибка: укажите имя файла";
        }

        String filename = args[0];
        File file = new File(filename);

        if (!file.exists()) {
            return "Файл не найден: " + filename;
        }

        if (!file.canRead()) {
            return "Нет прав на чтение файла: " + filename;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            int lineCount = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                lineCount++;
                if (line.isEmpty() || line.startsWith("#")) continue;
                System.out.println("[СКРИПТ] " + line);
            }
            return "Скрипт выполнен. Обработано строк: " + lineCount;
        } catch (FileNotFoundException e) {
            return "Ошибка чтения файла: " + e.getMessage();
        }
    }

    @Override
    public String getName() { return "execute_script"; }

    @Override
    public String getDescription() { return "выполнить скрипт из файла"; }

    @Override
    public String getSyntax() { return "execute_script file_name"; }
}