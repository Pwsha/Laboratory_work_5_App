package org.example.command;

import org.example.program.*;
import java.time.LocalDateTime;
import java.util.*;


public class CommandHelper {

    public static Long generateId(HashSet<StudyGroup> collection) {
        Random random = new Random();
        long id;
        do {
            id = System.currentTimeMillis() + random.nextInt(1000);
            final long currentId = id;
            if (collection.stream().noneMatch(g -> g.getId().equals(currentId))) {
                return currentId;
            }
        } while (true);
    }

    public static StudyGroup readStudyGroup(Scanner scanner, HashSet<StudyGroup> collection) {
        StudyGroup group = new StudyGroup();
        group.setId(generateId(collection));
        group.setCreationDate(LocalDateTime.now());

        System.out.println("Введите данные группы:");

        // name
        while (true) {
            System.out.print("  Название группы: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                group.setName(name);
                break;
            }
            System.out.println("  Ошибка: название не может быть пустым");
        }

        // coordinates
        group.setCoordinates(readCoordinates(scanner));

        // studentsCount
        while (true) {
            System.out.print("  Количество студентов (>0): ");
            try {
                long count = Long.parseLong(scanner.nextLine().trim());
                if (count > 0) {
                    group.setStudentsCount(count);
                    break;
                }
                System.out.println("  Ошибка: должно быть >0");
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: введите число");
            }
        }

        // expelledStudents
        while (true) {
            System.out.print("  Количество отчисленных (>0): ");
            try {
                int count = Integer.parseInt(scanner.nextLine().trim());
                if (count > 0) {
                    group.setExpelledStudents(count);
                    break;
                }
                System.out.println("  Ошибка: должно быть >0");
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: введите число");
            }
        }

        // formOfEducation
        group.setFormOfEducation(readEnum(scanner, FormOfEducation.class, "форму обучения"));

        // semesterEnum
        group.setSemesterEnum(readEnum(scanner, Semester.class, "семестр"));

        // groupAdmin
        System.out.print("  Добавить администратора? (y/n): ");
        if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            group.setGroupAdmin(readPerson(scanner));
        }

        return group;
    }

    /**
     * Читает Coordinates.
     */
    private static Coordinates readCoordinates(Scanner scanner) {
        Coordinates coords = new Coordinates();

        while (true) {
            System.out.print("  Координата x (<=741): ");
            try {
                Float x = Float.parseFloat(scanner.nextLine().trim());
                if (x <= 741) {
                    coords.setX(x);
                    break;
                }
                System.out.println("  Ошибка: x <= 741");
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: введите число");
            }
        }

        while (true) {
            System.out.print("  Координата y (>-938): ");
            try {
                long y = Long.parseLong(scanner.nextLine().trim());
                if (y > -938) {
                    coords.setY(y);
                    break;
                }
                System.out.println("  Ошибка: y > -938");
            } catch (NumberFormatException e) {
                System.out.println("  Ошибка: введите число");
            }
        }

        return coords;
    }

    /**
     * Читает Person.
     */
    private static Person readPerson(Scanner scanner) {
        Person person = new Person();

        System.out.println("  Данные администратора:");

        while (true) {
            System.out.print("    Имя: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                person.setName(name);
                break;
            }
            System.out.println("    Ошибка: имя не может быть пустым");
        }

        System.out.print("    Дата рождения (yyyy-MM-dd) или пусто: ");
        String dateStr = scanner.nextLine().trim();
        if (!dateStr.isEmpty()) {
            try {
                person.setBirthday(java.sql.Date.valueOf(dateStr));
            } catch (Exception e) {
                System.out.println("    Неверный формат, поле не будет установлено");
            }
        }

        while (true) {
            System.out.print("    Вес (>0): ");
            try {
                Integer weight = Integer.parseInt(scanner.nextLine().trim());
                if (weight > 0) {
                    person.setWeight(weight);
                    break;
                }
                System.out.println("    Ошибка: вес >0");
            } catch (NumberFormatException e) {
                System.out.println("    Ошибка: введите число");
            }
        }

        while (true) {
            System.out.print("    Номер паспорта (<=20 символов): ");
            String passport = scanner.nextLine().trim();
            if (!passport.isEmpty() && passport.length() <= 20) {
                person.setPassportID(passport);
                break;
            }
            System.out.println("    Ошибка: от 1 до 20 символов");
        }

        System.out.print("    Добавить местоположение? (y/n): ");
        if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            person.setLocation(readLocation(scanner));
        }

        return person;
    }

    /**
     * Читает Location.
     */
    private static Location readLocation(Scanner scanner) {
        Location loc = new Location();

        System.out.println("    Местоположение:");

        System.out.print("      x: ");
        try {
            loc.setX(Double.parseDouble(scanner.nextLine().trim()));
        } catch (NumberFormatException e) {
            System.out.println("      Неверный формат, установлено 0");
            loc.setX(0);
        }

        while (true) {
            System.out.print("      y (не null): ");
            try {
                loc.setY(Double.parseDouble(scanner.nextLine().trim()));
                break;
            } catch (NumberFormatException e) {
                System.out.println("      Ошибка: введите число");
            }
        }

        while (true) {
            System.out.print("      z (не null): ");
            try {
                loc.setZ(Float.parseFloat(scanner.nextLine().trim()));
                break;
            } catch (NumberFormatException e) {
                System.out.println("      Ошибка: введите число");
            }
        }

        return loc;
    }

    public static <T extends Enum<T>> T readEnum(Scanner scanner, Class<T> enumClass, String description) {
        T[] constants = enumClass.getEnumConstants();
        System.out.println("  Доступные варианты " + description + ":");
        for (T c : constants) {
            System.out.println("    " + c.name());
        }

        while (true) {
            System.out.print("  Выберите " + description + ": ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.println("  Ошибка: неверное значение");
            }
        }
    }
}