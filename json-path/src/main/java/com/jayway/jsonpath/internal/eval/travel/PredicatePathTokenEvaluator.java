package com.jayway.jsonpath.internal.eval.travel;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.PredicateContextImpl;
import com.jayway.jsonpath.internal.path.PredicatePathToken;

import static java.lang.String.format;

public class PredicatePathTokenEvaluator implements PathTokenEvaluator {
    private PredicatePathToken token;
    public PredicatePathTokenEvaluator(PredicatePathToken token) {
        this.token = token;
    }

    @Override
    public void evaluate(String currentPath, PathRef ref, Object model, EvaluationContextImpl ctx) {
        if (ctx.jsonProvider().isMap(model)) {
            if (this.accept(model, ctx.rootDocument(), ctx.configuration(), ctx)) {
                PathRef op = ctx.forUpdate() ? ref : PathRef.NO_OP;
                if (this.token.isLeaf()) {
                    ctx.addResult(currentPath, op, model);
                } else {
                    this.token.next().evaluate(currentPath, op, model, ctx);
                }
            }
        } else if (ctx.jsonProvider().isArray(model)){
            int idx = 0;
            Iterable<?> objects = ctx.jsonProvider().toIterable(model);

            for (Object idxModel : objects) {
                if (this.accept(idxModel, ctx.rootDocument(),  ctx.configuration(), ctx)) {
                    this.token.handleArrayIndex(idx, currentPath, model, ctx);
                }
                idx++;
            }
        } else {
            if (this.token.isUpstreamDefinite()) {
                throw new InvalidPathException(format("Filter: %s can not be applied to primitives. Current context is: %s", toString(), model));
            }
        }
    }

    public boolean accept(final Object obj, final Object root, final Configuration configuration, EvaluationContextImpl evaluationContext) {
        Predicate.PredicateContext ctx = new PredicateContextImpl(obj, root, configuration, evaluationContext.documentEvalCache());

        for (Predicate predicate : this.token.getPredicates()) {
            try {
                if (!predicate.apply(ctx)) {
                    return false;
                }
            } catch (InvalidPathException e) {
                return false;
            }
        }
        return true;
    }
}
