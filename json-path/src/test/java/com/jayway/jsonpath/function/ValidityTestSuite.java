package com.jayway.jsonpath.function;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class ValidityTestSuite {
    private String sourceFile;
    private String[] paths;
    private String[] expected;
    private ReadContext ctx;

    public ValidityTestSuite(String sourceFile, String[] paths, String[] expected) throws IOException {
        this.sourceFile = sourceFile;
        this.paths = paths;
        this.expected = expected;

        InputStream is = this.getClass().getResourceAsStream("/goessner.json");
        this.ctx = JsonPath.parse(is);
        is.close();
    }

    public void produce() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < this.paths.length; i++) {
            assertThat(ctx.read(this.paths[i]).toString()).isEqualTo(this.expected[i]);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}


