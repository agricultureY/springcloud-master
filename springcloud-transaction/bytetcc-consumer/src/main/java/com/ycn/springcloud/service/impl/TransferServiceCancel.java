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
 * @ClassName TransferServiceCancel
 * @Date 2018/7/11 15:46
 */
@Service("transferServiceCancel")
public class TransferServiceCancel implements TransferService {

    public static final Logger logger = LoggerFactory.getLogger(TransferServiceCancel.class);

    @Autowired
    private TransferDao transferDao;

    @Override
    @Transactional
    public void transfer(String sourceAcctId, String targetAcctId, double amount) {
        int value = this.transferDao.cancelIncrease(targetAcctId, amount);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("exec decrease: acctId= {}, amount= {}", targetAcctId, amount);
    }
}
