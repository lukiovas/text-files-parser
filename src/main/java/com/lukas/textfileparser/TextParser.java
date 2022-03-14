package com.lukas.textfileparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TextParser {

    private TextFileReader textFileReader;

    @Autowired
    public TextParser(TextFileReader textFileReader) {
        this.textFileReader = textFileReader;
    }


    protected Map<String,Map<String,Long>> getoutputMap(List<MultipartFile> filesList) {

        List <String[]> stringsArrayList = new ArrayList<>();

        filesList.stream().forEach(
                f -> {
                    try {
                        String s = textFileReader.readFromFile(f);
                        String[] words = s.replaceAll("[^\\w]", " ").split("\\s+");
                        if (!words[0].equals("")) {
                            stringsArrayList.add(words);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        Map<String,Map<String,Long>> outputMap = new HashMap<>();

        outputMap.put("Words and count of chars from A to G",
                getSubMap(stringsArrayList,'a','g'));
        outputMap.put("Words and count of chars from H to N",
                getSubMap(stringsArrayList,'h','n'));
        outputMap.put("Words and count of chars from O to U",
                getSubMap(stringsArrayList,'o','u'));
        outputMap.put("Words and count of chars from V to Z",
                getSubMap(stringsArrayList,'v','z'));

        // return sorted map
        return outputMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private Map<String,Long> getSubMap (List<String[]> stringsArrayList, char fromChar, char toChar) {
        return getSortedMap(
                listToCountMap(
                        listToSubList(
                                getStringList(stringsArrayList), fromChar, toChar)
                ));
    }

    private List<String> getStringList (List <String[]> stringsList) {

        String[] words = stringsList.stream()
                .flatMap(Stream::of)
                .toArray(String[]::new);
        return Arrays.asList(words);
    }




    private List<String> listToSubList (List<String> wordsList, char charFrom, char charTo) {
        return wordsList.stream()
                .filter( word ->
                        word.toLowerCase().charAt(0) >= charFrom
                                && word.toLowerCase().charAt(0) <= charTo
                ).collect(Collectors.toList());
    }

    private Map<String,Long> listToCountMap(List<String> subList) {
        return subList.stream().collect(Collectors.groupingBy(k -> k, Collectors.counting()));

    }

    private Map <String, Long> getSortedMap (Map<String, Long> unsortedMap) {

        Map<String, Long> sortedWordsCountMap = unsortedMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //sorting
                .collect(Collectors.toMap( // collecting to linkedHashMap
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return sortedWordsCountMap;
    }

}
