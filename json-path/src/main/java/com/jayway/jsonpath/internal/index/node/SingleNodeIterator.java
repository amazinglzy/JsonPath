package com.jayway.jsonpath.internal.index.node;

import java.util.ListIterator;
import java.util.LinkedList;

public class SingleNodeIterator implements NodeIterator {
    private LinkedList<Node> data;
    private ListIterator<Node> iter;

    public SingleNodeIterator(LinkedList<Node> data) {
        this.data = data;
        this.iter = this.data.listIterator();
    }

    @Override
    public Node read() {
        Node ret = this.iter.next();
        this.iter.previous();
        return ret;
    }

    @Override
    public void next() {
        this.iter.next();
    }

    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }
}
