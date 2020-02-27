package com.jayway.jsonpath.function;

import org.junit.Test;

import java.io.IOException;

public class ProfileTest {

    @Test
    public void testGoessner() throws IOException {
        Profile profile = new Profile("goessner.json", new String[] {
                "$.store.book[2].author",
                "$..author",
                "$.store.*",
                "$.store..price",
                "$..book[2].title",
                "$..book[-1:].title",
                "$..book[:2].title",
                "$..*",
                "$.store.book[*].niçôlàs['nico']['foo'][*].bar[1:-2:3]",
                "$.store['book'][:2].title",
                "$.store.book[?(@.isbn)].title",
                "$.store.book[?(@.category == 'fiction')].title",
                "$.store.book[?(@.price < 10 && @.price >4)].title"
        });
        System.out.print("Total Time for goessner.json(ms): ");
        profile.produce();
    }
}
