package com.jayway.jsonpath.internal.eval.travel;

import com.jayway.jsonpath.internal.PathRef;

public interface PathTokenEvaluator {
    void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx);
}
