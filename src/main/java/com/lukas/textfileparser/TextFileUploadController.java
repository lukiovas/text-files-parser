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
            Map<String,Map<String,Long>> emptyMap = new ConcurrentHashMap<>();
            emptyMap.put("Provided file is not of type txt.", new ConcurrentHashMap<>());
            return emptyMap;
        }
    }

//    @GetMapping(value = "/files/{file_name}")
//    public void getFile(
//            @PathVariable("file_name") String fileName,
//            HttpServletResponse response) {
//        try {
//            // get your file as InputStream
//            InputStream is = ...;
//            // copy it to response's OutputStream
//            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException ex) {
//            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
//            throw new RuntimeException("IOError writing file to output stream");
//        }
//    }
}

