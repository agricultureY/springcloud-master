package com.ycn.springcloud.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
public class UploadController {

    @PostMapping(value = "uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestPart(value = "file") MultipartFile file) throws IOException {
        String path = "E:/tmp/" + new Date().getTime() + "/";
        File tmpFile = new File(path);
        //创建文件路径
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        path += file.getOriginalFilename();
        File newFile = new File(path);
        //通过CommonsMultipartFile的方法直接写文件
        file.transferTo(newFile);
        return file.getOriginalFilename();
    }
}
