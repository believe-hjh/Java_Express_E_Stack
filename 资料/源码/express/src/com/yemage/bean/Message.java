package com.yemage.bean;

/**
 * @author yemage
 */
public class Message {
    //{result:"",data:{},status:0}

    //消息内容
    private String result;

    //消息所携带的一组数据
    private Object data;

    //状态码   返回0 成功 返回-1表示失败
    private int status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Message(String result, int status) {
        this.result = result;
        this.status = status;
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(String result) {
        this.result = result;
    }

    public Message() {
    }

    public Message(String result, Object data, int status) {
        this.result = result;
        this.data = data;
        this.status = status;
    }
}
