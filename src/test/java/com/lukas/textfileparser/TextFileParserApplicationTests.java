package com.lukas.textfileparser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TextFileParserApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    public void readTxtFileTest() throws IOException {
//        String expectedData = "This is a test text file";
//        File file = new File("src/test/resources/test.txt");
//        TextFileReader textFileReader = new TextFileReader();
//        InputStream inputStream = null;
//        String data;
//        try {
//            data = textFileReader.readFromFile(file);
//        }
//        finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        assertEquals(data, expectedData);
//    }

}
