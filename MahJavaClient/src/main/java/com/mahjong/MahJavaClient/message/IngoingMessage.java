package com.mahjong.MahJavaClient.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class IngoingMessage implements Serializable {
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Object content;

    public IngoingMessage() {}

    public IngoingMessage(String type, Object content) {
        this.type = type;
        this.content = content;
    }
}
