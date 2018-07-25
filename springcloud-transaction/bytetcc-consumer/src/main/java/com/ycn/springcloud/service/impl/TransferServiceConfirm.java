package com.ycn.springcloud.service.impl;

import com.ycn.springcloud.dao.TransferDao;
import com.ycn.springcloud.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName TransferServiceConfirm
 * @Date 2018/7/11 15:45
 */
@Service("transferServiceConfirm")
public class TransferServiceConfirm implements TransferService {

    public static final Logger logger = LoggerFactory.getLogger(TransferServiceConfirm.class);

    @Autowired
    private TransferDao transferDao;

    @Override
    @Transactional
    public void transfer(String sourceAcctId, String targetAcctId, double amount) {
        int value = this.transferDao.confirmIncrease(targetAcctId, amount);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("done increase: acctId= {}, amount= {}", targetAcctId, amount);
    }
}
