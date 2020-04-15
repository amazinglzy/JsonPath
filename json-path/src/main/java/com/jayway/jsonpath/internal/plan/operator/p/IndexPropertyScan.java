package com.jayway.jsonpath.internal.plan.operator.p;

import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.Node;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.plan.nav.ResultIterator;
import com.jayway.jsonpath.internal.plan.nav.iter.ArrayPathTokenIterator;
import com.jayway.jsonpath.internal.plan.operator.PlanOperator;

import java.util.LinkedList;
import java.util.List;

public class IndexPropertyScan implements PlanOperator {
    private List<String> properties;
    private IndexContext indexContext;

    public IndexPropertyScan(IndexContext indexContext, List<String> properties) {
        this.properties = properties;
        this.indexContext = indexContext;
    }

    public IndexPropertyScan(IndexContext indexContext, final String property) {
        this.properties = new LinkedList<String>(){{
            add(property);
        }};
        this.indexContext = indexContext;
    }

    @Override
    public NodeIterator iterator() {
        return this.indexContext.openObject(properties);
    }
}
