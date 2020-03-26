package com.jayway.jsonpath.internal.index.node;

public interface NodeIterator {
    Node peek();
    void next();
    boolean hasNext();
}
