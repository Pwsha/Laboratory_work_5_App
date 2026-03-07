package org.example.program;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс для инициализации студенческой группы
 * @author Pwsha
 * @version v1.3
 */
public class StudyGroup implements Comparable<StudyGroup> {
    /** Поле id */
    private final Long id; // Поле не может быть null, >0, уникальное, генерируется автоматически
    /** Поле имени */
    private final String name; // Поле не может быть null, не пустое
    /** Поле координат */
    private final Coordinates coordinates; // Поле не может быть null
    /** Поле даты*/
    private final LocalDateTime creationDate; // Поле не может быть null, генерируется автоматически
    /** Поле количества студентов */
    private final long studentsCount; // >0
    /** Поле исключённых студентов */
    private final int expelledStudents; // >0
    /** Поле видом обучения */
    private final FormOfEducation formOfEducation; // Поле не может быть null
    /** Поле семестров */
    private final Semester semesterEnum; // Поле не может быть null
    /** Поле админа */
    private final Person groupAdmin; // Может быть null

    /**
     * Конструктор со всеми значениями
     * @param builder
     */
    private StudyGroup(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.coordinates = builder.coordinates;
        this.creationDate = builder.creationDate;
        this.studentsCount = builder.studentsCount;
        this.expelledStudents = builder.expelledStudents;
        this.formOfEducation = builder.formOfEducation;
        this.semesterEnum = builder.semesterEnum;
        this.groupAdmin = builder.groupAdmin;
    }

    /**
     * Класс Билдер для построения конструкторов
     */
    public static class Builder {
        private Long id;
        private String name;
        private Coordinates coordinates;
        private LocalDateTime creationDate;
        private long studentsCount;
        private int expelledStudents;
        private FormOfEducation formOfEducation;
        private Semester semesterEnum;
        private Person groupAdmin;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Builder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder studentsCount(long studentsCount) {
            this.studentsCount = studentsCount;
            return this;
        }

        public Builder expelledStudents(int expelledStudents) {
            this.expelledStudents = expelledStudents;
            return this;
        }

        public Builder formOfEducation(FormOfEducation formOfEducation) {
            this.formOfEducation = formOfEducation;
            return this;
        }

        public Builder semesterEnum(Semester semesterEnum) {
            this.semesterEnum = semesterEnum;
            return this;
        }

        public Builder groupAdmin(Person groupAdmin) {
            this.groupAdmin = groupAdmin;
            return this;
        }

        public StudyGroup build() {
            // Валидация
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("id должен быть > 0");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("name не может быть пустым");
            }
            if (coordinates == null) {
                throw new IllegalArgumentException("coordinates не может быть null");
            }
            if (creationDate == null) {
                this.creationDate = LocalDateTime.now();
            }
            if (studentsCount <= 0) {
                throw new IllegalArgumentException("studentsCount должен быть > 0");
            }
            if (expelledStudents <= 0) {
                throw new IllegalArgumentException("expelledStudents должен быть > 0");
            }
            if (formOfEducation == null) {
                throw new IllegalArgumentException("formOfEducation не может быть null");
            }
            if (semesterEnum == null) {
                throw new IllegalArgumentException("semesterEnum не может быть null");
            }

            return new StudyGroup(this);
        }
    }

    /**
     * Функция получения значения поля {@link StudyGroup#id}
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#name}
     * @return id
     */
    public String getName() {
        return name;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#coordinates}
     * @return id
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#creationDate}
     * @return id
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#studentsCount}
     * @return id
     */
    public long getStudentsCount() {
        return studentsCount;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#expelledStudents}
     * @return id
     */
    public int getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#formOfEducation}
     * @return id
     */
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#semesterEnum}
     * @return id
     */
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    /**
     * Функция получения значения поля {@link StudyGroup#groupAdmin}
     * @return id
     */
    public Person getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * Метод проверки значений
     * @return true/false
     */
    public boolean isValid() {
        try {
            return id != null && id > 0 &&
                    name != null && !name.trim().isEmpty() &&
                    coordinates != null &&
                    creationDate != null &&
                    studentsCount > 0 &&
                    expelledStudents > 0 &&
                    formOfEducation != null &&
                    semesterEnum != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод сортировки по id
     * @param other
     * @return compareTo(id)
     */
    @Override
    public int compareTo(StudyGroup other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup that = (StudyGroup) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("StudyGroup[id=%d, name='%s', students=%d, form=%s, semester=%s]",
                id, name, studentsCount, formOfEducation, semesterEnum);
    }
}