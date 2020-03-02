package com.jayway.jsonpath.internal.eval.travel;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.EvaluationContextImpl;
import com.jayway.jsonpath.internal.path.RootPathToken;

public class RootPathTokenEvaluator implements PathTokenEvaluator {
    private RootPathToken token;

    public RootPathTokenEvaluator(RootPathToken token) {
        this.token = token;
    }

    @Override
    public void evaluate(String currentPath, PathRef pathRef, Object model, EvaluationContextImpl ctx) {
        if (this.token.isLeaf()) {
            PathRef op = ctx.forUpdate() ?  pathRef : PathRef.NO_OP;
            ctx.addResult(this.token.getRootToken(), op, model);
        } else {
            this.token.next().evaluate(this.token.getRootToken(), pathRef, model, ctx);
        }
    }
}
