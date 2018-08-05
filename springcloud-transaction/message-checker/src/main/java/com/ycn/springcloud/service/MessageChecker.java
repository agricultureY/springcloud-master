package com.ycn.springcloud.service;

import com.ycn.springcloud.entity.ReliableMessage;
import com.ycn.springcloud.page.PageQuery;
import com.ycn.springcloud.page.PageResult;
import lombok.extern.log4j.Log4j;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象消息检验服务
 *
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName MessageChecker
 * @Date 2018/7/31 10:36
 */
@Log4j
public abstract class MessageChecker {

    /**
     * 消息处理
     * @param messages
     */
    protected abstract void processMessage(Map<String, ReliableMessage> messages);

    /**
     * 分页查询消息
     * @param pageQuery
     * @return
     */
    protected abstract PageResult<ReliableMessage> getPageResult(PageQuery pageQuery);

    public void checkMessage() {
        try {
            /**存放查询结果*/
            Map<String, ReliableMessage> msgmap = new HashMap<>();
            /**每页条数            一次最多处理页数            当前页*/
            int paseSzie = 2000, maxHandlerPageCount = 3, currentPage = 1;
            PageQuery pageQuery = new PageQuery(paseSzie, currentPage, "ASC", "create_time");
            PageResult<ReliableMessage> pageResult = this.getPageResult(pageQuery);
            if (null != pageResult && !CollectionUtils.isEmpty(pageResult.getRows())) {
                List<ReliableMessage> rows = pageResult.getRows();
                for (ReliableMessage item : rows)
                    msgmap.put(item.getMessageId(), item);

                long totalPage = pageResult.getTotalPage();
                if(totalPage > maxHandlerPageCount)
                    totalPage = maxHandlerPageCount;

                for (currentPage = 2; currentPage <= totalPage; currentPage++) {
                    pageQuery = new PageQuery(paseSzie, currentPage, "ASC", "create_time");
                    pageResult = getPageResult(pageQuery);
                    if (null != pageResult && !CollectionUtils.isEmpty(pageResult.getRows())) {
                        List<ReliableMessage> otherResults = pageResult.getRows();
                        for (ReliableMessage rowItem : otherResults)
                            msgmap.put(rowItem.getMessageId(), rowItem);
                    } else {
                        break;
                    }
                }
            }
            /**消息处理*/
            this.processMessage(msgmap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理待发送消息异常!", e);
        }
    }
}
