package com.mahjong.MahJavaClient.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class SessionHandler implements StompSessionHandler {
    private final Logger logger = LogManager.getLogger(SessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        // TODO
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public void handleTransportError(StompSession stompSession, Throwable throwable) {
        logger.error("Got a transport error", throwable);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        // TODO
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        // TODO
        System.out.println("Received : " + payload);
    }
}
