package com.jayway.jsonpath.internal.path.evaluate;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.EvaluationContextImpl;

public interface PathTokenEvaluator {
    void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx);
}
