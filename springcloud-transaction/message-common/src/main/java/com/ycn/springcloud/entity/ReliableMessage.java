package com.ycn.springcloud.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ycn
 * @package com.ycn.springcloud.entity
 * @ClassName ReliableMessage
 * @Date 2018/7/30 8:54
 */
@ToString
@Setter
@Getter
@TableName("reliable_message")
public class ReliableMessage extends Model<ReliableMessage> {

    private static final long serialVersionUID = 6245250112777038134L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 消息ID
     */
    @TableField("message_id")
    private String messageId;

    /**
     * 消息内容
     */
    @TableField("message_body")
    private String messageBody;

    /**
     * 消息数据类型
     */
    @TableField("message_data_type")
    private String messageDataType;

    /**
     * 消息队列
     */
    @TableField("consumer_queue")
    private String consumerQueue;

    /**
     * 消息重发次数
     */
    @TableField("message_send_times")
    private Integer messageSendTimes;

    /**
     * 是否死亡   Y:已死亡    N:未死亡
     */
    @TableField("already_dead")
    private String alreadyDead;

    /**
     * 状态      WAIT_VERIFY:待确认      SENDING:发送中
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime = new Date();

    /**
     * 最后修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 修改者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本号
     */
    private Long version = 0L;

    /**
     * 业务系统唯一ID
     */
    @TableField("biz_unique_id")
    private Long bizUniqueId;

    public ReliableMessage() {
    }

    public ReliableMessage(String messageId, String messageBody, String consumerQueue) {
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.consumerQueue = consumerQueue;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
