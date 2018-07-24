package com.ycn.springcloud.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author ycn
 * @package com.ycn.springcloud.web
 * @ClassName TraceController
 * @Date 2018/6/28 17:28
 */
@RestController
public class TraceController {

    private final Logger logger = LoggerFactory.getLogger(TraceController.class);

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/trace-consumer")
    public String trace(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println(headerNames);
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + "-->" + request.getHeader(name));
        }

        logger.info("<---------------  call trace-consumer  ---------------->");
        return restTemplate.getForObject("http://trace-product/trace-product", String.class);
    }
}
