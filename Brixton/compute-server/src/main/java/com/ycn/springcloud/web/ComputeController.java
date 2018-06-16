package com.ycn.springcloud.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComputeController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
    	Integer r = a + b;
    	List<String> serverIds = client.getServices();
    	if(!CollectionUtils.isEmpty(serverIds)) {
    		for (String serverId : serverIds) {
				System.out.println("<----------"+serverId+"--------->");
				List<ServiceInstance>  instances = client.getInstances(serverId);
				if(!CollectionUtils.isEmpty(instances)) {
					for (ServiceInstance instance : instances) {						
						logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
					}
				}
			}
    	}
        return r;
    }
    
    @RequestMapping(value = "/addWithServerId" ,method = RequestMethod.GET)
    public String addWithServerId(@RequestParam Integer a, @RequestParam Integer b) {
    	Integer r = a + b;
        return "serverId:compute-server<-------->"+r;
    }
}
