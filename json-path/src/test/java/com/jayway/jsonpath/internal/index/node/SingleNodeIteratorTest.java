package com.jayway.jsonpath.internal.index.node;

import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleNodeIteratorTest {
    @Test
    public void testSingleNodeIteratorSanity() {
        LinkedList<Node> l = new LinkedList<Node>() {{
            add(new ArrayNode(0, 0, 10, 1, null));
            add(new ArrayNode(0, 1, 5, 2, null));
        }};

        NodeIterator iter = new SingleNodeIterator(l);
        assertThat(iter.read()).isEqualToIgnoringNullFields(new ArrayNode(0, 0, 10, 1, null));
        iter.next();
        assertThat(iter.read()).isEqualToIgnoringNullFields(new ArrayNode(0, 1, 5, 2, null));
        iter.next();
        assertThat(iter.hasNext()).isEqualTo(false);
    }

    @Test
    public void testSingleNodeIteratorClone() {
        LinkedList<Node> l = new LinkedList<Node>() {{
            add(new ArrayNode(0, 0, 10, 1, null));
            add(new ArrayNode(0, 1, 5, 2, null));
        }};

        NodeIterator iter = new SingleNodeIterator(l);

        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.read()).isEqualToIgnoringNullFields(new ArrayNode(0, 0, 10, 1, null));

        NodeIterator iterCopy = iter.cloneCurrentIterator();

        iter.next();
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.read()).isEqualToIgnoringNullFields(new ArrayNode(0, 1, 5, 2, null));
        assertThat(iterCopy.read()).isEqualToIgnoringNullFields(new ArrayNode(0, 0, 10, 1, null));
    }
}
