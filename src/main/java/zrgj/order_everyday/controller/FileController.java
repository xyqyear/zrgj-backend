package zrgj.order_everyday.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import zrgj.order_everyday.pojo.dto.ResultMap;
import zrgj.order_everyday.service.FileService;
import zrgj.order_everyday.util.ResponseWrapper;

@Controller
public class FileController {
    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) {
        // get file extension from file
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        try {
            return ResponseWrapper.wrap(FileService.saveFile(file.getBytes(), fileExtension));
        } catch (IOException e) {
            return ResponseWrapper.wrap(ResultMap.failure("upload failed: " + e.getMessage()));
        }
    }
}
