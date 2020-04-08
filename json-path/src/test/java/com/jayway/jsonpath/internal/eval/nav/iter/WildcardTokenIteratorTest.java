package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.path.PathTokenFactory;
import com.jayway.jsonpath.internal.path.WildcardPathToken;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WildcardTokenIteratorTest {
    @Test
    public void testBasic01() {
        String str = "{\n" +
                "    \"a\": [1, 2, 3],\n" +
                "    \"b\": [2, 3, 4]\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        RootTokenIterator parIter = new RootTokenIterator(indexContext);
        WildcardPathToken token = (WildcardPathToken) PathTokenFactory.createWildCardPathToken();
        WildcardPathTokenIterator iter = new WildcardPathTokenIterator(parIter, token, indexContext);

        assertThat(iter.hasNext()).isEqualTo(true);
        assertThat(iter.getValue()).isEqualTo(configuration.jsonProvider().parse("[1,2,3]"));
        iter.next();
        assertThat(iter.getValue()).isEqualTo(configuration.jsonProvider().parse("[2,3,4]"));
    }

    @Test
    public void testBasic02() {
        String str = "{\n" +
                "    \"a\": [1, 2, 3],\n" +
                "    \"b\": [2, 3, 4]\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        RootTokenIterator ppIter = new RootTokenIterator(indexContext);
        WildcardPathToken pToken = (WildcardPathToken) PathTokenFactory.createWildCardPathToken();
        WildcardPathToken token = (WildcardPathToken) PathTokenFactory.createWildCardPathToken();
        WildcardPathTokenIterator pIter = new WildcardPathTokenIterator(ppIter, pToken, indexContext);
        WildcardPathTokenIterator iter = new WildcardPathTokenIterator(pIter, token, indexContext);

        assertThat(iter.hasNext()).isEqualTo(true);
        assertThat(iter.getValue()).isEqualTo(1);
        iter.next();
        assertThat(iter.getValue()).isEqualTo(2);
        iter.next();
        assertThat(iter.getValue()).isEqualTo(3);
        iter.next();
        assertThat(iter.getValue()).isEqualTo(2);
        iter.next();
        assertThat(iter.getValue()).isEqualTo(3);
        iter.next();
        assertThat(iter.getValue()).isEqualTo(4);

    }
}
