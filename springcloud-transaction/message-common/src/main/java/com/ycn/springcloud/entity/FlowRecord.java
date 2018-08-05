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
 * @author ycn
 * @package com.ycn.springcloud.entity
 * @ClassName FlowRecord
 * @Date 2018/8/1 22:00
 */
@Getter
@Setter
@ToString
@TableName("flow_record")
public class FlowRecord extends Model<FlowRecord> {

    private static final long serialVersionUID = -8917550960288803797L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 流水名称
     */
    private String name;
    /**
     * 总价
     */
    private BigDecimal sum;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
