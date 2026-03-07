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

    public Long getId() {
        return id;
    }
//
//    public void setId(Long id) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException("id должен быть > 0");
//        }
//        this.id = id;
//    }
//
    public String getName() {
        return name;
    }
//
//    public void setName(String name) {
//        if (name == null || name.trim().isEmpty()) {
//            throw new IllegalArgumentException("name не может быть пустым");
//        }
//        this.name = name;
//    }
//
    public Coordinates getCoordinates() {
        return coordinates;
    }
//
//    public void setCoordinates(Coordinates coordinates) {
//        if (coordinates == null) {
//            throw new IllegalArgumentException("coordinates не может быть null");
//        }
//        this.coordinates = coordinates;
//    }
//
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
//
//    public void setCreationDate(LocalDateTime creationDate) {
//        if (creationDate == null) {
//            throw new IllegalArgumentException("creationDate не может быть null");
//        }
//        this.creationDate = creationDate;
//    }
//
    public long getStudentsCount() {
        return studentsCount;
    }
//
//    public void setStudentsCount(long studentsCount) {
//        if (studentsCount <= 0) {
//            throw new IllegalArgumentException("studentsCount должен быть > 0");
//        }
//        this.studentsCount = studentsCount;
//    }
//
    public int getExpelledStudents() {
        return expelledStudents;
    }
//
//    public void setExpelledStudents(int expelledStudents) {
//        if (expelledStudents <= 0) {
//            throw new IllegalArgumentException("expelledStudents должен быть > 0");
//        }
//        this.expelledStudents = expelledStudents;
//    }
//
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }
//
//    public void setFormOfEducation(FormOfEducation formOfEducation) {
//        if (formOfEducation == null) {
//            throw new IllegalArgumentException("formOfEducation не может быть null");
//        }
//        this.formOfEducation = formOfEducation;
//    }
//
    public Semester getSemesterEnum() {
        return semesterEnum;
    }
//
//    public void setSemesterEnum(Semester semesterEnum) {
//        if (semesterEnum == null) {
//            throw new IllegalArgumentException("semesterEnum не может быть null");
//        }
//        this.semesterEnum = semesterEnum;
//    }
//
    public Person getGroupAdmin() {
        return groupAdmin;
    }
//
//    public void setGroupAdmin(Person groupAdmin) {
//        this.groupAdmin = groupAdmin;
//    }


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