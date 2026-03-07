package org.example.data;

import org.example.program.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс парсера csv файла
 * @author Pwsha
 * @version v1.3
 */
public class StudyGroupCsvParser {

    /** Поле файла */
    private final String filename;
    /** Поле заголовка */
    private static final String HEADER = "id,name,coordinates_x,coordinates_y,creationDate,studentsCount,expelledStudents,formOfEducation,semesterEnum,groupAdmin_name,groupAdmin_birthday,groupAdmin_weight,groupAdmin_passportID,groupAdmin_location_x,groupAdmin_location_y,groupAdmin_location_z";
    /** Поле даты */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Конструктор со всеми значениями
     * @param filename - файл
     */
    public StudyGroupCsvParser(String filename) {
        this.filename = filename;
    }

    /**
     * Метод загрузки файла и его проверки
     * @return collection
     * @throws IOException
     */
    public HashSet<StudyGroup> loadFromFile() throws IOException {
        HashSet<StudyGroup> collection = new HashSet<>();
        File file = new File(filename);

        if (!file.exists()) {
            return collection;
        }

        if (!file.canRead()) {
            throw new IOException("Нет прав на чтение файла: " + filename);
        }

        try (Scanner scanner = new Scanner(file)) {
            // Пропускаем заголовок, если он есть
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                try {
                    StudyGroup group = parseLine(line);
                    if (group != null && group.isValid()) {
                        collection.add(group);
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        return collection;
    }

    /**
     * Метод для команды сохранения данных в файл
     * @param collection
     * @throws IOException
     */
    public void saveToFile(HashSet<StudyGroup> collection) throws IOException {
        // Проверяем, можно ли писать в файл
        File file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            throw new IOException("Нет прав на запись в файл: " + filename);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(HEADER);

            for (StudyGroup group : collection) {
                writer.println(formatLine(group));
            }
        }
    }

    public boolean isFileAccessible() {
        File file = new File(filename);
        return file.exists() && file.canRead();
    }

    /**
     * Метод создания пустого файла
     * @throws IOException
     */
    public void createEmptyFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(HEADER);
        }
    }

