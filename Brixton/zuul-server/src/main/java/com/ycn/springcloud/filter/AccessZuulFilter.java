package com.ycn.springcloud.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 网关过滤器
 * 
 * @Package: com.ycn.springcloud.filter
 * @author: ycn
 * @date: 2018年6月4日 下午12:54:25
 */
public class AccessZuulFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(AccessZuulFilter.class);

	/**
	 * 过滤器的具体逻辑
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		Object token = request.getParameter("token");
		if(null == token) {
			log.warn("access token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("token is empty");
			} catch (Exception e) {
			}
			return null;
		}
		log.info("access token ok");
		return null;
	}

	/**
	 * 返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。
	 * 		返回true，所以该过滤器总是生效
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 通过int值来定义过滤器的执行顺序
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
	 * 		pre：可以在请求被路由之前调用
	 * 		routing：在路由请求时候被调用
	 * 		post：在routing和error过滤器之后被调用
	 * 		error：处理请求时发生错误时被调用
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}
