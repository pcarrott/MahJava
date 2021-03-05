package com.mahjong.MahJavaClient.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Component
public class MahJavaClient implements CommandLineRunner {
    private final static String URL = "ws://localhost:8080/app";

    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void run(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new SessionHandler();
        StompSession session = stompClient.connect(URL, sessionHandler).get();

        final int userId = new Random().nextInt();
        session.subscribe("/topic/user/" + userId, sessionHandler);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("1 - Create game");
            System.out.println("2 - Join game");
            System.out.println("0 - Quit");
            System.out.println("-----------------------");
            String option = new Scanner(System.in).nextLine();

            switch (option) {
                case "0":
                    System.exit(SpringApplication.exit(context));
                case "1":
                    session.send("/app/user/" + userId + "/game/create", userId);
                    break;
                case "2":
                    System.out.print("Enter gameId: ");
                    String gameId = new Scanner(System.in).nextLine();
                    session.subscribe("/topic/game/" + gameId, sessionHandler);
                    session.send("/app/game/join/" + gameId, gameId);
                    break;
                default:
                    System.out.println("That was not an option...");
            }

            System.out.println("");
        }
    }
}
