package com.ycn.springcloud.service;

import com.ycn.springcloud.config.MultipartSupportConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "eureka-feign", configuration = MultipartSupportConfig.class)
public interface UploadService {

    @PostMapping(value = "uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleFileUpload(@RequestPart(value = "file") MultipartFile file);
}
