package org.example.program;

import org.example.Command.*;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {
    private static final String CSV_HEADER = "id,name,coordinates_x,coordinates_y,creationDate,studentsCount,expelledStudents,formOfEducation,semesterEnum,groupAdmin_name,groupAdmin_birthday,groupAdmin_weight,groupAdmin_passportID,groupAdmin_location_x,groupAdmin_location_y,groupAdmin_location_z";
    private static final int COMMAND_HISTORY_SIZE = 5;

    private HashSet<StudyGroup> collection;
    private final LocalDateTime initializationDate;
    private final String filename;
    private final Scanner scanner;
    private final LinkedList<String> commandHistory;


    public App(String filename) {
        this.collection = new HashSet<>();
        this.initializationDate = LocalDateTime.now();
        this.filename = filename;
        this.scanner = new Scanner(System.in);
        this.commandHistory = new LinkedList<>();
    }

    //Запуск
    public void run() {
        ReadCSV.loadCollectionFromFile();
        System.out.println("Программа запущена. Введите 'help' для списка команд.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            addToHistory(command);

            try {
                executeCommand(command, argument);
            } catch (Exception e) {
                System.out.println("Ошибка выполнения команды: " + e.getMessage());
            }
        }
    }

    private void addToHistory(String command) {
        commandHistory.add(command);
        if (commandHistory.size() > COMMAND_HISTORY_SIZE) {
            commandHistory.removeFirst();
        }
    }

    //Выполнение команд пользователя
    private void executeCommand(String command, String argument) {
        switch (command) {
            case "help":
                help.printHelp();
                break;
            case "info":
                printInfo();
                break;
            case "show":
                showCollection();
                break;
            case "add":
                addElement();
                break;
            case "update":
                updateElement(argument);
                break;
            case "remove_by_id":
                removeById(argument);
                break;
            case "clear":
                clearCollection();
                break;
            case "save":
                saveCollectionToFile();
                break;
            case "execute_script":
                executeScript(argument);
                break;
            case "exit":
                exit.exitProgram();
                break;
            case "add_if_max":
                addIfMax();
                break;
            case "remove_greater":
                removeGreater();
                break;
            case "history":
                showHistory();
                break;
            case "remove_any_by_students_count":
                removeAnyByStudentsCount(argument);
                break;
            case "min_by_semester_enum":
                minBySemesterEnum();
                break;
            case "count_greater_than_expelled_students":
                countGreaterThanExpelledStudents(argument);
                break;
            default:
                System.out.println("Неизвестная команда. Введите 'help' для справки.");
        }
    }

    private void printInfo() {
        System.out.println("Тип коллекции: " + collection.getClass().getName());
        System.out.println("Дата инициализации: " + initializationDate);
        System.out.println("Количество элементов: " + collection.size());
    }

    private void showCollection() {
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста.");
            return;
        }

        collection.stream()
                .sorted()
                .forEach(System.out::println);
    }

    private void addElement() {
        StudyGroup group = readStudyGroup();
        if (group != null) {
            collection.add(group);
            System.out.println("Элемент успешно добавлен с id: " + group.getId());
        }
    }

    private void updateElement(String argument) {
        try {
            Long id = Long.parseLong(argument.trim());
            Optional<StudyGroup> existing = collection.stream()
                    .filter(g -> g.getId().equals(id))
                    .findFirst();

            if (existing.isPresent()) {
                collection.remove(existing.get());
                StudyGroup newGroup = readStudyGroup();
                newGroup.setId(id); // Сохраняем оригинальный id
                collection.add(newGroup);
                System.out.println("Элемент с id " + id + " успешно обновлен.");
            } else {
                System.out.println("Элемент с id " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: id должен быть числом.");
        }
    }

    private void removeById(String argument) {
        try {
            Long id = Long.parseLong(argument.trim());
            boolean removed = collection.removeIf(g -> g.getId().equals(id));

            if (removed) {
                System.out.println("Элемент с id " + id + " успешно удален.");
            } else {
                System.out.println("Элемент с id " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: id должен быть числом.");
        }
    }

    private void clearCollection() {
        collection.clear();
        System.out.println("Коллекция очищена.");
    }

    private void saveCollectionToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(CSV_HEADER);

            for (StudyGroup group : collection) {
                writer.println(new Parsing_csv());
            }

            System.out.println("Коллекция успешно сохранена в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    private void executeScript(String filename) {
        File scriptFile = new File(filename);

        if (!scriptFile.exists()) {
            System.out.println("Ошибка: файл не найден.");
            return;
        }

        if (!scriptFile.canRead()) {
            System.out.println("Ошибка: нет прав на чтение файла.");
            return;
        }

        try (Scanner scriptScanner = new Scanner(scriptFile)) {
            while (scriptScanner.hasNextLine()) {
                String line = scriptScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                System.out.println("Выполнение: " + line);

                String[] parts = line.split("\\s+", 2);
                String command = parts[0].toLowerCase();
                String argument = parts.length > 1 ? parts[1] : "";

                // Для скриптов нужно обрабатывать ввод данных особым образом
                if (command.equals("add") || command.equals("add_if_max") || command.equals("update")) {
                    // Временно подменяем Scanner для чтения данных из скрипта
                    Scanner oldScanner = this.scanner;
                    try {
                        // Здесь нужно реализовать чтение составных данных из скрипта
                        System.out.println("Команды с вводом данных в скриптах требуют дополнительной реализации.");
                    } finally {
                        // Восстанавливаем оригинальный Scanner
                        Field field = App.class.getDeclaredField("scanner");
                        field.setAccessible(true);
                        field.set(this, oldScanner);
                    }
                } else {
                    executeCommand(command, argument);
                }
            }
            System.out.println("Скрипт успешно выполнен.");
        } catch (FileNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private void addIfMax() {
        StudyGroup newGroup = readStudyGroup();
        if (newGroup == null) return;

        Optional<StudyGroup> max = collection.stream().max(StudyGroup::compareTo);

        if (max.isEmpty() || newGroup.compareTo(max.get()) > 0) {
            collection.add(newGroup);
            System.out.println("Элемент успешно добавлен с id: " + newGroup.getId());
        } else {
            System.out.println("Элемент не добавлен, так как не превышает максимальный.");
        }
    }

    private void removeGreater() {
        StudyGroup reference = readStudyGroup();
        if (reference == null) return;

        int initialSize = collection.size();
        collection.removeIf(g -> g.compareTo(reference) > 0);

        System.out.println("Удалено элементов: " + (initialSize - collection.size()));
    }

    private void showHistory() {
        System.out.println("Последние " + COMMAND_HISTORY_SIZE + " команд:");
        commandHistory.forEach(System.out::println);
    }

    private void removeAnyByStudentsCount(String argument) {
        try {
            long studentsCount = Long.parseLong(argument.trim());

            Optional<StudyGroup> toRemove = collection.stream()
                    .filter(g -> g.getStudentsCount() == studentsCount)
                    .findFirst();

            if (toRemove.isPresent()) {
                collection.remove(toRemove.get());
                System.out.println("Элемент с studentsCount = " + studentsCount + " удален.");
            } else {
                System.out.println("Элемент с studentsCount = " + studentsCount + " не найден.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: studentsCount должен быть числом.");
        }
    }

    private void minBySemesterEnum() {
        Optional<StudyGroup> min = collection.stream()
                .min(Comparator.comparing(StudyGroup::getSemesterEnum));

        if (min.isPresent()) {
            System.out.println("Элемент с минимальным semesterEnum:");
            System.out.println(min.get());
        } else {
            System.out.println("Коллекция пуста.");
        }
    }

    private void countGreaterThanExpelledStudents(String argument) {
        try {
            int expelledStudents = Integer.parseInt(argument.trim());

            long count = collection.stream()
                    .filter(g -> g.getExpelledStudents() > expelledStudents)
                    .count();

            System.out.println("Количество элементов с expelledStudents > " + expelledStudents + ": " + count);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: expelledStudents должен быть числом.");
        }
    }

    private StudyGroup readStudyGroup() {
        try {
            StudyGroup group = new StudyGroup();

            // id генерируется автоматически
            group.setId(generateId());

            // name
            while (true) {
                System.out.print("Введите название группы (не может быть пустым): ");
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    group.setName(name);
                    break;
                }
                System.out.println("Ошибка: название не может быть пустым.");
            }

            // coordinates
            group.setCoordinates(readCoordinates());

            // creationDate генерируется автоматически
            group.setCreationDate(LocalDateTime.now());

            // studentsCount
            while (true) {
                System.out.print("Введите количество студентов (> 0): ");
                try {
                    long count = Long.parseLong(scanner.nextLine().trim());
                    if (count > 0) {
                        group.setStudentsCount(count);
                        break;
                    }
                    System.out.println("Ошибка: количество студентов должно быть > 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число.");
                }
            }

            // expelledStudents
            while (true) {
                System.out.print("Введите количество отчисленных студентов (> 0): ");
                try {
                    int count = Integer.parseInt(scanner.nextLine().trim());
                    if (count > 0) {
                        group.setExpelledStudents(count);
                        break;
                    }
                    System.out.println("Ошибка: количество отчисленных должно быть > 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число.");
                }
            }

            // formOfEducation
            group.setFormOfEducation(readFormOfEducation());

            // semesterEnum
            group.setSemesterEnum(readSemester());

            // groupAdmin (может быть null)
            System.out.print("Хотите добавить информацию об администраторе? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y") || answer.equals("yes")) {
                group.setGroupAdmin(readPerson());
            } else {
                group.setGroupAdmin(null);
            }

            return group;
        } catch (Exception e) {
            System.out.println("Ошибка при создании объекта: " + e.getMessage());
            return null;
        }
    }

    private Long generateId() {
        long id = 0;
        long finalId = id;
        do {
            id = System.currentTimeMillis() + new Random().nextInt(1000);
        } while (collection.stream().anyMatch(g -> g.getId().equals(finalId)));
        return id;
    }

    private Coordinates readCoordinates() {
        Coordinates coordinates = new Coordinates();

        // x
        while (true) {
            System.out.print("Введите координату x (<= 741, не null): ");
            try {
                Float x = Float.parseFloat(scanner.nextLine().trim());
                if (x <= 741) {
                    coordinates.setX(x);
                    break;
                }
                System.out.println("Ошибка: x должно быть <= 741.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }

        // y
        while (true) {
            System.out.print("Введите координату y (> -938): ");
            try {
                long y = Long.parseLong(scanner.nextLine().trim());
                if (y > -938) {
                    coordinates.setY(y);
                    break;
                }
                System.out.println("Ошибка: y должно быть > -938.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }

        return coordinates;
    }

    private Person readPerson() {
        Person person = new Person();

        // name
        while (true) {
            System.out.print("  Введите имя администратора (не может быть пустым): ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                person.setName(name);
                break;
            }
            System.out.println("  Ошибка: имя не может быть пустым.");
        }

        // birthday (может быть null)
        System.out.print("  Введите дату рождения (yyyy-MM-dd) или оставьте пустым: ");
        String dateStr = scanner.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try {
                person.setBirthday(Date.from(
                        LocalDateTime.parse(dateStr + "T00:00:00")
                                .atZone(ZoneId.systemDefault())
                                .toInstant()));
            } catch (DateTimeParseException e) {
                System.out.println("  Неверный формат даты, поле будет null.");
                person.setBirthday(null);
            }
        } else {
            person.setBirthday(null);
        }

        // weight
        while (true) {
            System.out.print("  Введите вес (> 0, не null): ");
            try {
                Integer weight = Integer.parseInt(scanner.nextLine().trim());
                if (weight > 0) {
                    person.setWeight(weight);
                    break;
                }
                System.out.println("  Ошибка: вес должен быть > 0.");
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: введите число.");
            }
        }

        // passportID
        while (true) {
            System.out.print("  Введите номер паспорта (<= 20 символов, не null): ");
            String passport = scanner.nextLine().trim();
            if (!passport.isEmpty() && passport.length() <= 20) {
                person.setPassportID(passport);
                break;
            }
            System.out.println("  Ошибка: паспорт должен быть от 1 до 20 символов.");
        }

        // location (может быть null)
        System.out.print("  Хотите добавить информацию о местоположении? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equals("y") || answer.equals("yes")) {
            person.setLocation(readLocation());
        } else {
            person.setLocation(null);
        }

        return person;
    }

    private Location readLocation() {
        Location location = new Location();

        // x
        System.out.print("    Введите x (может быть любым числом): ");
        try {
            location.setX(Double.parseDouble(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("    Неверный формат, установлено значение 0.");
            location.setX(0);
        }

        // y
        while (true) {
            System.out.print("    Введите y (не null): ");
            try {
                Double y = Double.parseDouble(scanner.nextLine().trim());
                location.setY(y);
                break;
            } catch (NumberFormatException e) {
                System.out.println("    Ошибка: введите число.");
            }
        }

        // z
        while (true) {
            System.out.print("    Введите z (не null): ");
            try {
                Float z = Float.parseFloat(scanner.nextLine().trim());
                location.setZ(z);
                break;
            } catch (NumberFormatException e) {
                System.out.println("    Ошибка: введите число.");
            }
        }

        return location;
    }

    private FormOfEducation readFormOfEducation() {
        System.out.println("Доступные формы обучения:");
        for (FormOfEducation f : FormOfEducation.values()) {
            System.out.println("  " + f);
        }

        while (true) {
            System.out.print("Выберите форму обучения: ");
            try {
                return FormOfEducation.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неверное значение. Попробуйте снова.");
            }
        }
    }

    private Semester readSemester() {
        System.out.println("Доступные семестры:");
        for (Semester s : Semester.values()) {
            System.out.println("  " + s);
        }

        while (true) {
            System.out.print("Выберите семестр: ");
            try {
                return Semester.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: неверное значение. Попробуйте снова.");
            }
        }
    }

//    //Загрузка коллекции
//    private void loadCollectionFromFile() {
//        File file = new File(filename);
//
//        if (!file.exists()) {
//            System.out.println("Файл не найден. Будет создана пустая коллекция.");
//            return;
//        }
//
//        if (!file.canRead()) {
//            System.out.println("Нет прав на чтение файла. Будет создана пустая коллекция.");
//            return;
//        }
//
//        try (Scanner fileScanner = new Scanner(file)) {
//            if (!fileScanner.hasNextLine()) {
//                System.out.println("Файл пуст.");
//                return;
//            }
//
//            // Пропускаем заголовок
//            String header = fileScanner.nextLine();
//
//            int lineNumber = 1;
//            while (fileScanner.hasNextLine()) {
//                lineNumber++;
//                String line = fileScanner.nextLine().trim();
//
//                if (line.isEmpty()) continue;
//
//                try {
//                    StudyGroup group = new StudyGroup();
//                    if (group != null) {
//                        collection.add(group);
//                    }
//                } catch (Exception e) {
//                    System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
//                }
//            }
//
//            System.out.println("Загружено элементов: " + collection.size());
//        } catch (FileNotFoundException e) {
//            System.out.println("Ошибка при чтении файла: " + e.getMessage());
//        }
//    }


    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Ошибка: необходимо указать имя файла как аргумент командной строки.");
            System.exit(1);
        }

        String filename = args[0];
        ReadCSV file = new ReadCSV(filename);
        App manager = new App(filename);
        manager.run();
    }
}