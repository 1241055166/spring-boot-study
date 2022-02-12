package com.henry.valueobject;


/**
 * @ClassName Message
 * @Description TODO
 * @Author henry
 * @Date 2022/02/08
 **/
public class Message {

    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
