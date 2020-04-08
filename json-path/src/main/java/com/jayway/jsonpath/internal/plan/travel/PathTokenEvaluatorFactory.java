package com.jayway.jsonpath.internal.plan.travel;

import com.jayway.jsonpath.internal.path.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PathTokenEvaluatorFactory {
    public static PathTokenEvaluator create(PathToken token) {
        if (token.getClass().equals(RootPathToken.class)) {
            return new RootPathTokenEvaluator((RootPathToken) token);
        } else if ( token.getClass().equals(ArrayPathToken.class) ) {
            return new ArrayPathTokenEvaluator((ArrayPathToken) token);
        } else if (token.getClass().equals(FunctionPathToken.class)) {
            return new FunctionPathTokenEvaluator((FunctionPathToken) token);
        } else if (token.getClass().equals(PredicatePathToken.class)) {
            return new PredicatePathTokenEvaluator((PredicatePathToken) token);
        } else if (token.getClass().equals(PropertyPathToken.class)) {
            return new PropertyPathTokenEvaluator((PropertyPathToken) token);
        } else if (token.getClass().equals(ScanPathToken.class)) {
            return new ScanPathTokenEvaluator((ScanPathToken) token);
        } else if (token.getClass().equals(WildcardPathToken.class)) {
            return new WildcardPathTokenEvaluator((WildcardPathToken) token);
        } else {
            throw new NotImplementedException();
        }
    }
}
