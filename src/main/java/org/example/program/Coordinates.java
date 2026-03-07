package org.example.program;

import java.util.Objects;

/**
 * Класс для инициализации координат
 * @author Pwsha
 * @version v1.3
 */
public class Coordinates {
    /** Поле x */
    private Float x; // Максимальное значение: 741, не null
    /** Поле y */
    private long y; // > -938

    /**
     * Конструктор со всеми значениями
     * @param builder
     */
    private Coordinates(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
    }

    /**
     * Класс Билдер для построения конструкторов
     */
    public static class Builder {
        private Float x;
        private long y;

        public Builder x(Float x) {
            this.x = x;
            return this;
        }

        public Builder y(long y) {
            this.y = y;
            return this;
        }

        public Coordinates build() {
            if (x == null) {
                throw new IllegalArgumentException("x должен быть не null");
            }
            if (x > 741) {
                throw new IllegalArgumentException("x должен быть <= 741");
            }
            if (y <= -938) {
                throw new IllegalArgumentException("y должен быть > -938");
            }
            return new Coordinates(this);
        }
    }

    /**
     * Функция получения значения поля {@link Coordinates#x}
     * @return x
     */
    public Float getX() {
        return x;
    }

    /**
     * Функция получения значения поля {@link Coordinates#y}
     * @return y
     */
    public long getY() {
        return y;
    }

    /**
     * Метод проверки значений
     * @return true/false
     */
    public boolean isValid() {
        return x != null && x <= 741 && y > -938;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return y == that.y && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + '}';
    }
}