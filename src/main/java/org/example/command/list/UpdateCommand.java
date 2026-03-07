package org.example.command.list;

import org.example.command.Command;
import org.example.command.CommandHelper;
import org.example.program.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Класс команды обновления элемента по id
 * @author Pwsha
 * @version v1.3
 */
public class UpdateCommand implements Command {

    @Override
    public String execute(String[] args, HashSet<StudyGroup> collection, Scanner scanner) {
        if (args.length == 0) {
            return "Ошибка: укажите id";
        }

        try {
            Long id = Long.parseLong(args[0]);

            // Ищем существующий элемент
            StudyGroup existing = collection.stream()
                    .filter(g -> g.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (existing == null) {
                return "Элемент с id " + id + " не найден";
            }

            System.out.println("Редактирование элемента с id: " + id);
            System.out.println("Текущие данные: " + existing);
            System.out.println("Введите новые данные (оставьте пустым чтобы оставить текущее значение):");

            // Получаем новые данные от пользователя
            StudyGroup updatedGroup = readUpdatedGroup(scanner, collection, existing);

            // Удаляем старый и добавляем обновленный
            collection.remove(existing);
            collection.add(updatedGroup);

            return "Элемент с id " + id + " успешно обновлен";

        } catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом";
        }
    }

    private StudyGroup readUpdatedGroup(Scanner scanner, HashSet<StudyGroup> collection, StudyGroup existing) {
        System.out.println("Введите новые данные группы (Enter - оставить текущее значение):");

        // name
        String name = existing.getName();
        System.out.print("  Название группы [" + name + "]: ");
        String nameInput = scanner.nextLine().trim();
        if (!nameInput.isEmpty()) {
            name = nameInput;
        }

        // coordinates
        System.out.println("  Координаты (текущие: x=" + existing.getCoordinates().getX() +
                ", y=" + existing.getCoordinates().getY() + "):");

        Float x = existing.getCoordinates().getX();
        System.out.print("    x [" + x + "]: ");
        String xInput = scanner.nextLine().trim();
        if (!xInput.isEmpty()) {
            try {
                x = Float.parseFloat(xInput);
            } catch (NumberFormatException e) {
                System.out.println("    Неверный формат, оставлено текущее значение");
            }
        }

        Long y = existing.getCoordinates().getY();
        System.out.print("    y [" + y + "]: ");
        String yInput = scanner.nextLine().trim();
        if (!yInput.isEmpty()) {
            try {
                y = Long.parseLong(yInput);
            } catch (NumberFormatException e) {
                System.out.println("    Неверный формат, оставлено текущее значение");
            }
        }

        // Создаем координаты через Builder
        Coordinates coordinates = new Coordinates.Builder()
                .x(x)
                .y(y)
                .build();

        // studentsCount
        long studentsCount = existing.getStudentsCount();
        System.out.print("  Количество студентов [" + studentsCount + "]: ");
        String studentsInput = scanner.nextLine().trim();
        if (!studentsInput.isEmpty()) {
            try {
                studentsCount = Long.parseLong(studentsInput);
            } catch (NumberFormatException e) {
                System.out.println("  Неверный формат, оставлено текущее значение");
            }
        }

        // expelledStudents
        int expelledStudents = existing.getExpelledStudents();
        System.out.print("  Количество отчисленных [" + expelledStudents + "]: ");
        String expelledInput = scanner.nextLine().trim();
        if (!expelledInput.isEmpty()) {
            try {
                expelledStudents = Integer.parseInt(expelledInput);
            } catch (NumberFormatException e) {
                System.out.println("  Неверный формат, оставлено текущее значение");
            }
        }

        // formOfEducation
        FormOfEducation formOfEducation = existing.getFormOfEducation();
        System.out.println("  Форма обучения (текущая: " + formOfEducation + ")");
        System.out.print("  Новая форма (Enter - оставить): ");
        String formInput = scanner.nextLine().trim().toUpperCase();
        if (!formInput.isEmpty()) {
            try {
                formOfEducation = FormOfEducation.valueOf(formInput);
            } catch (IllegalArgumentException e) {
                System.out.println("  Неверное значение, оставлено текущее");
            }
        }

        // semesterEnum
        Semester semester = existing.getSemesterEnum();
        System.out.println("  Семестр (текущий: " + semester + ")");
        System.out.print("  Новый семестр (Enter - оставить): ");
        String semesterInput = scanner.nextLine().trim().toUpperCase();
        if (!semesterInput.isEmpty()) {
            try {
                semester = Semester.valueOf(semesterInput);
            } catch (IllegalArgumentException e) {
                System.out.println("  Неверное значение, оставлен текущий");
            }
        }

        // groupAdmin
        Person admin = existing.getGroupAdmin();
        System.out.print("  Изменить администратора? (y/n): ");
        String changeAdmin = scanner.nextLine().trim().toLowerCase();
        if (changeAdmin.startsWith("y")) {
            if (admin == null) {
                System.out.println("  Добавление нового администратора:");
                admin = readUpdatedPerson(scanner, null);
            } else {
                System.out.println("  Редактирование администратора:");
                admin = readUpdatedPerson(scanner, admin);
            }
        }

        // Создаем обновленную группу через Builder
        return new StudyGroup.Builder()
                .id(existing.getId()) // ID остается тем же
                .name(name)
                .coordinates(coordinates)
                .creationDate(existing.getCreationDate()) // Дата создания не меняется
                .studentsCount(studentsCount)
                .expelledStudents(expelledStudents)
                .formOfEducation(formOfEducation)
                .semesterEnum(semester)
                .groupAdmin(admin)
                .build();
    }

