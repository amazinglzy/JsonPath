package com.jayway.jsonpath.internal.index;

public class Node {
    private String nodeName;
    private Object value;
    private long firstVisit, lastVisit;
    private int level;

    public Node(long firstVisit, long lastVisit, int level, String nodeName, Object value) {
        this.nodeName = nodeName;
        this.value = value;
        this.firstVisit = firstVisit;
        this.lastVisit = lastVisit;
        this.level = level;
    }

    public String getNodeName() {
        return nodeName;
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

}
