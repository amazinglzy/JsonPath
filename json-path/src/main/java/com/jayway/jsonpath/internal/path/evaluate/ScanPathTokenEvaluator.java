package com.jayway.jsonpath.internal.path.evaluate;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.EvaluationContextImpl;
import com.jayway.jsonpath.internal.path.PathToken;
import com.jayway.jsonpath.internal.path.ScanPathToken;

public class ScanPathTokenEvaluator {
    private ScanPathToken token;

    public ScanPathTokenEvaluator(ScanPathToken token) {
        this.token = token;
    }


    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {

        PathToken pt = this.token.next();

        this.token.walk(pt, currentPath, parent,  model, ctx, this.token.createScanPredicate(pt, ctx));
    }
}
