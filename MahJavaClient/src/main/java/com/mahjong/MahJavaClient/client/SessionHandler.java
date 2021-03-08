package com.mahjong.MahJavaClient.client;

import com.mahjong.MahJavaClient.message.IngoingMessage;
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
        return IngoingMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        IngoingMessage response = (IngoingMessage) payload;
        switch (response.getType()) {
            case "create":
                Integer createdGame = (Integer) response.getContent();
                System.out.println("Game created with id: " + createdGame);
                break;
            case "join":
                Integer joinedGame = (Integer) response.getContent();
                System.out.println("Joined game with id: " + joinedGame);
                break;
            case "start":
                Integer startedGame = (Integer) response.getContent();
                System.out.println("Started game with id: " + startedGame);
                break;
            case "discard":
                boolean discarded = (boolean) response.getContent();
                System.out.println("Discarded tile: " + discarded);
                break;
            case "skip":
                System.out.println("Skipped tile");
                break;
            case "draw":
                boolean drawn = (boolean) response.getContent();
                System.out.println("Draw tile: " + drawn);
                break;
            case "resume":
                System.out.println("Resume play");
                break;
            case "leave":
                Integer leftGame = (Integer) response.getContent();
                System.out.println("Left game with id: " + leftGame);
                break;
            default:
                logger.error("Unknown type for ingoing message");
        }
    }
}
