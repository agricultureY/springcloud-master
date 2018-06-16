package com.ycn.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * 
 * 
 * @Package: com.ycn.springcloud.service
 * @author: ycn
 * @date: 2018年5月30日 下午2:11:28
 */
@Service
public class ComputeService {

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * @HystrixCommand指定回调方法
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "addServiceFallback")
	public String addService() {
		return restTemplate.getForEntity("http://COMPUTE-SERVER/add?a=10&b=30", String.class).getBody();
	}
	
	public String addServiceFallback() {
		return "server error!";
	}
}
