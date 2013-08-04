package com.gdiamantidis.events;

public class AverageCountEvent implements Event {

    private final Double count;

    public AverageCountEvent(Double count) {
        this.count = count;
    }

    public Double getCount() {
        return count;
    }
}
