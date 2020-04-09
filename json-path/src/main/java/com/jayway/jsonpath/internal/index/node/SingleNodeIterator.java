package com.jayway.jsonpath.internal.index.node;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.LinkedList;

public class SingleNodeIterator implements NodeIterator {
    private ArrayList<Node> data;
    private int idx;

    public SingleNodeIterator(List<Node> data) {
        this.data = new ArrayList<Node>(data);
        this.idx = 0;
    }

    private SingleNodeIterator(ArrayList<Node> data, int idx) {
        this.data = data;
        this.idx = idx;
    }

    @Override
    public Node read() {
        return this.data.get(this.idx);
    }

    @Override
    public void next() {
        if (this.idx >= this.data.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.idx ++;
    }

    @Override
    public boolean hasNext() {
        return this.idx < this.data.size();
    }

    @Override
    public NodeIterator cloneCurrentIterator() {
        return new SingleNodeIterator(this.data, this.idx);
    }
}
