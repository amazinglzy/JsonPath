package com.jayway.jsonpath.internal.index.node;

public interface NodeIterator {
    Node read();
    void next();
    boolean hasNext();
}
