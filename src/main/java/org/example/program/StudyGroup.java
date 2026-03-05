package org.example.program;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudyGroup implements Comparable<StudyGroup> {
    private Long id; // Поле не может быть null, >0, уникальное, генерируется автоматически
    private String name; // Поле не может быть null, не пустое
    private Coordinates coordinates; // Поле не может быть null
    private LocalDateTime creationDate; // Поле не может быть null, генерируется автоматически
    private long studentsCount; // >0
    private int expelledStudents; // >0
    private FormOfEducation formOfEducation; // Поле не может быть null
    private Semester semesterEnum; // Поле не может быть null
    private Person groupAdmin; // Может быть null

    public StudyGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id должен быть > 0");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть пустым");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate не может быть null");
        }
        this.creationDate = creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(long studentsCount) {
        if (studentsCount <= 0) {
            throw new IllegalArgumentException("studentsCount должен быть > 0");
        }
        this.studentsCount = studentsCount;
    }

    public int getExpelledStudents() {
        return expelledStudents;
    }

    public void setExpelledStudents(int expelledStudents) {
        if (expelledStudents <= 0) {
            throw new IllegalArgumentException("expelledStudents должен быть > 0");
        }
        this.expelledStudents = expelledStudents;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        if (formOfEducation == null) {
            throw new IllegalArgumentException("formOfEducation не может быть null");
        }
        this.formOfEducation = formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        if (semesterEnum == null) {
            throw new IllegalArgumentException("semesterEnum не может быть null");
        }
        this.semesterEnum = semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    /**
     * Проверяет валидность объекта.
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