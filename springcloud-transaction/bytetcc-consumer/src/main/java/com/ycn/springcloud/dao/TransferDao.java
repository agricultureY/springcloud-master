package com.ycn.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ycn
 * @package com.ycn.springcloud.dao
 * @ClassName TransferDao
 * @Date 2018/7/25 10:30
 */
@Mapper
public interface TransferDao {

    @Update("update tb_account_two set frozen = frozen + #{amount} where acct_id = #{acctId}")
    int increaseAmount(@Param("acctId") String accountId, @Param("amount") double amount);

    @Select("select amount from tb_account_two where acct_id = #{acctId}")
    Double getAcctAmount(@Param("acctId") String accountId);

    @Update("update tb_account_two set amount = amount + #{amount}, frozen = frozen - #{amount} where acct_id = #{acctId}")
    int confirmIncrease(@Param("acctId") String accountId, @Param("amount") double amount);

    @Update("update tb_account_two set frozen = frozen - #{amount} where acct_id = #{acctId}")
    int cancelIncrease(@Param("acctId") String accountId, @Param("amount") double amount);

}
