package com.ycn.springcloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/goUploadImg")
    public String goUploadImg() {
        return "uploadimg";
    }
}
