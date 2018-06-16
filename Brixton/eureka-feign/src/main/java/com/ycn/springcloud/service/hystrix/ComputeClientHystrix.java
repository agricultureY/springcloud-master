package com.ycn.springcloud.service.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.ycn.springcloud.service.ComputeClient;

@Component
public class ComputeClientHystrix implements ComputeClient {

	@Override
	public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
		return -999999999;
	}

}
