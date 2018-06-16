package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ycn.springcloud.service.ComputeClient;

/**
 * 服务消费者
 * 
 * @Package: com.ycn.springcloud.web
 * @author: ycn
 * @date: 2018年5月30日 上午10:38:40
 */
@RestController
public class ConsumerController {

	@Autowired
	private ComputeClient computeClient;
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public Integer add(Integer t1, Integer t2) {
		return computeClient.add(t1, t2);
	}
}
