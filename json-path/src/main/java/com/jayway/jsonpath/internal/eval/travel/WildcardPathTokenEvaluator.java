package com.jayway.jsonpath.internal.eval.travel;

import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.WildcardPathToken;

import static java.util.Arrays.asList;

public class WildcardPathTokenEvaluator implements PathTokenEvaluator {
    private WildcardPathToken token;
    public WildcardPathTokenEvaluator(WildcardPathToken token) {
        this.token = token;
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        if (ctx.jsonProvider().isMap(model)) {
            for (String property : ctx.jsonProvider().getPropertyKeys(model)) {
                this.token.handleObjectProperty(currentPath, model, ctx, asList(property));
            }
        } else if (ctx.jsonProvider().isArray(model)) {
            for (int idx = 0; idx < ctx.jsonProvider().length(model); idx++) {
                try {
                    this.token.handleArrayIndex(idx, currentPath, model, ctx);
                } catch (PathNotFoundException p){
                    if(ctx.options().contains(Option.REQUIRE_PROPERTIES)){
                        throw p;
                    }
                }
            }
        }
    }
}
