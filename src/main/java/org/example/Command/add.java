//package org.example.Command;
//
//import org.example.program.*;
//
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeParseException;
//import java.util.Date;
//import java.util.Random;
//import java.util.Scanner;
//
//public class add {
//
//    private final Scanner scanner;
//
//    public add() {
//        this.scanner = new Scanner(System.in);
//    }
//    private StudyGroup readStudyGroup() {
//        try {
//            StudyGroup group = new StudyGroup();
//
//            // id генерируется автоматически
//            group.setId(generateId());
//
//            // name
//            while (true) {
//                System.out.print("Введите название группы (не может быть пустым): ");
//                String name = scanner.nextLine().trim();
//                if (!name.isEmpty()) {
//                    group.setName(name);
//                    break;
//                }
//                System.out.println("Ошибка: название не может быть пустым.");
//            }
//
//            // coordinates
//            group.setCoordinates(readCoordinates());
//
//            // creationDate генерируется автоматически
//            group.setCreationDate(LocalDateTime.now());
//
//            // studentsCount
//            while (true) {
//                System.out.print("Введите количество студентов (> 0): ");
//                try {
//                    long count = Long.parseLong(scanner.nextLine().trim());
//                    if (count > 0) {
//                        group.setStudentsCount(count);
//                        break;
//                    }
//                    System.out.println("Ошибка: количество студентов должно быть > 0.");
//                } catch (NumberFormatException e) {
//                    System.out.println("Ошибка: введите число.");
//                }
//            }
//
//            // expelledStudents
//            while (true) {
//                System.out.print("Введите количество отчисленных студентов (> 0): ");
//                try {
//                    int count = Integer.parseInt(scanner.nextLine().trim());
//                    if (count > 0) {
//                        group.setExpelledStudents(count);
//                        break;
//                    }
//                    System.out.println("Ошибка: количество отчисленных должно быть > 0.");
//                } catch (NumberFormatException e) {
//                    System.out.println("Ошибка: введите число.");
//                }
//            }
//
//            // formOfEducation
//            group.setFormOfEducation(readFormOfEducation());
//
//            // semesterEnum
//            group.setSemesterEnum(readSemester());
//
//            // groupAdmin (может быть null)
//            System.out.print("Хотите добавить информацию об администраторе? (y/n): ");
//            String answer = scanner.nextLine().trim().toLowerCase();
//            if (answer.equals("y") || answer.equals("yes")) {
//                group.setGroupAdmin(readPerson());
//            } else {
//                group.setGroupAdmin(null);
//            }
//
//            return group;
//        } catch (Exception e) {
//            System.out.println("Ошибка при создании объекта: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private Long generateId() {
//        long id = 0;
//        long finalId = id;
//        do {
//            id = System.currentTimeMillis() + new Random().nextInt(1000);
//        } while (collection.stream().anyMatch(g -> g.getId().equals(finalId)));
//        return id;
//    }
//
//    private Coordinates readCoordinates() {
//        Coordinates coordinates = new Coordinates();
//
//        // x
//        while (true) {
//            System.out.print("Введите координату x (<= 741, не null): ");
//            try {
//                Float x = Float.parseFloat(scanner.nextLine().trim());
//                if (x <= 741) {
//                    coordinates.setX(x);
//                    break;
//                }
//                System.out.println("Ошибка: x должно быть <= 741.");
//            } catch (NumberFormatException e) {
//                System.out.println("Ошибка: введите число.");
//            }
//        }
//
//        // y
//        while (true) {
//            System.out.print("Введите координату y (> -938): ");
//            try {
//                long y = Long.parseLong(scanner.nextLine().trim());
//                if (y > -938) {
//                    coordinates.setY(y);
//                    break;
//                }
//                System.out.println("Ошибка: y должно быть > -938.");
//            } catch (NumberFormatException e) {
//                System.out.println("Ошибка: введите число.");
//            }
//        }
//
//        return coordinates;
//    }
//
//    private Person readPerson() {
//        Person person = new Person();
//
//        // name
//        while (true) {
//            System.out.print("  Введите имя администратора (не может быть пустым): ");
//            String name = scanner.nextLine().trim();
//            if (!name.isEmpty()) {
//                person.setName(name);
//                break;
//            }
//            System.out.println("  Ошибка: имя не может быть пустым.");
//        }
//
//        // birthday (может быть null)
//        System.out.print("  Введите дату рождения (yyyy-MM-dd) или оставьте пустым: ");
//        String dateStr = scanner.nextLine().trim();
//        if (!dateStr.isEmpty()) {
//            try {
//                person.setBirthday(Date.from(
//                        LocalDateTime.parse(dateStr + "T00:00:00")
//                                .atZone(ZoneId.systemDefault())
//                                .toInstant()));
//            } catch (DateTimeParseException e) {
//                System.out.println("  Неверный формат даты, поле будет null.");
//                person.setBirthday(null);
//            }
//        } else {
//            person.setBirthday(null);
//        }
//
//        // weight
//        while (true) {
//            System.out.print("  Введите вес (> 0, не null): ");
//            try {
//                Integer weight = Integer.parseInt(scanner.nextLine().trim());
//                if (weight > 0) {
//                    person.setWeight(weight);
//                    break;
//                }
//                System.out.println("  Ошибка: вес должен быть > 0.");
//            } catch (NumberFormatException e) {
//                System.out.println("  Ошибка: введите число.");
//            }
//        }
//
//        // passportID
//        while (true) {
//            System.out.print("  Введите номер паспорта (<= 20 символов, не null): ");
//            String passport = scanner.nextLine().trim();
//            if (!passport.isEmpty() && passport.length() <= 20) {
//                person.setPassportID(passport);
//                break;
//            }
//            System.out.println("  Ошибка: паспорт должен быть от 1 до 20 символов.");
//        }
//
//        // location (может быть null)
//        System.out.print("  Хотите добавить информацию о местоположении? (y/n): ");
//        String answer = scanner.nextLine().trim().toLowerCase();
//        if (answer.equals("y") || answer.equals("yes")) {
//            person.setLocation(readLocation());
//        } else {
//            person.setLocation(null);
//        }
//
//        return person;
//    }
//
//    private Location readLocation() {
//        Location location = new Location();
//
//        // x
//        System.out.print("    Введите x (может быть любым числом): ");
//        try {
//            location.setX(Double.parseDouble(scanner.nextLine().trim()));
//        } catch (NumberFormatException e) {
//            System.out.println("    Неверный формат, установлено значение 0.");
//            location.setX(0);
//        }
//
//        // y
//        while (true) {
//            System.out.print("    Введите y (не null): ");
//            try {
//                Double y = Double.parseDouble(scanner.nextLine().trim());
//                location.setY(y);
//                break;
//            } catch (NumberFormatException e) {
//                System.out.println("    Ошибка: введите число.");
//            }
//        }
//
//        // z
//        while (true) {
//            System.out.print("    Введите z (не null): ");
//            try {
//                Float z = Float.parseFloat(scanner.nextLine().trim());
//                location.setZ(z);
//                break;
//            } catch (NumberFormatException e) {
//                System.out.println("    Ошибка: введите число.");
//            }
//        }
//
//        return location;
//    }
//
//    private FormOfEducation readFormOfEducation() {
//        System.out.println("Доступные формы обучения:");
//        for (FormOfEducation f : FormOfEducation.values()) {
//            System.out.println("  " + f);
//        }
//
//        while (true) {
//            System.out.print("Выберите форму обучения: ");
//            try {
//                return FormOfEducation.valueOf(scanner.nextLine().trim().toUpperCase());
//            } catch (IllegalArgumentException e) {
//                System.out.println("Ошибка: неверное значение. Попробуйте снова.");
//            }
//        }
//    }
//
//    private Semester readSemester() {
//        System.out.println("Доступные семестры:");
//        for (Semester s : Semester.values()) {
//            System.out.println("  " + s);
//        }
//
//        while (true) {
//            System.out.print("Выберите семестр: ");
//            try {
//                return Semester.valueOf(scanner.nextLine().trim().toUpperCase());
//            } catch (IllegalArgumentException e) {
//                System.out.println("Ошибка: неверное значение. Попробуйте снова.");
//            }
//        }
//    }
//}