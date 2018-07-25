package com.ycn.springcloud.service.impl;

import com.ycn.springcloud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * cancel实现
 *
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName AccountServiceCancel
 * @Date 2018/7/11 14:25
 */
@Service("accountServiceCancel")
public class AccountServiceCancel implements AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountServiceCancel.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void increaseAmount(String acctId, Double amount) {
        int value = this.jdbcTemplate.update("update tb_account_one set frozen = frozen - ? where acct_id = ?", amount, acctId);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("undo increase: acct= {}, amount= {}", acctId, amount);
    }

    @Override
    @Transactional
    public void decreaseAmount(String acctId, Double amount) {
        int value = this.jdbcTemplate.update(
                "update tb_account_one set amount = amount + ?, frozen = frozen - ? where acct_id = ?", amount, amount, acctId);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("undo decrease: acct= {}, amount= {}", acctId, amount);
    }
}
