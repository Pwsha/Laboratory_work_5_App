package org.example.program;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parsing_csv {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public HashSet<StudyGroup> readStudyGroupsFromCSV(String filePath)
            throws IOException, CsvException {
        HashSet<StudyGroup> studyGroups = new HashSet<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            // Пропускаем заголовок, если он есть
            boolean skipHeader = true;

            for (String[] record : records) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                try {
                    StudyGroup group = parseStudyGroup(record);
                    studyGroups.add(group);
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка при парсинге записи: " + Arrays.toString(record));
                    System.err.println("Причина: " + e.getMessage());
                }
            }
        }

        return studyGroups;
    }

    private StudyGroup parseStudyGroup(String[] data) {
        if (data.length < 19) { // Минимальное количество полей
            throw new IllegalArgumentException("Недостаточно полей в записи");
        }

        StudyGroup group = new StudyGroup();
        int index = 0;

        // StudyGroup fields
        group.setId(parseLong(data[index++], "id"));
        group.setName(parseString(data[index++], "name"));

        // Coordinates
        Coordinates coordinates = new Coordinates();
        coordinates.setX(parseFloat(data[index++], "coordinates.x", 741.0f));
        coordinates.setY(parseLongWithMin(data[index++], "coordinates.y", -938));
        group.setCoordinates(coordinates);

        // Creation date
        group.setCreationDate(parseLocalDateTime(data[index++], "creationDate"));

        // Other StudyGroup fields
        group.setStudentsCount(parseLongWithMin(data[index++], "studentsCount", 0));
        group.setExpelledStudents(parseIntWithMin(data[index++], "expelledStudents", 0));
        group.setFormOfEducation(parseFormOfEducation(data[index++]));
        group.setSemesterEnum(parseSemester(data[index++]));

        // Person (groupAdmin) - может быть null
        String personName = data[index++];
        if (personName != null && !personName.isEmpty() && !personName.equals("null")) {
            Person groupAdmin = new Person();
            groupAdmin.setName(personName);
            groupAdmin.setBirthday(parseDate(data[index++]));
            groupAdmin.setWeight(parseIntegerWithMin(data[index++], "weight", 0));
            groupAdmin.setPassportID(parseStringWithMaxLength(data[index++], "passportID", 20));

            // Location - может быть null
            String locationX = data[index++];
            if (locationX != null && !locationX.isEmpty() && !locationX.equals("null")) {
                Location location = new Location();
                location.setX(parseDouble(locationX, "location.x"));
                location.setY(parseDouble(data[index++], "location.y"));
                location.setZ(parseFloat(data[index++], "location.z"));
                groupAdmin.setLocation(location);
            } else {
                // Пропускаем поля location
                index += 3;
                groupAdmin.setLocation(null);
            }

            group.setGroupAdmin(groupAdmin);
        } else {
            // Пропускаем все поля Person и Location
            index += 6; // birthday, weight, passportID, location.x, location.y, location.z
            group.setGroupAdmin(null);
        }

        return group;
    }

    // Вспомогательные методы для парсинга с валидацией

    private Long parseLong(String value, String fieldName) {
        try {
            Long result = Long.parseLong(value.trim());
            if (result <= 0) {
                throw new IllegalArgumentException(fieldName + " должен быть больше 0");
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private Long parseLongWithMin(String value, String fieldName, long minValue) {
        try {
            Long result = Long.parseLong(value.trim());
            if (result <= minValue) {
                throw new IllegalArgumentException(fieldName + " должен быть больше " + minValue);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private Integer parseIntegerWithMin(String value, String fieldName, int minValue) {
        try {
            Integer result = Integer.parseInt(value.trim());
            if (result <= minValue) {
                throw new IllegalArgumentException(fieldName + " должен быть больше " + minValue);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private int parseIntWithMin(String value, String fieldName, int minValue) {
        try {
            int result = Integer.parseInt(value.trim());
            if (result <= minValue) {
                throw new IllegalArgumentException(fieldName + " должен быть больше " + minValue);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private Float parseFloat(String value, String fieldName) {
        try {
            return Float.parseFloat(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private Float parseFloat(String value, String fieldName, float maxValue) {
        try {
            Float result = Float.parseFloat(value.trim());
            if (result > maxValue) {
                throw new IllegalArgumentException(fieldName + " не может быть больше " + maxValue);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private Double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа для " + fieldName);
        }
    }

    private String parseString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        return value.trim();
    }

    private String parseStringWithMaxLength(String value, String fieldName, int maxLength) {
        String trimmed = parseString(value, fieldName);
        if (trimmed.length() > maxLength) {
            throw new IllegalArgumentException(fieldName + " не может быть длиннее " + maxLength + " символов");
        }
        return trimmed;
    }

    private LocalDateTime parseLocalDateTime(String value, String fieldName) {
        try {
            return LocalDateTime.parse(value.trim(), DATE_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты для " + fieldName);
        }
    }

    private Date parseDate(String value) {
        if (value == null || value.trim().isEmpty() || value.equals("null")) {
            return null;
        }
        try {
            // Здесь нужно использовать соответствующий формат даты
            // Например, SimpleDateFormat или другой парсер
            return new Date(Long.parseLong(value.trim())); // Пример для timestamp
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private FormOfEducation parseFormOfEducation(String value) {
        try {
            return FormOfEducation.valueOf(value.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверное значение FormOfEducation: " + value);
        }
    }

    private Semester parseSemester(String value) {
        try {
            return Semester.valueOf(value.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверное значение Semester: " + value);
        }
    }

    // Пример использования
    public static void main(String[] args) {
        Parsing_csv reader = new Parsing_csv();

        try {
            HashSet<StudyGroup> groups = reader.readStudyGroupsFromCSV("study_groups.csv");
            System.out.println("Загружено групп: " + groups.size());

            // Вывод загруженных групп
            for (StudyGroup group : groups) {
                System.out.println(group);
            }

        } catch (IOException | CsvException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
}