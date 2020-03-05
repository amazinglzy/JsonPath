package com.jayway.jsonpath.internal.eval;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.EvaluationContext;

public interface WriteEvaluator {
    EvaluationContext evaluate(Object document, Object rootDocument, Configuration configuration, boolean forUpdate);
}
