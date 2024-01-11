package com.ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {
    @Value("${path-upload}")
    private String pathUpload;
    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }
    @PostMapping("/upload")
    public String load(@RequestParam("img")MultipartFile file){
        //xử lý load
        String fileName = file.getOriginalFilename();

        try {
            FileCopyUtils.copy(file.getBytes(),new File(pathUpload+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
