package com.gdiamantidis.events;

public class AddCommentEvent implements Event {

    private int count;

    public AddCommentEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
