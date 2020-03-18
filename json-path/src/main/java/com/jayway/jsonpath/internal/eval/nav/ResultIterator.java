package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.index.IndexContext;

public class ResultIterator {
    private Path path;
    private IndexContext indexContext;

    public ResultIterator(Path path, IndexContext indexContext) {
        this.path = path;
        this.indexContext = indexContext;
    }

    public Object next() {
        return null;
    }

    public boolean hasNext() {
        return false;
    }
}
