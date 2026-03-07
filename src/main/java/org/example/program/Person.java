package org.example.program;

import java.util.Date;
import java.util.Objects;

public class Person {
    private String name; // Не null, не пустое
    private Date birthday; // Может быть null
    private Integer weight; // Не null, >0
    private String passportID; // Длина <= 20, не null
    private Location location; // Может быть null

    private Person(Builder builder) {
        this.name = builder.name;
        this.birthday = builder.birthday;
        this.weight = builder.weight;
        this.passportID = builder.passportID;
        this.location = builder.location;
    }
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
            if (passportID == null || passportID.length() > 20) {
                throw new IllegalArgumentException("passportID должен быть не null и <= 20 символов");
            }
            return new Person(this);
        }
    }

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
    public Date getBirthday() {
        return birthday;
    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }
//
    public Integer getWeight() {
        return weight;
    }
//
//    public void setWeight(Integer weight) {
//        if (weight == null || weight <= 0) {
//            throw new IllegalArgumentException("weight должен быть > 0");
//        }
//        this.weight = weight;
//    }

    public String getPassportID() {
        return passportID;
    }
//
//    public void setPassportID(String passportID) {
//        if (passportID == null) {
//            throw new IllegalArgumentException("passportID не может быть null");
//        }
//        if (passportID.length() > 20) {
//            throw new IllegalArgumentException("длина passportID не должна превышать 20");
//        }
//        this.passportID = passportID;
//    }
//
    public Location getLocation() {
        return location;
    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }

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