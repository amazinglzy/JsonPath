package com.jayway.jsonpath.internal.index;

public class ArrayNode extends Node{
    private long index;
    public ArrayNode(long index, Object value) {
        super(-1, -1, -1, value);
        this.index = index;
    }

    public ArrayNode(long index, long firstVisit, long lastVisit, int level, Object value) {
        super(firstVisit, lastVisit, level, value);
        this.index = index;
    }
}
