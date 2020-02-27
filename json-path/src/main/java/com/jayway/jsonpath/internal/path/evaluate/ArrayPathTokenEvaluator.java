package com.jayway.jsonpath.internal.path.evaluate;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.ArrayPathToken;
import com.jayway.jsonpath.internal.path.EvaluationContextImpl;

public class ArrayPathTokenEvaluator {

    private ArrayPathToken token;
    public ArrayPathTokenEvaluator(ArrayPathToken token) {
        this.token = token;
    }

    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        if (! this.token.checkArrayModel(currentPath, model, ctx))
            return;
        if(this.token.getArraySliceOperation() != null){
            this.token.evaluateSliceOperation(currentPath, parent, model, ctx);
        } else {
            this.token.evaluateIndexOperation(currentPath, parent, model, ctx);
        }
    }
}
