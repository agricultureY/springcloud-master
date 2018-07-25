package com.ycn.springcloud.controller;

import com.ycn.springcloud.dao.TransferDao;
import com.ycn.springcloud.feign.AccountClient;
import com.ycn.springcloud.service.TransferService;
import org.bytesoft.compensable.Compensable;
import org.bytesoft.compensable.CompensableCancel;
import org.bytesoft.compensable.CompensableConfirm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName SimplifiedController
 * @Date 2018/7/12 12:45
 */
@Compensable(interfaceClass = TransferService.class, simplified = true)
@RequestMapping("/simplified")
@RestController
public class SimplifiedController implements TransferService {

    public static final Logger logger = LoggerFactory.getLogger(SimplifiedController.class);

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountClient accountClient;

    @RequestMapping("/transfer")
    @ResponseBody
    @Transactional
    @Override
    public void transfer(String sourceAcctId, String targetAcctId, double amount) {
        accountClient.decreaseAmount(sourceAcctId, amount);
        increaseAmount(targetAcctId, amount);
    }
    private void increaseAmount(String acctId, double amount) {
        int value = transferDao.increaseAmount(acctId, amount);
        if (value != 1) {
            logger.error("exec increase error");
            throw new IllegalStateException("exec increase error");
        }
        logger.info("exec increase: acct= {}, amount= {}", acctId, amount);
    }

    @CompensableConfirm
    @Transactional
    public void confirmTransfer(String sourceAcctId, String targetAcctId, double amount) {
        int value = transferDao.confirmIncrease(targetAcctId, amount);
        if (value != 1) {
            logger.error("done increase error");
            throw new IllegalStateException("done increase error");
        }
        logger.info("done increase: acct= {}, amount= {}", targetAcctId, amount);
    }

    @CompensableCancel
    @Transactional
    public void cancelTransfer(String sourceAcctId, String targetAcctId, double amount) {
        int value = transferDao.cancelIncrease(targetAcctId, amount);
        if (value != 1) {
            logger.error("exec decrease error");
            throw new IllegalStateException("exec decrease error");
        }
        logger.info("exec decrease: acct= {}, amount= {}", targetAcctId, amount);
    }
}
