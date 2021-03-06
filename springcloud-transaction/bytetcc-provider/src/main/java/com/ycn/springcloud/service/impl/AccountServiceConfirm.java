package com.ycn.springcloud.service.impl;

import com.ycn.springcloud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * confirm实现
 *
 * @author ycn
 * @package com.ycn.springcloud.service.impl
 * @ClassName AccountServiceConfirm
 * @Date 2018/7/11 11:33
 */
@Service("accountServiceConfirm")
public class AccountServiceConfirm implements AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountServiceConfirm.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void increaseAmount(String acctId, Double amount) {
        int value = this.jdbcTemplate.update(
                "update tb_account_one set amount = amount + ?, frozen = frozen - ? where acct_id = ?", amount, amount, acctId);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("done increase: acct= {}, amount= {}", acctId, amount);
    }

    @Override
    @Transactional
    public void decreaseAmount(String acctId, Double amount) {
        int value = this.jdbcTemplate.update("update tb_account_one set frozen = frozen - ? where acct_id = ?", amount, acctId);
        if (value != 1) {
            throw new IllegalStateException("ERROR!");
        }
        logger.info("done decrease: acct= {}, amount= {}", acctId, amount);
    }
}
