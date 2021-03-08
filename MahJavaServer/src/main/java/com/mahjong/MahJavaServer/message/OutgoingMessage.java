package com.mahjong.MahJavaServer.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class OutgoingMessage implements Serializable {
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Object content;

    public OutgoingMessage() {}

    public OutgoingMessage(String type, Object content) {
        this.type = type;
        this.content = content;
    }
}
