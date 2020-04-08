/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayway.jsonpath.internal.path;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.InvalidPathException;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.plan.travel.EvaluationContextImpl;
import com.jayway.jsonpath.internal.plan.travel.PathTokenEvaluatorFactory;

import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 *
 */
public class PredicatePathToken extends PathToken {


    private final Collection<Predicate> predicates;

    PredicatePathToken(Predicate filter) {
        this.predicates = asList(filter);
    }

    PredicatePathToken(Collection<Predicate> predicates) {
        this.predicates = predicates;
    }

    @Override
    public void evaluate(String currentPath, PathRef ref, Object model, EvaluationContextImpl ctx) {
        PathTokenEvaluatorFactory.create(this).evaluate(
                currentPath,
                ref,
                model,
                ctx
        );
    }

    @Deprecated
    public boolean accept(final Object obj, final Object root, final Configuration configuration, EvaluationContextImpl evaluationContext) {
        Predicate.PredicateContext ctx = new PredicateContextImpl(obj, root, configuration, evaluationContext.documentEvalCache());

        for (Predicate predicate : predicates) {
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

    @Override
    public String getPathFragment() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < predicates.size(); i++){
            if(i != 0){
                sb.append(",");
            }
            sb.append("?");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean isTokenDefinite() {
        return false;
    }

    public Collection<Predicate> getPredicates() {
        return predicates;
    }
}
