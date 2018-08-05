package com.ycn.springcloud.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单流水参数
 *
 * @author ycn
 * @package com.ycn.springcloud.dto
 * @ClassName GoodsFlowParam
 * @Date 2018/8/2 20:54
 */
@Getter
@Setter
@ToString
public class GoodsFlowParam implements Serializable {

    private static final long serialVersionUID = -7703801662280855654L;

    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 总金额
     */
    private BigDecimal sum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 下单人id
     */
    private Long userId;
}
