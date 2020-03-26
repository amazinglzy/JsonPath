package com.jayway.jsonpath.internal.index.node;

import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class TestNodeStream {
    @Test
    public void testSingleNodeIteratorSanity() {
        LinkedList<Node> l = new LinkedList<Node>() {{
            add(new ArrayNode(0, 0, 10, 1, null));
            add(new ArrayNode(0, 1, 5, 2, null));
        }};

        NodeIterator iter = new SingleNodeIterator(l);
        iter.next();
        assertThat(iter.peek()).isEqualByComparingTo(new ArrayNode(0, 0, 10, 1, null));
        iter.next();
        assertThat(iter.peek()).isEqualByComparingTo(new ArrayNode(0, 1, 5, 2, null));
        assertThat(iter.hasNext()).isEqualTo(false);
    }

    @Test
    public void testCombinedNodeIteratorSanity() {
        LinkedList<Node> l1 = new LinkedList<Node>() {{
            add(new ArrayNode(0, 0, 10, 1, null));
            add(new ArrayNode(0, 1, 5, 2, null));
        }};
        LinkedList<Node> l2 = new LinkedList<Node>() {{
            add(new ArrayNode(1, 6, 7, 2, null));
            add(new ArrayNode(1, 7, 8, 2, null));
        }};
        NodeIterator iter = new CombinedNodeIterator(new SingleNodeIterator(l1), new SingleNodeIterator(l2));
        iter.next();
        assertThat(iter.peek()).isEqualByComparingTo(new ArrayNode(0, 0, 10, 1, null));
        iter.next();
        assertThat(iter.peek()).isEqualByComparingTo(new ArrayNode(0, 1, 5, 2, null));
        iter.next();
        assertThat(iter.peek()).isEqualByComparingTo(new ArrayNode(1, 6, 7, 2, null));
        assertThat(iter.hasNext()).isEqualTo(true);
        iter.next();
        assertThat(iter.peek()).isEqualByComparingTo(new ArrayNode(1, 7, 8, 2, null));
        assertThat(iter.hasNext()).isEqualTo(false);
    }
}
