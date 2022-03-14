package com.lukas.textfileparser;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TextFileUploadController {

    TextParser textParser;

    @Autowired
    public TextFileUploadController(TextParser textParser) {
        this.textParser = textParser;
    }

    @PostMapping(value = "/upload")
    public Map<String,Map<String,Long>> uploadTextFiles(@RequestParam("textFiles") MultipartFile[] textFiles) {
        //parallel reikia
        boolean isTextFile = Arrays.stream(textFiles).allMatch(
                file -> FilenameUtils.getExtension(file.getOriginalFilename()).equals("txt"));
        if (isTextFile) {
            return textParser.getoutputMap(Arrays.asList(textFiles));
        }
        else {
            Map<String,Map<String,Long>> emptyMap = new HashMap<>();
            emptyMap.put("Provided file is not of type txt.", new HashMap<>());
            return emptyMap;
        }
    }
}

