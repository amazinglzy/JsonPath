package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.path.PathToken;

public interface ResultIterator {
    void next();
    boolean hasNext();

    String getPath();
    Object getValue();
    Node getNode();
}

/*
        if (currentToken instanceof ArrayPathToken) {
        } else if (currentToken instanceof FunctionPathToken) {
        } else if (currentToken instanceof PredicatePathToken) {
        } else if (currentToken instanceof RootPathToken) {
        } else if (currentToken instanceof WildcardPathToken) {
        } else if (currentToken instanceof ScanPathToken) {
        } else if (currentToken instanceof PropertyPathToken) {
        }
 */