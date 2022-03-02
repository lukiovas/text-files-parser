package com.lukas.textfileparser;

import org.springframework.stereotype.Component;

import java.io.*;


@Component("textFileReader")
public class TextFileReader {

    public TextFileReader() {
    }

    String readFromFile(File file)
            throws IOException {
        InputStream inputStream = new FileInputStream(file);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        }
        return resultStringBuilder.toString();
    }

}
