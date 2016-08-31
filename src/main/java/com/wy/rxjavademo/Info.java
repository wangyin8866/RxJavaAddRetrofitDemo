package com.wy.rxjavademo;

/**
 * Created by wy on 2016/8/29.
 */
public class Info {
    private int code;
    private String reason;

    public Info(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Info{" +
                "code=" + code +
                ", reason='" + reason + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
