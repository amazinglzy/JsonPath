package com.jayway.jsonpath.internal.path.evaluate;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.Path;

public class TreeTravelEvaluator {
    private Path path;

    public TreeTravelEvaluator(Path path) {
        this.path = path;
    }

    public EvaluationContext evaluate(Object document, Object rootDocument, Configuration configuration, boolean forUpdate) {
        return this.path.evaluate(document, rootDocument, configuration, forUpdate);
    }

    public EvaluationContext evaluate(Object document, Object rootDocument, Configuration configuration) {
        return this.evaluate(document, rootDocument, configuration, false);
    }
}
