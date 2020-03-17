package com.jayway.jsonpath.internal.index.node;

public class CombinedNodeIterator implements NodeIterator {
    private NodeIterator iter1, iter2;

    public CombinedNodeIterator(NodeIterator iter1, NodeIterator iter2) {
        this.iter1 = iter1;
        this.iter2 = iter2;
    }

    @Override
    public Node peek() {
        Node n1 = this.iter1.peek();
        Node n2 = this.iter2.peek();
        if (n1.compareTo(n2) < 0) return n1;
        else return n2;
    }

    @Override
    public Node next() {
        if (this.iter1.hasNext() && this.iter2.hasNext()) {
            Node n1 = this.iter1.peek();
            Node n2 = this.iter2.peek();
            if (n1.compareTo(n2) < 0) return this.iter1.next();
            else return this.iter2.next();
        } else if (this.iter1.hasNext()) {
            return this.iter1.next();
        } else {
            return this.iter2.next();
        }
    }

    @Override
    public boolean hasNext() {
        return (this.iter1.hasNext() || this.iter2.hasNext());
    }
}
