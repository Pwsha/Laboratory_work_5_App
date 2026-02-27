package org.example.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class ReadCSV {
    public static HashSet<StudyGroup> collection;
    private final LocalDateTime initializationDate;
    public static String filename;
    private final Scanner scanner;
    private final LinkedList<String> commandHistory;

    public ReadCSV(String filename) {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
        this.filename = filename;
        this.scanner = new Scanner(System.in);
        this.commandHistory = new LinkedList<>();
    }

    public static void loadCollectionFromFile() {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("Файл не найден. Будет создана пустая коллекция.");
            return;
        }

        if (!file.canRead()) {
            System.out.println("Нет прав на чтение файла. Будет создана пустая коллекция.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            if (!fileScanner.hasNextLine()) {
                System.out.println("Файл пуст.");
                return;
            }

            // Пропускаем заголовок
            String header = fileScanner.nextLine();

            int lineNumber = 1;
            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty()) continue;

                try {
                    StudyGroup group = new StudyGroup();
                    if (group != null) {
                        collection.add(group);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }

            System.out.println("Загружено элементов: " + collection.size());
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
