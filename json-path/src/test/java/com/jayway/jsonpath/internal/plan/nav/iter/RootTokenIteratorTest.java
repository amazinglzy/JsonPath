package com.jayway.jsonpath.internal.plan.nav.iter;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RootTokenIteratorTest {
    @Test
    public void testCreation() {
        // create a indexContext
        // apply such iterator on such index context
        String str = "{\n" +
                "    \"name\": \"Alice\",\n" +
                "    \"age\": 100\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);
        RootTokenIterator iter = new RootTokenIterator(indexContext);

        assertThat(iter.getNode()).isEqualToIgnoringNullFields(
                new ObjectNode("$", 0, 5, 0, null)
        );
        assertThat(iter.hasNext()).isEqualTo(true);
        iter.next();
        assertThat(iter.hasNext()).isEqualTo(false);
    }
}
