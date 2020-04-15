package com.jayway.jsonpath.internal.plan.operator;

import com.jayway.jsonpath.internal.index.node.NodeIterator;

public interface PlanOperator {
    NodeIterator iterator();
}
