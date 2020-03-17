package com.jayway.jsonpath.internal.index.node;

public interface NodeIterator {
    Node peek();
    Node next();
    boolean hasNext();
}
