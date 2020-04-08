package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.internal.eval.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.ArrayNode;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.path.ArrayPathToken;

public class ArrayPathTokenIterator extends TokenIterator {

    public ArrayPathTokenIterator(ResultIterator parIter, ArrayPathToken token, IndexContext indexContext) {
        super(parIter, token, indexContext);
    }

    @Override
    protected NodeIterator openCorrespondNodeIterator() {
        if (((ArrayPathToken)this.token).getArrayIndexOperation() != null)
            return this.indexContext.openArray(((ArrayPathToken)token).getArrayIndexOperation());
        else
            return this.indexContext.openArray(((ArrayPathToken)token).getArraySliceOperation());
    }

    @Override
    protected String getPathFragment(Node node) {
        return "." + String.valueOf(((ArrayNode)this.currentNodeIter.read()).getIndex());
    }
}
