package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.eval.ReadEvaluator;

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
        return null;
    }
}
