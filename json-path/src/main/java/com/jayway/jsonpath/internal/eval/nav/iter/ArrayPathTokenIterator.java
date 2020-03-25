package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.internal.eval.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.path.ArrayPathToken;

// TODO
public class ArrayPathTokenIterator implements ResultIterator {
    private ResultIterator parIter ;
    private ArrayPathToken token;

    public ArrayPathTokenIterator(ResultIterator parIter, ArrayPathToken token) {
        this.parIter = parIter;
        this.token = token;
    }

    @Override
    public void next() {
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Node getNode() {
        return null;
    }
}
