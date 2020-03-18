package com.jayway.jsonpath.internal.eval.travel;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.*;
import com.jayway.jsonpath.spi.json.JsonProvider;

import java.util.Collection;

public class ScanPathTokenEvaluator implements PathTokenEvaluator {
    private ScanPathToken token;

    public ScanPathTokenEvaluator(ScanPathToken token) {
        this.token = token;
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {

        PathToken pt = this.token.next();

        walk(pt, currentPath, parent,  model, ctx, createScanPredicate(pt, ctx));
    }

    public static void walk(PathToken pt, String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx, ScanPathToken.Predicate predicate) {
        if (ctx.jsonProvider().isMap(model)) {
            walkObject(pt, currentPath, parent, model, ctx, predicate);
        } else if (ctx.jsonProvider().isArray(model)) {
            walkArray(pt, currentPath, parent, model, ctx, predicate);
        }
    }

    public static void walkArray(PathToken pt, String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx, ScanPathToken.Predicate predicate) {

        if (predicate.matches(model)) {
            if (pt.isLeaf()) {
                pt.evaluate(currentPath, parent, model, ctx);
            } else {
                PathToken next = pt.next();
                Iterable<?> models = ctx.jsonProvider().toIterable(model);
                int idx = 0;
                for (Object evalModel : models) {
                    String evalPath = currentPath + "[" + idx + "]";
                    next.evaluate(evalPath, parent, evalModel, ctx);
                    idx++;
                }
            }
        }

        Iterable<?> models = ctx.jsonProvider().toIterable(model);
        int idx = 0;
        for (Object evalModel : models) {
            String evalPath = currentPath + "[" + idx + "]";
            walk(pt, evalPath, PathRef.create(model, idx), evalModel, ctx, predicate);
            idx++;
        }
    }

    public static void walkObject(PathToken pt, String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx, ScanPathToken.Predicate predicate) {

        if (predicate.matches(model)) {
            pt.evaluate(currentPath, parent, model, ctx);
        }
        Collection<String> properties = ctx.jsonProvider().getPropertyKeys(model);

        for (String property : properties) {
            String evalPath = currentPath + "['" + property + "']";
            Object propertyModel = ctx.jsonProvider().getMapValue(model, property);
            if (propertyModel != JsonProvider.UNDEFINED) {
                walk(pt, evalPath, PathRef.create(model, property), propertyModel, ctx, predicate);
            }
        }
    }

    public static ScanPathToken.Predicate createScanPredicate(final PathToken target, final EvaluationContextImpl ctx) {
        if (target instanceof PropertyPathToken) {
            return new ScanPathToken.PropertyPathTokenPredicate(target, ctx);
        } else if (target instanceof ArrayPathToken) {
            return new ScanPathToken.ArrayPathTokenPredicate(ctx);
        } else if (target instanceof WildcardPathToken) {
            return new ScanPathToken.WildcardPathTokenPredicate();
        } else if (target instanceof PredicatePathToken) {
            return new ScanPathToken.FilterPathTokenPredicate(target, ctx);
        } else {
            return ScanPathToken.FALSE_PREDICATE;
        }
    }
}
