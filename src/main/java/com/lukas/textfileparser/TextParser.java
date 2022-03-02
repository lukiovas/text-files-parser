package com.lukas.textfileparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TextParser {

    @Autowired
    private TextFileReader textFileReader = new TextFileReader();



    public Map <String, Long> getWordFrequency(List<File> filesList) {
        List <String> wordsList = new ArrayList<>();
        filesList.parallelStream().forEach(
                f -> {
                    try {

                        String s = textFileReader.readFromFile(f);
                        System.out.println("string read: " + s);
                        String[] words = s.split(" ");

                        System.out.println("file: " + f.getName());
                        wordsList.addAll(List.of(words));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        Map <String, Long> wordsFrequencyMap =wordsList.stream()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

        for (Map.Entry<String, Long> entry: wordsFrequencyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return wordsFrequencyMap;
    }
}
