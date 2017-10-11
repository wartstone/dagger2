package com.vertical.app.bean;

import java.io.Serializable;

/**
 * Created by katedshan on 17/9/3.
 */

public class MessageBean implements Serializable {
    private long id;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
