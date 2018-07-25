package com.ycn.springcloud.controller;

import com.ycn.springcloud.dao.TransferDao;
import com.ycn.springcloud.feign.AccountClient;
import com.ycn.springcloud.service.TransferService;
import org.bytesoft.compensable.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName TransferController
 * @Date 2018/7/11 17:32
 */
@Compensable(interfaceClass = TransferService.class, confirmableKey = "transferServiceConfirm", cancellableKey = "transferServiceCancel")
@RestController
public class TransferController implements TransferService {

    public static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    private TransferDao transferDao;
    @Autowired
    private AccountClient accountClient;

    @ResponseBody
    @RequestMapping(value = "/transfer")
    @Transactional
    public void transfer(@RequestParam String sourceAcctId, @RequestParam String targetAcctId, @RequestParam double amount) {
        accountClient.decreaseAmount(sourceAcctId, amount);
        this.increaseAmount(targetAcctId, amount);
    }
    private void increaseAmount(String acctId, double amount) {
        int value = this.transferDao.increaseAmount(acctId, amount);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("exec increase: acct= {}, amount= {}", acctId, amount);
    }

    /**
     * TransferController中可以存在@Compensable.interfaceClass指定接口中未定义的方法.
     * 需要注意的是, 未在interfaceClass接口中定义的方法, 不属于可补偿型业务操作, 不走TCC全局事务.
     * @param acctId
     * @return
     */
    @RequestMapping("/getAmount")
    public Double getAmount(@RequestParam("acctId") String acctId) {
        return transferDao.getAcctAmount(acctId);
    }
}
