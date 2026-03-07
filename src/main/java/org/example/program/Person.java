package org.example.program;

import java.util.Date;
import java.util.Objects;

/**
 * Класс для инициализации админа
 * @author Pwsha
 * @version v1.3
 */
public class Person {
    /** Поле имени*/
    private String name; // Не null, не пустое
    /** Поле дня рождения */
    private Date birthday; // Может быть null
    /** Поле веса */
    private Integer weight; // Не null, >0
    /** Поле id паспорта */
    private String passportID; // Длина <= 20, не null
    /** Поле локации */
    private Location location; // Может быть null

    /**
     * Конструктор со всеми значениями
     * @param builder
     */
    private Person(Builder builder) {
        this.name = builder.name;
        this.birthday = builder.birthday;
        this.weight = builder.weight;
        this.passportID = builder.passportID;
        this.location = builder.location;
    }

    /**
     * Класс Билдер для построения конструкторов
     */
    public static class Builder {
        private String name;
        private Date birthday;
        private Integer weight;
        private String passportID;
        private Location location;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder birthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder weight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public Builder passportID(String passportID) {
            this.passportID = passportID;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Person build() {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("name не может быть пустым");
            }
            if (weight == null || weight <= 0) {
                throw new IllegalArgumentException("weight должен быть > 0");
            }
            if (passportID == null) {
                throw new IllegalArgumentException("passportID должен быть не null");
            }
            if (passportID.length() > 20) {
                throw new IllegalArgumentException("passportID должен быть не менее 20 символов");
            }
            return new Person(this);
        }
    }

    /**
     * Функция получения значения поля {@link Person#name}
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Функция получения значения поля {@link Person#birthday}
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }
    /**
     * Функция получения значения поля {@link Person#weight}
     * @return weight
     */
    public Integer getWeight() {
        return weight;
    }
    /**
     * Функция получения значения поля {@link Person#passportID}
     * @return passportID
     */
    public String getPassportID() {
        return passportID;
    }
    /**
     * Функция получения значения поля {@link Person#location}
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Метод проверки значений
     * @return true/false
     */
    public boolean isValid() {
        try {
            return name != null && !name.trim().isEmpty() &&
                    weight != null && weight > 0 &&
                    passportID != null && passportID.length() <= 20;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(passportID, person.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passportID);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', passport='" + passportID + "'}";
    }

}