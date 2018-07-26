package com.ycn.springcloud.constant;

/**
 * @author ycn
 * @package com.ycn.springcloud.constant
 * @ClassName TokenEnum
 * @Date 2018/7/26 11:01
 */
public enum TokenEnum {
    TOKEN_API("1", "api"),
    TOKEN_FORM("2","form");

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    TokenEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
