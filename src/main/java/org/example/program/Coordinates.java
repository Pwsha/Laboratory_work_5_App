package org.example.program;

import java.util.Objects;

public class Coordinates {
    private Float x;
    private long y;

    public Coordinates() {
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        if (x == null) {
            throw new IllegalArgumentException("x не может быть null");
        }
        if (x > 741) {
            throw new IllegalArgumentException("x должно быть <= 741");
        }
        this.x = x;
    }


    public long getY() {
        return y;
    }

    public void setY(long y) {
        if (y <= -938) {
            throw new IllegalArgumentException("y должно быть > -938");
        }
        this.y = y;
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
        return "program.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}