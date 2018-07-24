package com.ycn.springcloud.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ycn
 * @package com.ycn.springcloud.web
 * @ClassName TraceController
 * @Date 2018/6/28 17:19
 */
@RestController
public class TraceController {

    private final Logger logger = LoggerFactory.getLogger(TraceController.class);

    @RequestMapping("/trace-product")
    public String trace(HttpServletRequest request) {
        /**  log日志值信息
         * 第一个值：标识服务名称。
         * 第二个值  traceId:它用来标识一条请求链路。一条请求链路中包含一个Trace ID，多个Span ID。
         * 第三个值  spanId:它表示一个基本的工作单元，比如：发送一个HTTP请求。
         * 第四个值：false，表示是否要将该信息输出到Zipkin等服务中来收集和展示。
         * */
        logger.info("<---------------call trace-product, traceId={}, spanId={}---------------->",
                request.getHeader("X-B3-TraceId"),
                request.getHeader("X-B3-SpanId"));
        return "trace";
    }
}
