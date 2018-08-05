package com.ycn.springcloud.enums;

/**
 * 消息枚举
 *
 * @author ycn
 * @package com.ycn.springcloud.enums
 * @ClassName MessageEnum
 * @Date 2018/7/30 10:25
 */
public enum MessageEnum {

    Y("是"),

    N("否"),

    WAIT_VERIFY("待确认"),

    MAKE_ORDER("MAKE_ORDER"),

    SENDING("发送中");

    private String desc;

    MessageEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
