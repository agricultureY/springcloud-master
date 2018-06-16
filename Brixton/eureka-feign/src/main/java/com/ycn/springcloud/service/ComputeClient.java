package com.ycn.springcloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ycn.springcloud.service.hystrix.ComputeClientHystrix;

/**
 * 
 * @Package: com.ycn.springcloud.service
 * @author: ycn
 * @date: 2018年5月30日 上午10:32:20
 */
@FeignClient(value = "compute-server", fallback = ComputeClientHystrix.class)//绑定该接口对应compute-service服务,指定对应的回调类
public interface ComputeClient {

	/**
	 * 通过springmvc注解调用服务
	 * @param a
	 * @param b
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
}
