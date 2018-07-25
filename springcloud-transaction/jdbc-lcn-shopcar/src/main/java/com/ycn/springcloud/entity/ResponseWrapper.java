package com.ycn.springcloud.entity;

/**
 * 自定义返回结果
 *
 * @author ycn
 * @package com.ycn.springcloud.entity
 * @ClassName ResponseEntity
 * @Date 2018/7/2 17:29
 */
public class ResponseWrapper {

    private Integer code;

    private String msg;

    private Object res;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    private ResponseWrapper() {  }

    public static ResponseWrapper markSuccess(Object obj) {
        ResponseWrapper res = new ResponseWrapper();
        res.setCode(200);
        res.setMsg("请求成功");
        res.setRes(obj);
        return res;
    }

    public static ResponseWrapper markServerError(){
        ResponseWrapper res = new ResponseWrapper();
        res.setCode(500);
        res.setMsg("服务器异常");
        return res;
    }

    public static ResponseWrapper markParamError(){
        ResponseWrapper res = new ResponseWrapper();
        res.setCode(400);
        res.setMsg("请求参数错误");
        return res;
    }
}
