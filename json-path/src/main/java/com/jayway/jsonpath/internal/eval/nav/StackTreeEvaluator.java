package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.eval.ReadEvaluator;
import com.jayway.jsonpath.internal.eval.travel.EvaluationContextImpl;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.path.*;

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
        EvaluationContextImpl ret = new EvaluationContextImpl(this.path, rootDocument, configuration, false);
        return ret;
    }


    private void evaluatePath(PathToken currentToken, PathToken parentToken, IndexContext indexContext, EvaluationContextImpl ctx) {
        if (currentToken instanceof ArrayPathToken) {
        } else if (currentToken instanceof FunctionPathToken) {
        } else if (currentToken instanceof PredicatePathToken) {
        } else if (currentToken instanceof RootPathToken) {
        } else if (currentToken instanceof WildcardPathToken) {
        } else if (currentToken instanceof ScanPathToken) {
        } else if (currentToken instanceof PropertyPathToken) {
        }
    }
}
