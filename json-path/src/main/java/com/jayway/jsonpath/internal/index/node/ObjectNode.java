package com.jayway.jsonpath.internal.index.node;

import com.jayway.jsonpath.internal.index.node.Node;

public class ObjectNode extends Node {
    private String nodeName;

    public ObjectNode(String nodeName, Object value) {
        super(-1, -1, -1, value);
        this.nodeName = nodeName;
    }

    public ObjectNode(String nodeName, long firstVisit, long lastVisit, int level, Object value) {
        super(firstVisit, lastVisit, level, value);
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return this.nodeName;
    }
}
