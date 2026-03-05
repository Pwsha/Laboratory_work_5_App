package org.example.program;

import java.util.Objects;

public class Location {
    private double x;
    private Double y; // Не null
    private Float z; // Не null

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

    public boolean isValid() {
        return y != null && z != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(x, location.x) == 0 &&
                Objects.equals(y, location.y) &&
                Objects.equals(z, location.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Location{x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}