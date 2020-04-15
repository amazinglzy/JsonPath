package com.jayway.jsonpath.internal.plan.operator.p;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.plan.operator.p.IndexPropertyScan;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexPropertyScanTest {
    @Test
    public void testSanity01() {
        String str = "{\n" +
                "    \"p\": 1,\n" +
                "    \"q\": 2\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();

        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);
        List<String> properties = new LinkedList<String>(){{
            add("p");
            add("q");
        }};

        IndexPropertyScan scan = new IndexPropertyScan(indexContext, properties);
        NodeIterator iter = scan.iterator();

        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.read().getValue()).isEqualTo(1);
        iter.next();
        assertThat(iter.read().getValue()).isEqualTo(2);
    }
}
