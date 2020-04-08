package com.jayway.jsonpath.internal.plan.nav.iter;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.path.ArrayIndexOperation;
import com.jayway.jsonpath.internal.path.ArrayPathToken;
import com.jayway.jsonpath.internal.path.PathTokenFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayTokenIteratorTest {

    @Test
    public void testBasic01() {
        String str = "[1, 2, 3]";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        RootTokenIterator parIter = new RootTokenIterator(indexContext);
        ArrayIndexOperation operation = ArrayIndexOperation.parse("0,1");
        ArrayPathToken token = (ArrayPathToken)PathTokenFactory.createIndexArrayPathToken(operation);
        ArrayPathTokenIterator iter = new ArrayPathTokenIterator(parIter, token, indexContext);

        assertThat((Integer) iter.getValue()).isEqualTo(1);
        assertThat(iter.hasNext()).isEqualTo(true);
        iter.next();
        assertThat((Integer) iter.getValue()).isEqualTo(2);
    }

}
