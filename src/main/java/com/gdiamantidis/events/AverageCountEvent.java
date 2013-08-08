package com.gdiamantidis.events;

public class AverageCountEvent implements Event {

    private final Double count;

    public AverageCountEvent(Double count) {
        this.count = count;
    }

    public Double getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AverageCountEvent that = (AverageCountEvent) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return count != null ? count.hashCode() : 0;
    }
}
