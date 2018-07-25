package com.ycn.springcloud.service.impl;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName TxManagerTxUrlServiceImpl
 * @Date 2018/7/2 17:59
 */
@Service
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {

    private final Logger logger = LoggerFactory.getLogger(TxManagerTxUrlServiceImpl.class);

    @Value("${tm.manager.url}")
    private String url;

    @Override
    public String getTxUrl() {
        System.out.println("tm url: ---------->"+url);
        logger.info("tm url: ---------->"+url);
        return url;
    }
}
