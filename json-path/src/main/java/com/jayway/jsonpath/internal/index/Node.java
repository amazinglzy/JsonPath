package com.jayway.jsonpath.internal.index;

public class Node {
    private Object value;
    private long firstVisit, lastVisit;
    private int level;

    public Node(long firstVisit, long lastVisit, int level, Object value) {
        this.value = value;
        this.firstVisit = firstVisit;
        this.lastVisit = lastVisit;
        this.level = level;
    }

    public Object getValue() {
        return value;
    }

    public long getFirstVisit() {
        return firstVisit;
    }

    public long getLastVisit() {
        return lastVisit;
    }

    public int getLevel() {
        return level;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setFirstVisit(long firstVisit) {
        this.firstVisit = firstVisit;
    }

    public void setLastVisit(long lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
