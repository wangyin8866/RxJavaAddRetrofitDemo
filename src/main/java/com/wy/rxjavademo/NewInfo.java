package com.wy.rxjavademo;

/**
 * Created by wy on 2016/8/29.
 */
public class NewInfo {
    private String content;
    private String hashId;
    private String unixtime;
    private String updatetime;

    @Override
    public String toString() {
        return "NewInfo{" +
                "content='" + content + '\'' +
                ", hashId='" + hashId + '\'' +
                ", unixtime='" + unixtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }

    public NewInfo(String content, String hashId, String unixtime, String updatetime) {
        this.content = content;
        this.hashId = hashId;
        this.unixtime = unixtime;
        this.updatetime = updatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public String getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(String unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
