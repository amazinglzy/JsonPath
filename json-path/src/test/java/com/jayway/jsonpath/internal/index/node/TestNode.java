package com.jayway.jsonpath.internal.index.node;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestNode {

    @Test
    public void TestSanity() {
        Node a = new ArrayNode(0, 1, 20, 2, null);
        Node b = new ObjectNode("label", 2, 19, 1, "df");
        assertThat(a.getFirstVisit()).isEqualTo(1);
        assertThat(a.getLastVisit()).isEqualTo(20);
        assertThat(b.getLevel()).isEqualTo(1);
        assertThat(b.getValue()).isEqualTo("df");
    }

    @Test
    public void TestComparable() {
        Node a = new ArrayNode(0, 1, 20, 2, null);
        Node b = new ObjectNode("label", 2, 19, 1, "df");
        assertThat(a.compareTo(b)).isLessThan(0);
    }
}
