package com.jayway.jsonpath.internal.plan.nav;

import com.jayway.jsonpath.internal.index.node.Node;

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