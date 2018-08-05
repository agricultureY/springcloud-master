package com.ycn.springcloud.enums;

/**
 * 订单状态枚举
 *
 * @author ycn
 * @package com.ycn.springcloud.enums
 * @ClassName OrderStatusEnum
 * @Date 2018/8/2 13:48
 */
public enum OrderStatusEnum {

    /**
     * 未完成
     */
    NOT_SUCCESS(1),

    /**
     * 已完成
     */
    SUCCESS(2);

    private Integer status;

    OrderStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
