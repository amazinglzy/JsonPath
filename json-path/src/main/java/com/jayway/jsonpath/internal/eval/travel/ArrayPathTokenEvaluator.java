package com.jayway.jsonpath.internal.eval.travel;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.ArrayPathToken;

public class ArrayPathTokenEvaluator implements PathTokenEvaluator {

    private ArrayPathToken token;
    public ArrayPathTokenEvaluator(ArrayPathToken token) {
        this.token = token;
    }

    @Override
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