    /**
     * Функция получения значения поля {@link StudyGroupCsvParser#filename}
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    private StudyGroup parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 9) {
            throw new IllegalArgumentException("Недостаточно полей. Ожидается минимум 9, получено " + parts.length);
        }

        try {
            int idx = 0;

            // id
            Long id = parseLong(parts[idx++]);

            // name
            String name = parts[idx++];
            if (name.isEmpty()) {
                throw new IllegalArgumentException("name не может быть пустым");
            }

            // coordinates
            Float x = parseFloat(parts[idx++]);
            Long y = parseLong(parts[idx++]);
            Coordinates coordinates = new Coordinates.Builder()
                    .x(x)
                    .y(y)
                    .build();

            // creationDate
            String dateStr = parts[idx++];
            LocalDateTime creationDate = dateStr.isEmpty() ? LocalDateTime.now() :
                    LocalDateTime.parse(dateStr, DATE_FORMATTER);

            // studentsCount
            long studentsCount = parseLong(parts[idx++]);

            // expelledStudents
            int expelledStudents = parseInt(parts[idx++]);

            // formOfEducation
            String formStr = parts[idx++];
            FormOfEducation formOfEducation = FormOfEducation.valueOf(formStr);

            // semesterEnum
            String semesterStr = parts[idx++];
            Semester semester = Semester.valueOf(semesterStr);

            // groupAdmin (если есть поля)
            Person groupAdmin = null;
            if (parts.length > idx && !parts[idx].isEmpty()) {
                groupAdmin = parsePerson(parts, idx);
            }

            // Создаем StudyGroup через Builder
            return new StudyGroup.Builder()
                    .id(id)
                    .name(name)
                    .coordinates(coordinates)
                    .creationDate(creationDate)
                    .studentsCount(studentsCount)
                    .expelledStudents(expelledStudents)
                    .formOfEducation(formOfEducation)
                    .semesterEnum(semester)
                    .groupAdmin(groupAdmin)
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка парсинга: " + e.getMessage(), e);
        }
    }

    private Person parsePerson(String[] parts, int startIdx) {
        int idx = startIdx;

        try {
            // name
            String name = parts[idx++];
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Имя администратора не может быть пустым");
            }

            // birthday (может быть пустым)
            Date birthday = null;
            if (idx < parts.length && !parts[idx].isEmpty()) {
                long birthdayMillis = parseLong(parts[idx]);
                birthday = new Date(birthdayMillis);
            }
            idx++;

            // weight
            Integer weight = null;
            if (idx < parts.length && !parts[idx].isEmpty()) {
                weight = parseInt(parts[idx]);
            }
            idx++;

            // passportID
            String passportID = null;
            if (idx < parts.length && !parts[idx].isEmpty()) {
                passportID = parts[idx];
            }
            idx++;

            // location (если есть поля)
            Location location = null;
            if (parts.length > idx + 2) {
                Double locY = null;
                Float locZ = null;

                double locX = 0;
                if (!parts[idx].isEmpty()) {
                    locX = parseDouble(parts[idx]);
                }
                idx++;

                if (!parts[idx].isEmpty()) {
                    locY = parseDouble(parts[idx]);
                }
                idx++;

                if (!parts[idx].isEmpty()) {
                    locZ = parseFloat(parts[idx]);
                }

                // Создаем location только если есть обязательные поля
                if (locY != null && locZ != null) {
                    location = new Location.Builder()
                            .x(locX)
                            .y(locY)
                            .z(locZ)
                            .build();
                }
            }

            // Создаем Person через Builder
            return new Person.Builder()
                    .name(name)
                    .birthday(birthday)
                    .weight(weight)
                    .passportID(passportID)
                    .location(location)
                    .build();

        } catch (Exception e) {
            System.err.println("Ошибка парсинга Person: " + e.getMessage());
            return null;
        }
    }

    private String formatLine(StudyGroup group) {
        StringBuilder sb = new StringBuilder();

        // Основные поля
        sb.append(group.getId()).append(",");
        sb.append(escapeCsv(group.getName())).append(",");
        sb.append(group.getCoordinates().getX()).append(",");
        sb.append(group.getCoordinates().getY()).append(",");
        sb.append(group.getCreationDate().format(DATE_FORMATTER)).append(",");
        sb.append(group.getStudentsCount()).append(",");
        sb.append(group.getExpelledStudents()).append(",");
        sb.append(group.getFormOfEducation().name()).append(",");
        sb.append(group.getSemesterEnum().name()).append(",");

        // Администратор
        Person admin = group.getGroupAdmin();
        if (admin != null) {
            sb.append(escapeCsv(admin.getName())).append(",");

            Date birthday = admin.getBirthday();
            sb.append(birthday != null ? birthday.getTime() : "").append(",");

            sb.append(admin.getWeight() != null ? admin.getWeight() : "").append(",");
            sb.append(escapeCsv(admin.getPassportID())).append(",");

            Location loc = admin.getLocation();
            if (loc != null) {
                sb.append(loc.getX()).append(",");
                sb.append(loc.getY() != null ? loc.getY() : "").append(",");
                sb.append(loc.getZ() != null ? loc.getZ() : "");
            } else {
                sb.append(",,");
            }
        } else {
            sb.append(",,,,,,"); // 7 пустых полей для администратора
        }

        return sb.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private Long parseLong(String str) {
        if (str == null || str.trim().isEmpty()) return 0L;
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private Integer parseInt(String str) {
        if (str == null || str.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private Float parseFloat(String str) {
        if (str == null || str.trim().isEmpty()) return 0f;
        try {
            return Float.parseFloat(str.trim());
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    private Double parseDouble(String str) {
        if (str == null || str.trim().isEmpty()) return 0.0;
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}