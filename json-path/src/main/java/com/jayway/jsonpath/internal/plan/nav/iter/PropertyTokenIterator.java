package com.jayway.jsonpath.internal.plan.nav.iter;

import com.jayway.jsonpath.internal.plan.nav.ResultIterator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import com.jayway.jsonpath.internal.path.PropertyPathToken;

public class PropertyTokenIterator extends TokenIterator {
    public PropertyTokenIterator(ResultIterator parIter, PropertyPathToken token, IndexContext indexContext) {
        super(parIter, token, indexContext);
    }

    @Override
    protected NodeIterator openCorrespondNodeIterator() {
        return indexContext.openObject(((PropertyPathToken)token).getProperties());
    }

    @Override
    protected String getPathFragment(Node node) {
        return "." + ((ObjectNode)node).getNodeName();
    }
}
