package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.internal.eval.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;

public class RootTokenIterator implements ResultIterator {
    private NodeIterator iter;
    private IndexContext indexContext;

    public RootTokenIterator(IndexContext indexContext) {
        this.indexContext = indexContext;
        this.iter =  indexContext.openObject("$");
    }

    @Override
    public void next() {
        this.iter.next();
    }

    @Override
    public String getPath() {
        return "$";
    }

    @Override
    public Object getValue() {
        return this.iter.peek().getValue();
    }

    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public Node getNode() {
        return this.iter.peek();
    }
}
