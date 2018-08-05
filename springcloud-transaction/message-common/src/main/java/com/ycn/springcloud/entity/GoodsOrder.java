package com.ycn.springcloud.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 *
 * @author ycn
 * @package com.ycn.springcloud.entity
 * @ClassName GoodsOrder
 * @Date 2018/8/1 21:50
 */
@Getter
@Setter
@ToString
@TableName("goods_order")
public class GoodsOrder extends Model<GoodsOrder> {

    private static final long serialVersionUID = 785315984527571867L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 商品名称
     */
    @TableField("goods_name")
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
    @TableField("create_time")
    private Date createTime;
    /**
     * 下单人id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 订单状态：1.未完成   2.已完成
     */
    @TableField("status")
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
