package com.jayway.jsonpath.function;

import com.jayway.jsonpath.internal.Path;
import com.jayway.jsonpath.internal.path.PathCompiler;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class ProfileTest {

    @Test
    public void testGoessner() throws IOException {
        ValidityTestSuite profile = new ValidityTestSuite(
                "goessner.json",
                new String[] {
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
                },
                new String[] {
                        "Herman Melville",
                        "[\"Nigel Rees\",\"Evelyn Waugh\",\"Herman Melville\",\"J. R. R. Tolkien\"]",
                        "[[{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Evelyn Waugh\",\"title\":\"Sword of Honour\",\"price\":12.99},{\"category\":\"fiction\",\"author\":\"Herman Melville\",\"title\":\"Moby Dick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"J. R. R. Tolkien\",\"title\":\"The Lord of the Rings\",\"isbn\":\"0-395-19395-8\",\"price\":22.99}],{\"foo\":\"baz\",\"color\":\"red\",\"price\":19.95,\"foo:bar\":\"fooBar\",\"dot.notation\":\"new\",\"dash-notation\":\"dashes\"}]",
                        "[8.95,12.99,8.99,22.99,19.95]",
                        "[\"Moby Dick\"]",
                        "[\"The Lord of the Rings\"]",
                        "[\"Sayings of the Century\",\"Sword of Honour\"]",
                        "[{\"book\":[{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Evelyn Waugh\",\"title\":\"Sword of Honour\",\"price\":12.99},{\"category\":\"fiction\",\"author\":\"Herman Melville\",\"title\":\"Moby Dick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"J. R. R. Tolkien\",\"title\":\"The Lord of the Rings\",\"isbn\":\"0-395-19395-8\",\"price\":22.99}],\"bicycle\":{\"foo\":\"baz\",\"color\":\"red\",\"price\":19.95,\"foo:bar\":\"fooBar\",\"dot.notation\":\"new\",\"dash-notation\":\"dashes\"}},\"bar\",\"ID\",[{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Evelyn Waugh\",\"title\":\"Sword of Honour\",\"price\":12.99},{\"category\":\"fiction\",\"author\":\"Herman Melville\",\"title\":\"Moby Dick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"J. R. R. Tolkien\",\"title\":\"The Lord of the Rings\",\"isbn\":\"0-395-19395-8\",\"price\":22.99}],{\"foo\":\"baz\",\"color\":\"red\",\"price\":19.95,\"foo:bar\":\"fooBar\",\"dot.notation\":\"new\",\"dash-notation\":\"dashes\"},{\"category\":\"reference\",\"author\":\"Nigel Rees\",\"title\":\"Sayings of the Century\",\"price\":8.95},{\"category\":\"fiction\",\"author\":\"Evelyn Waugh\",\"title\":\"Sword of Honour\",\"price\":12.99},{\"category\":\"fiction\",\"author\":\"Herman Melville\",\"title\":\"Moby Dick\",\"isbn\":\"0-553-21311-3\",\"price\":8.99},{\"category\":\"fiction\",\"author\":\"J. R. R. Tolkien\",\"title\":\"The Lord of the Rings\",\"isbn\":\"0-395-19395-8\",\"price\":22.99},\"reference\",\"Nigel Rees\",\"Sayings of the Century\",8.95,\"fiction\",\"Evelyn Waugh\",\"Sword of Honour\",12.99,\"fiction\",\"Herman Melville\",\"Moby Dick\",\"0-553-21311-3\",8.99,\"fiction\",\"J. R. R. Tolkien\",\"The Lord of the Rings\",\"0-395-19395-8\",22.99,\"baz\",\"red\",19.95,\"fooBar\",\"new\",\"dashes\"]",
                        "[]",
                        "[\"Sayings of the Century\",\"Sword of Honour\"]",
                        "[\"Moby Dick\",\"The Lord of the Rings\"]",
                        "[\"Sword of Honour\",\"Moby Dick\",\"The Lord of the Rings\"]",
                        "[\"Sayings of the Century\",\"Moby Dick\"]",
                });
        System.out.print("Total Time for goessner.json(ms): ");
        profile.produce();
    }

    @Test
    public void testSample() {
        String pathStr = "$.store.book[?(@.category == 'fiction')].title";
        Path path = PathCompiler.compile(pathStr);
        assertThat(path.isRootPath()).isTrue();
    }
}
