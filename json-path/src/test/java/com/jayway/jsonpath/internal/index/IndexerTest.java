package com.jayway.jsonpath.internal.index;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.internal.index.node.ArrayNode;
import com.jayway.jsonpath.internal.index.node.NodeIterator;
import com.jayway.jsonpath.internal.index.node.ObjectNode;
import com.jayway.jsonpath.internal.path.ArrayIndexOperation;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexerTest {
    @Test
    public void testIndexerSanity() {
        String str =
                "{\n" +
                        "    \"a\": {\n" +
                        "        \"b\": {\n" +
                        "            \"c\": \"value\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"d\": [\n" +
                        "        1,\n" +
                        "        2,\n" +
                        "        3\n" +
                        "    ]\n" +
                        "}";
        /*
        $   0   15  0
        a   1   6   1
        b   2   5   2
        c   3   4   3
        d   7   14   1
        0   8   9   2
        1   10   11  2
        2   12  13  2
         */
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);
        assertThat(indexContext.openArray(new Long(1)).peek()).isEqualToIgnoringNullFields(
                new ArrayNode(1, 10, 11, 2, 2)
        );
        assertThat(indexContext.openObject("$").peek()).isEqualToIgnoringNullFields(
                new ObjectNode("$", 0, 15, 0, null)
        );
        assertThat(indexContext.openArray(new Long(2)).peek()).isEqualToIgnoringNullFields(
                new ArrayNode(2, 12, 13, 2, 3)
        );
    }

    @Test
    public void testIndexerOrderOfIndexContext() {
        String str = "{\n" +
                "    \"a\": {\n" +
                "        \"a\": {\n" +
                "            \"b\": \"value\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"b\": 2,\n" +
                "}";
        /*
        $   0   9   0
        a   1   6   1
        a   2   5   2
        b   3   4   3
        b   7   8   1
         */
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);
        NodeIterator iter = indexContext.openObject("a");
        assertThat(iter.peek()).isEqualToIgnoringNullFields(
                new ObjectNode("a", 1, 6, 1, null)
        );
        iter.next();
        assertThat(iter.peek()).isEqualToIgnoringNullFields(
                new ObjectNode("a", 2, 5, 2, null)
        );
    }

    @Test
    public void testIndexContextOpenArrayOperation() {
        String str =
                "{\n" +
                        "    \"a\": {\n" +
                        "        \"b\": {\n" +
                        "            \"c\": \"value\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"d\": [\n" +
                        "        1,\n" +
                        "        2,\n" +
                        "        3\n" +
                        "    ]\n" +
                        "}";
        /*
        $   0   15  0
        a   1   6   1
        b   2   5   2
        c   3   4   3
        d   7   14   1
        0   8   9   2
        1   10   11  2
        2   12  13  2
         */
        Configuration configuration = Configuration.defaultConfiguration();
        IndexContext indexContext = Indexer.index(configuration.jsonProvider().parse(str), configuration);
        ArrayIndexOperation operation = ArrayIndexOperation.parse("0,1");
        NodeIterator iter = indexContext.openArray(operation);
        assertThat(iter.peek()).isEqualToIgnoringNullFields(
                new ArrayNode(0, 8, 9, 2, null)
        );
        assertThat(iter.hasNext());
        iter.next();
        assertThat(iter.peek()).isEqualToIgnoringNullFields(
                new ArrayNode(1, 10, 11, 2, null)
        );
    }
}
