package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ycn.springcloud.service.ComputeService;

/**
 * 消费COMPUTE-SERVICE的add服务；通过直接RestTemplate来调用服务
 * 
 * @Package: com.ycn.springcloud.web
 * @author: ycn
 * @date: 2018年5月30日 上午10:03:17
 */
@RestController
public class ConsumerController {

    
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ComputeService computeService;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return restTemplate.getForEntity("http://COMPUTE-SERVER/add?a=10&b=30", String.class).getBody();
	}
	
	@RequestMapping(value = "/addWithHystrix", method = RequestMethod.GET)
	public String addWithHystrix() {
		return computeService.addService();
	}
    
    @RequestMapping(value = "/addWithServerId" ,method = RequestMethod.GET)
    public String addWithServerId(@RequestParam Integer a, @RequestParam Integer b) {
    	Integer r = a + b;
        return "serverId:ribbon-consumer<-------->"+r;
    }
}
