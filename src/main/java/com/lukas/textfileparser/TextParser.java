package com.lukas.textfileparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TextParser {

    private TextFileReader textFileReader;

    @Autowired
    public TextParser(TextFileReader textFileReader) {
        this.textFileReader = textFileReader;
    }


    public List <Map<String, Long>> getWordFrequencyMaps(List<MultipartFile> filesList) {
        List<List<String>> charFrameLists = new ArrayList<>();

        filesList.parallelStream().forEach(
                f -> {
                    try {
                        String s = textFileReader.readFromFile(f);
                        String[] words = s.replaceAll("[^a-zA-Z0-9]", "").split(" ");
                        if (!words[0].equals("")) {
                            charFrameLists.addAll(splitArrayIntoLists(words));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        List<Map<String, Long>> listOfMaps = new ArrayList<>();
        charFrameLists.forEach(l -> listOfMaps.add(turnListIntoSortedMap(l)));
        return listOfMaps;
    }

    private Map <String, Long> turnListIntoSortedMap (List<String> list) {
        return list.stream()
                .collect(Collectors
                        .groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors
                        .toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private List<List<String>> splitArrayIntoLists(String[] words) {

        List<String> wordsListA_G = new ArrayList<>();
        List<String> wordsListH_N = new ArrayList<>();
        List<String> wordsListO_U = new ArrayList<>();
        List<String> wordsListV_Z = new ArrayList<>();

        if(words.length > 0) {
            System.out.println(words[0]);
            for (int i = 0; i<words.length; i++) {
                char c = words[i].toLowerCase().charAt(0);
                if (c>=97 && c<=103) {
                    wordsListA_G.add(words[i]);
                } else if (c>=104 && c<=110) {
                    wordsListH_N.add(words[i]);
                } else if (c>=111 && c<=117) {
                    wordsListO_U.add(words[i]);
                } else if (c>=118 && c<=122) {
                    wordsListV_Z.add(words[i]);
                }
            }
        }
        return List.of(wordsListA_G, wordsListH_N, wordsListO_U, wordsListV_Z);
    }
}
