package com.jayway.jsonpath.internal.path;

import com.jayway.jsonpath.internal.PathRef;
import com.jayway.jsonpath.internal.function.Parameter;
import com.jayway.jsonpath.internal.function.PathFunction;
import com.jayway.jsonpath.internal.function.PathFunctionFactory;
import com.jayway.jsonpath.internal.path.evaluate.FunctionPathTokenEvaluator;

import java.util.List;

/**
 * Token representing a Function call to one of the functions produced via the FunctionFactory
 *
 * @see PathFunctionFactory
 *
 * Created by mattg on 6/27/15.
 */
public class FunctionPathToken extends PathToken {

    private final String functionName;
    private final String pathFragment;
    private final List<Parameter> functionParams;

    public FunctionPathToken(String pathFragment, List<Parameter> parameters) {
        this.pathFragment = pathFragment + ((parameters != null && parameters.size() > 0) ? "(...)" : "()");
        if(null != pathFragment){
            functionName = pathFragment;
            functionParams = parameters;
        } else {
            functionName = null;
            functionParams = null;
        }
    }

    @Override
    public void evaluate(String currentPath, PathRef parent, Object model, EvaluationContextImpl ctx) {
        new FunctionPathTokenEvaluator(this).evaluate(
                currentPath,
                parent,
                model,
                ctx
        );
    }

    /**
     * Return the actual value by indicating true. If this return was false then we'd return the value in an array which
     * isn't what is desired - true indicates the raw value is returned.
     *
     * @return
     */
    @Override
    public boolean isTokenDefinite() {
        return true;
    }

    @Override
    public String getPathFragment() {
        return "." + pathFragment;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Parameter> getFunctionParams() {
        return functionParams;
    }
}
