package com.ycn.springcloud.controller;

import com.ycn.springcloud.service.AccountService;
import org.bytesoft.compensable.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 *  ByteTCC倾向于认为: 使用SpringCloud时, 直接对外提供服务的Controller应该明确规划好它是普通服务还是TCC服务.
 *  因此, 0.4.x版本强制对外提供TCC服务的Controller必须加@Compensable注解(若没有实质业务, 也可以不必指定confirmableKey和cancellableKey).
 *  若不加@Compensable注解, 则ByteTCC将其当成普通服务对待, 不接收Consumer端传播的事务上下文. 若它后续调用TCC服务, 则将开启新的TCC全局事务.
 *  未在interfaceClass接口中定义的方法, 不属于可补偿型业务操作, 不走TCC全局事务
 *
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName AccountController
 * @Date 2018/7/11 14:32
 */
@Compensable(interfaceClass = AccountService.class, confirmableKey = "accountServiceConfirm", cancellableKey = "accountServiceCancel")
@RestController
public class AccountController implements AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/increase", method = { RequestMethod.POST,  RequestMethod.GET})
    @ResponseBody
    @Transactional
    @Override
    public void increaseAmount(@RequestParam("acctId") String acctId, @RequestParam("amount") Double amount) {
        int value = this.jdbcTemplate.update("update tb_account_one set frozen = frozen + ? where acct_id = ?", amount, acctId);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("exec increase: acct= {}, amount= {}", acctId, amount);
    }

    @RequestMapping(value = "/decrease", method = { RequestMethod.POST,  RequestMethod.GET})
    @ResponseBody
    @Transactional
    @Override
    public void decreaseAmount(@RequestParam("acctId") String acctId, @RequestParam("amount") Double amount) {
        int value = this.jdbcTemplate.update(
                "update tb_account_one set amount = amount - ?, frozen = frozen + ? where acct_id = ?", amount, amount, acctId);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("exec decrease: acct= {}, amount= {}", acctId, amount);
    }
}
