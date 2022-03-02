package com.lukas.textfileparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;



@SpringBootApplication
public class TextFileParserApplication {

    public static void main(String[] args) {

        SpringApplication.run(TextFileParserApplication.class, args);

        File file = new File("src/test/resources/test.txt");
        File file2 = new File("src/test/resources/test2.txt");
        TextParser textParser = new TextParser();

        textParser.getWordFrequency(Arrays.asList(new File[]{file, file2}));

    }

}
