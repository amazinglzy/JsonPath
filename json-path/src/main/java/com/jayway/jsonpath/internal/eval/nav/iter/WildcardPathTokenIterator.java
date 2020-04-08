package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.internal.eval.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import com.jayway.jsonpath.internal.path.PropertyPathToken;
import com.jayway.jsonpath.internal.path.WildcardPathToken;

public class WildcardPathTokenIterator extends TokenIterator {

    public WildcardPathTokenIterator(ResultIterator parIter, WildcardPathToken token, IndexContext indexContext) {
        super(parIter, token, indexContext);
    }

    @Override
    protected NodeIterator openCorrespondNodeIterator() {
        return indexContext.openAll();
    }

    @Override
    protected String getPathFragment(Node node) {
        return ".*";
    }
}