    private Person readUpdatedPerson(Scanner scanner, Person existing) {
        String name;
        Integer weight;
        String passportID;
        Date birthday;
        Location location;

        if (existing == null) {
            // Создание нового администратора
            System.out.println("    Данные нового администратора:");

            // name
            while (true) {
                System.out.print("      Имя: ");
                name = scanner.nextLine().trim();
                if (!name.isEmpty()) break;
                System.out.println("      Ошибка: имя не может быть пустым");
            }

            // birthday
            System.out.print("      Дата рождения (yyyy-MM-dd) или пусто: ");
            String dateStr = scanner.nextLine().trim();
            birthday = dateStr.isEmpty() ? null : java.sql.Date.valueOf(dateStr);

            // weight
            while (true) {
                System.out.print("      Вес (>0): ");
                try {
                    weight = Integer.parseInt(scanner.nextLine().trim());
                    if (weight > 0) break;
                    System.out.println("      Ошибка: вес >0");
                } catch (NumberFormatException e) {
                    System.out.println("      Ошибка: введите число");
                }
            }

            // passportID
            while (true) {
                System.out.print("      Номер паспорта (<=20 символов): ");
                passportID = scanner.nextLine().trim();
                if (!passportID.isEmpty() && passportID.length() <= 20) break;
                System.out.println("      Ошибка: от 1 до 20 символов");
            }

            // location
            System.out.print("      Добавить местоположение? (y/n): ");
            location = scanner.nextLine().trim().toLowerCase().startsWith("y") ?
                    readUpdatedLocation(scanner, null) : null;

        } else {
            // Редактирование существующего администратора
            System.out.println("    Текущие данные: " + existing);

            // name
            System.out.print("      Имя [" + existing.getName() + "]: ");
            String nameInput = scanner.nextLine().trim();
            name = nameInput.isEmpty() ? existing.getName() : nameInput;

            // birthday
            String currentBirthday = existing.getBirthday() != null ?
                    existing.getBirthday().toString() : "не указана";
            System.out.print("      Дата рождения (yyyy-MM-dd) [" + currentBirthday + "]: ");
            String dateInput = scanner.nextLine().trim();
            birthday = dateInput.isEmpty() ? existing.getBirthday() :
                    java.sql.Date.valueOf(dateInput);

            // weight
            System.out.print("      Вес [" + existing.getWeight() + "]: ");
            String weightInput = scanner.nextLine().trim();
            weight = weightInput.isEmpty() ? existing.getWeight() :
                    Integer.parseInt(weightInput);

            // passportID
            System.out.print("      Номер паспорта [" + existing.getPassportID() + "]: ");
            String passportInput = scanner.nextLine().trim();
            passportID = passportInput.isEmpty() ? existing.getPassportID() : passportInput;

            // location
            System.out.print("      Изменить местоположение? (y/n): ");
            location = scanner.nextLine().trim().toLowerCase().startsWith("y") ?
                    readUpdatedLocation(scanner, existing.getLocation()) :
                    existing.getLocation();
        }

        // Создаем Person через Builder
        Person.Builder builder = new Person.Builder()
                .name(name)
                .weight(weight)
                .passportID(passportID)
                .birthday(birthday);

        if (location != null) {
            builder.location(location);
        }

        return builder.build();
    }

    private Location readUpdatedLocation(Scanner scanner, Location existing) {
        double x;
        Double y;
        Float z;

        if (existing == null) {
            // Создание нового местоположения
            System.out.println("      Местоположение:");

            System.out.print("        x: ");
            try {
                x = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("        Неверный формат, установлено 0");
                x = 0;
            }

            while (true) {
                System.out.print("        y (не null): ");
                try {
                    y = Double.parseDouble(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("        Ошибка: введите число");
                }
            }

            while (true) {
                System.out.print("        z (не null): ");
                try {
                    z = Float.parseFloat(scanner.nextLine().trim());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("        Ошибка: введите число");
                }
            }

        } else {
            // Редактирование существующего местоположения
            System.out.println("      Текущее местоположение: " + existing);

            System.out.print("        x [" + existing.getX() + "]: ");
            String xInput = scanner.nextLine().trim();
            x = xInput.isEmpty() ? existing.getX() : Double.parseDouble(xInput);

            System.out.print("        y [" + existing.getY() + "]: ");
            String yInput = scanner.nextLine().trim();
            y = yInput.isEmpty() ? existing.getY() : Double.parseDouble(yInput);

            System.out.print("        z [" + existing.getZ() + "]: ");
            String zInput = scanner.nextLine().trim();
            z = zInput.isEmpty() ? existing.getZ() : Float.parseFloat(zInput);
        }

        return new Location.Builder()
                .x(x)
                .y(y)
                .z(z)
                .build();
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить элемент по id";
    }

    @Override
    public String getSyntax() {
        return "update id";
}
}