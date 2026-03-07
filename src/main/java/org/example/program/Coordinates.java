package org.example.program;

import java.util.Objects;


public class Coordinates {
    private Float x; // Максимальное значение: 741, не null
    private long y; // > -938

    private Coordinates(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
    }

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
            if (x == null || x > 741) {
                throw new IllegalArgumentException("x должен быть <= 741 и не null");
            }
            if (y <= -938) {
                throw new IllegalArgumentException("y должен быть > -938");
            }
            return new Coordinates(this);
        }
    }

    public Float getX() {
        return x;
    }
//
//    public void setX(Float x) {
//        if (x == null) {
//            throw new IllegalArgumentException("x не может быть null");
//        }
//        if (x > 741) {
//            throw new IllegalArgumentException("x должно быть <= 741");
//        }
//        this.x = x;
//    }
//
    public long getY() {
        return y;
    }
//
//    public void setY(long y) {
//        if (y <= -938) {
//            throw new IllegalArgumentException("y должно быть > -938");
//        }
//        this.y = y;
//    }

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