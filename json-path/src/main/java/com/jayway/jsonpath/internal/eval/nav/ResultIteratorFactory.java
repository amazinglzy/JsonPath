package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.eval.nav.iter.*;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.path.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ResultIteratorFactory {

    public static ResultIterator create(Path path, IndexContext indexContext) {
        return create(null, path.getRootToken(), indexContext);
    }

    private static ResultIterator create(ResultIterator iter, PathToken token, IndexContext indexContext) {
        if (token.isLeaf()) {
            return createIterator(iter, token, indexContext);
        }
        return create(
                createIterator(iter, token, indexContext),
                token.next(),
                indexContext
        );
    }

    private static ResultIterator createIterator(ResultIterator iter, PathToken token, IndexContext indexContext) {
        if (token instanceof ArrayPathToken)
            return new ArrayPathTokenIterator(iter, (ArrayPathToken) token, indexContext);
        if (token instanceof PropertyPathToken)
            return new PropertyTokenIterator(iter, (PropertyPathToken) token, indexContext);
        if (token instanceof RootPathToken)
            return new RootTokenIterator(indexContext);
        if (token instanceof WildcardPathToken)
            return new WildcardPathTokenIterator(iter, (WildcardPathToken)token, indexContext);
        throw new NotImplementedException();
    }
}
