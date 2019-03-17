package com.rav.integration;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Transformer {
    public String transform(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path))).toUpperCase();
    }
}
