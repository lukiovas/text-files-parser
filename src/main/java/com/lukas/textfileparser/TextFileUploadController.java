package com.lukas.textfileparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TextFileUploadController {

    private static String UPLOADED_FOLDER = "src/main/resources/textFiles";

    TextParser textParser;

    @Autowired
    public TextFileUploadController(TextParser textParser) {
        this.textParser = textParser;
    }

    @PostMapping(value = "/upload")
    public String ParseTextFiles(@RequestParam("textFiles") MultipartFile[] textFiles) {

//        for (int i = 0; i < textFiles.length; i++ ) {
//            if (textFiles[i].isEmpty()) {
//                return "File empty";
//            }
//            try {
//                // Get the file and save it somewhere
//                byte[] bytes = textFiles[i].getBytes();
//                Path path = Paths.get(UPLOADED_FOLDER + textFiles[i].getOriginalFilename());
//                Files.write(path, bytes);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return textParser.getWordFrequencyMaps(Arrays.asList(textFiles)).toString();
    }
}

