package com.jayway.jsonpath.internal.eval.nav;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.path.PathCompiler;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultIteratorFactoryTest {
    @Test
    public void testBasic01() {
        String str = "{\n" +
                "    \"students\": \"alice\"\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        Path path = PathCompiler.compile("$.students");
        ResultIterator iter = ResultIteratorFactory.create(path, indexContext);
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.getValue()).isEqualTo("alice");
        iter.next();
        assertThat(iter.hasNext()).isFalse();
    }

    @Test
    public void testBasic02() {
        String str = "{\n" +
                "    \"a\": [\n" +
                "        {\"b\": \"df\"}\n" +
                "    ]\n" +
                "}";

        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        Path path = PathCompiler.compile("$.a[*].b");
        ResultIterator iter = ResultIteratorFactory.create(path, indexContext);
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.getValue()).isEqualTo("df");
        iter.next();
        assertThat(iter.hasNext()).isFalse();
    }
}
