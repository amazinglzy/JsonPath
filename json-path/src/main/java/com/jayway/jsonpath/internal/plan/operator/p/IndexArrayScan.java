package com.jayway.jsonpath.internal.plan.operator.p;

import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.path.ArrayIndexOperation;
import com.jayway.jsonpath.internal.path.ArraySliceOperation;
import com.jayway.jsonpath.internal.plan.operator.PlanOperator;

import static com.jayway.jsonpath.internal.Utils.*;

public class IndexArrayScan implements PlanOperator {
    private ArrayIndexOperation indexOperation;
    private ArraySliceOperation sliceOperation;
    private IndexContext indexContext;

    public IndexArrayScan(IndexContext indexContext,
                          ArrayIndexOperation indexOperation,
                          ArraySliceOperation sliceOperation) {
        isTrue(indexOperation != null || sliceOperation != null, "one of indexOperation " +
                "and sliceOperation must not be null");

        this.indexContext = indexContext;
        this.indexOperation = indexOperation;
        this.sliceOperation = sliceOperation;
    }

    @Override
    public NodeIterator iterator() {
        if (this.indexOperation != null) return this.indexContext.openArray(this.indexOperation);
        else return this.indexContext.openArray(this.sliceOperation);
    }
}
