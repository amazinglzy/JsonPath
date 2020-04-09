package com.jayway.jsonpath.internal.index.node;

public interface NodeIterator {
    /*
    return the current node of the iterator
     */
    Node read();

    /*

     */
    void next();

    /*

     */
    boolean hasNext();

    NodeIterator cloneCurrentIterator();
}
