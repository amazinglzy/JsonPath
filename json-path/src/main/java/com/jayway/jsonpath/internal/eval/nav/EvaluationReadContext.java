package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.internal.EvaluationContext;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.PathRef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.jayway.jsonpath.internal.Utils.notNull;

public class EvaluationReadContext implements EvaluationContext {
    private final Path path;
    private final Object valueResult;
    private final Object pathResult;
    private final Object rootDocument;
    private final Configuration configuration;
    private int resultIndex = 0;

    public EvaluationReadContext(Path path, Object rootDocument, Configuration configuration) {
        notNull(path, "path can not be null");
        notNull(rootDocument, "root can not be null");
        notNull(configuration, "configuration can not be null");
        this.path = path;
        this.rootDocument = rootDocument;
        this.configuration = configuration;
        this.valueResult = configuration.jsonProvider().createArray();
        this.pathResult = configuration.jsonProvider().createArray();
    }

    public void addResult(String path, Object model) {
        configuration.jsonProvider().setArrayIndex(valueResult, resultIndex, model);
        configuration.jsonProvider().setArrayIndex(pathResult, resultIndex, path);
        resultIndex++;
        // may be some logic for evaluation listener
    }

    @Override
    public Configuration configuration() {
        return this.configuration;
    }

    @Override
    public Object rootDocument() {
        return this.rootDocument;
    }

    @Override
    public <T> T getValue() {
        return this.getValue(true);
    }

    @Override
    public <T> T getValue(boolean unwrap) {
        if (path.isDefinite()) {
            if(resultIndex == 0){
                throw new PathNotFoundException("No results for path: " + path.toString());
            }
            int len = this.configuration.jsonProvider().length(valueResult);
            Object value = (len > 0) ? this.configuration.jsonProvider().getArrayIndex(valueResult, len-1) : null;
            if (value != null && unwrap){
                value = this.configuration.jsonProvider().unwrap(value);
            }
            return (T) value;
        }
        return (T)valueResult;
    }

    @Override
    public <T> T getPath() {
        if(resultIndex == 0){
            throw new PathNotFoundException("No results for path: " + path.toString());
        }
        return (T)pathResult;
    }

    @Override
    public List<String> getPathList() {
        List<String> res = new ArrayList<String>();
        if(resultIndex > 0){
            Iterable<?> objects = configuration.jsonProvider().toIterable(pathResult);
            for (Object o : objects) {
                res.add((String)o);
            }
        }
        return res;
    }

    @Override
    public Collection<PathRef> updateOperations() {
        return null;
    }
}
