package com.jayway.jsonpath.internal.path.evaluate;

import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.path.EvaluationContextImpl;
import com.jayway.jsonpath.internal.path.PropertyPathToken;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.jsonpath.internal.Utils.onlyOneIsTrueNonThrow;

public class PropertyPathTokenEvaluator implements PathTokenEvaluator {
    private PropertyPathToken token;

    public PropertyPathTokenEvaluator(PropertyPathToken token) {
        this.token = token;
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        // Can't assert it in ctor because isLeaf() could be changed later on.
        assert onlyOneIsTrueNonThrow(this.token.singlePropertyCase(), this.token.multiPropertyMergeCase(), this.token.multiPropertyIterationCase());

        if (!ctx.jsonProvider().isMap(model)) {
            if (! this.token.isUpstreamDefinite()) {
                return;
            } else {
                String m = model == null ? "null" : model.getClass().getName();

                throw new PathNotFoundException(String.format(
                        "Expected to find an object with property %s in path %s but found '%s'. " +
                                "This is not a json object according to the JsonProvider: '%s'.",
                        this.token.getPathFragment(), currentPath, m, ctx.configuration().jsonProvider().getClass().getName()));
            }
        }

        if (this.token.singlePropertyCase() || this.token.multiPropertyMergeCase()) {
            this.token.handleObjectProperty(currentPath, model, ctx, this.token.getProperties());
            return;
        }

        assert this.token.multiPropertyIterationCase();
        final List<String> currentlyHandledProperty = new ArrayList<String>(1);
        currentlyHandledProperty.add(null);
        for (final String property : this.token.getProperties()) {
            currentlyHandledProperty.set(0, property);
            this.token.handleObjectProperty(currentPath, model, ctx, currentlyHandledProperty);
        }
    }
}
