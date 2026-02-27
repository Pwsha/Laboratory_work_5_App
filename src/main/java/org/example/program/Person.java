package org.example.program;

import java.util.Date;
import java.util.Objects;

public class Person {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Date birthday; // Поле может быть null
    private Integer weight; // Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; // Длина строки не должна быть больше 20, Поле не может быть null
    private Location location; // Поле может быть null

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }


    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("weight должен быть > 0");
        }
        this.weight = weight;
    }

    public String getPassportID() {
        return passportID;
    }


    public void setPassportID(String passportID) {
        if (passportID == null) {
            throw new IllegalArgumentException("passportID не может быть null");
        }
        if (passportID.length() > 20) {
            throw new IllegalArgumentException("длина passportID не должна превышать 20 символов");
        }
        this.passportID = passportID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(birthday, person.birthday) &&
                Objects.equals(weight, person.weight) &&
                Objects.equals(passportID, person.passportID) &&
                Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, weight, passportID, location);
    }

    @Override
    public String toString() {
        return "program.Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                ", location=" + location +
                '}';
    }
}