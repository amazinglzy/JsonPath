package com.jayway.jsonpath.internal.path.evaluate;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.function.Parameter;
import com.jayway.jsonpath.internal.function.PathFunction;
import com.jayway.jsonpath.internal.function.PathFunctionFactory;
import com.jayway.jsonpath.internal.path.EvaluationContextImpl;
import com.jayway.jsonpath.internal.path.FunctionPathToken;


public class FunctionPathTokenEvaluator {
    private FunctionPathToken token;
    public FunctionPathTokenEvaluator(FunctionPathToken token) {
        this.token = token;
    }

    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        PathFunction pathFunction = PathFunctionFactory.newFunction(this.token.getFunctionName());
        evaluateParameters(currentPath, parent, model, ctx);
        Object result = pathFunction.invoke(currentPath, parent, model, ctx, this.token.getFunctionParams());
        ctx.addResult(currentPath + "." + this.token.getFunctionName(), parent, result);
        if (!this.token.isLeaf()) {
            this.token.next().evaluate(currentPath, parent, result, ctx);
        }
    }

    private void evaluateParameters(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {

        if (null != this.token.getFunctionParams()) {
            for (Parameter param : this.token.getFunctionParams()) {
                if (!param.hasEvaluated()) {
                    switch (param.getType()) {
                        case PATH:
                            param.setCachedValue(new TreeTravelEvaluator(param.getPath()).evaluate(ctx.rootDocument(), ctx.rootDocument(), ctx.configuration()).getValue());
                            param.setEvaluated(true);
                            break;
                        case JSON:
                            param.setCachedValue(ctx.configuration().jsonProvider().parse(param.getJson()));
                            param.setEvaluated(true);
                            break;
                    }
                }
            }
        }
    }
}
