package com.ycn.springcloud;

import com.ycn.springcloud.service.UploadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EurekaFeignUploadClientApplicationTests {

    @Autowired
    private UploadService uploadService;

    @Test
    @SneakyThrows
    public void contextLoads() {
        File file = new File("upload.txt");
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file", MediaType.TEXT_PLAIN_VALUE, true, file.getName());
        try (InputStream input = new FileInputStream(file); OutputStream out = fileItem.getOutputStream()) {
            IOUtils.copy(input, out);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        System.out.println(uploadService.handleFileUpload(multipartFile));
    }

}
