package com.jayway.jsonpath.internal.plan.operator.join;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.IndexContext;
import com.jayway.jsonpath.internal.index.Indexer;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.plan.operator.p.IndexPropertyScan;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PCJoinTest {
    @Test
    public void testPCJoinSanity01() {
        String str = "{\n" +
                "    \"level3\": {\n" +
                "        \"level2\": {\n" +
                "            \"level1\": 3\n" +
                "        }\n" +
                "    },\n" +
                "    \"level2\": {\n" +
                "        \"level1\": 2,\n" +
                "    },\n" +
                "    \"level1\": 1\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        IndexPropertyScan level1 = new IndexPropertyScan(indexContext, "level1");
        IndexPropertyScan level2 = new IndexPropertyScan(indexContext, "level2");
        IndexPropertyScan level3 = new IndexPropertyScan(indexContext, "level3");

        NodeIterator iter;

        iter = level1.iterator();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(3); iter.next();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(2); iter.next();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(1); iter.next();

        iter = new PCJoin(level2, level1).iterator();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(3); iter.next();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(2); iter.next();

        iter = new PCJoin(new PCJoin(level3, level2), level1).iterator();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(3);
    }

    @Test
    public void testPCJoinSanity02() {
        String str = "{\n" +
                "    \"level3\": {\n" +
                "        \"level2\": {\n" +
                "            \"level1\": 3\n" +
                "        }\n" +
                "    },\n" +
                "    \"level1\": 1,\n" +
                "    \"level2\": {\n" +
                "        \"level1\": 2,\n" +
                "    }\n" +
                "}";
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);

        IndexPropertyScan level1 = new IndexPropertyScan(indexContext, "level1");
        IndexPropertyScan level2 = new IndexPropertyScan(indexContext, "level2");
        IndexPropertyScan level3 = new IndexPropertyScan(indexContext, "level3");

        NodeIterator iter;

        iter = level1.iterator();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(3); iter.next();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(1); iter.next();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(2); iter.next();

        iter = new PCJoin(level2, level1).iterator();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(3); iter.next();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(2); iter.next();

        iter = new PCJoin(new PCJoin(level3, level2), level1).iterator();
        assertThat(iter.hasNext()).isTrue(); assertThat(iter.read().getValue()).isEqualTo(3);
    }
}
