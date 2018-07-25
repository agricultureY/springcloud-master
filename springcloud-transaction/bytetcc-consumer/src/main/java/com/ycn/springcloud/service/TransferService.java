package com.ycn.springcloud.service;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName AccountService
 * @Date 2018/7/11 15:43
 */
public interface TransferService {

    void transfer(String sourceAcctId, String targetAcctId, double amount);
}
