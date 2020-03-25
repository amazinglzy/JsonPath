package com.jayway.jsonpath.internal.eval.nav.iter;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import com.jayway.jsonpath.internal.path.PropertyPathToken;
import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyTokenIteratorTest {
    @Test
    public void testBasic01() {
        String str = "{\n" +
                "    \"name\": \"Alice\",\n" +
                "    \"age\": 100\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);
        RootTokenIterator parIter = new RootTokenIterator(indexContext);
        PropertyPathToken token = new PropertyPathToken(
                new LinkedList<String>(){{
                    add("name");
                }},
                ','
        );
        PropertyTokenIterator iter = new PropertyTokenIterator(parIter, token, indexContext);

        assertThat(iter.getValue().toString()).isEqualTo("Alice");
        assertThat(iter.hasNext()).isEqualTo(false);
    }
}
