package org.example.program;

import java.util.Objects;

public class Location {
    private double x;
    private Double y;
    private Float z;

    public Location() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        if (y == null) {
            throw new IllegalArgumentException("y не может быть null");
        }
        this.y = y;
    }

    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        if (z == null) {
            throw new IllegalArgumentException("z не может быть null");
        }
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.x, x) == 0 &&
                Objects.equals(y, location.y) &&
                Objects.equals(z, location.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "program.Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}