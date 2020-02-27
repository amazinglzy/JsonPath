package com.jayway.jsonpath.function;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.jayway.jsonpath.JsonPath.read;

public class Profile {
    private String sourceFile;
    private String[] paths;
    private ReadContext ctx;

    public Profile(String sourceFile, String[] paths) throws IOException {
        this.sourceFile = sourceFile;
        this.paths = paths;
        InputStream is = this.getClass().getResourceAsStream("/goessner.json");
        this.ctx = JsonPath.parse(is);
        is.close();
    }

    public void produce() {
        long begin = System.currentTimeMillis();
        for (String path: this.paths) {
            ctx.read(path);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}


