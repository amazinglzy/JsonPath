package com.jayway.jsonpath.internal.plan.nav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.plan.ReadEvaluator;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;

// TODO
// Trans Path into a Tree of Primitive
// Implement Primitive based on IndexContext
// Using Primitive to Evaluate Path
public class StackTreeEvaluator implements ReadEvaluator {
    private Path path;

    public StackTreeEvaluator(Path path) {
        this.path = path;
    }

    @Override
    public EvaluationContext evaluate(Object document, Object rootDocument, Configuration configuration) {
        IndexContext indexContext = Indexer.index(document, configuration);
        EvaluationReadContext ret = new EvaluationReadContext(this.path, rootDocument, configuration);
//        ResultIterator iter = new ResultIterator(this.path.getRootToken(), indexContext);
//        for (; iter.hasNext(); iter.next()) {
//            ret.addResult(iter.getPath(), iter.getValue());
//        }
        return ret;
    }
}
