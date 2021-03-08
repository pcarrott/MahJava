package com.mahjong.MahJavaServer.game;

import com.mahjong.MahJavaServer.message.OutgoingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate sender;

    @Autowired
    private GameService gameService;

    @MessageMapping("/user/{userId}/game/create")
    @SendTo("/queue/user/{userId}")
    public OutgoingMessage createGame(@PathVariable Integer userId) {
        Integer content = gameService.createGame(userId);
        return new OutgoingMessage("create", content);
    }

    @MessageMapping("/user/{userId}/game/{gameId}/join")
    @SendTo("/topic/game/{gameId}")
    public OutgoingMessage joinGame(@PathVariable Integer userId, @PathVariable Integer gameId) {
        Integer content = gameService.joinGame(userId, gameId);
        return new OutgoingMessage("join", content);
    }

    @MessageMapping("/game/{gameId}/start")
    public void startGame(@PathVariable Integer gameId) {
        boolean started = gameService.startGame(gameId);
        if (started) {
            OutgoingMessage response = new OutgoingMessage("start", gameId);
            sender.convertAndSend("/topic/game/" + gameId, response);
        }
    }

    @MessageMapping("/user/{userId}/game/{gameId}/discard")
    @SendTo("/topic/game/{gameId}")
    public OutgoingMessage discardTile(@PathVariable Integer userId, @PathVariable Integer gameId) {
        boolean discarded = gameService.discardTile(userId, gameId);
        return new OutgoingMessage("discard", discarded);
    }

    @MessageMapping("/user/{userId}/game/{gameId}/skip")
    public void skipTile(@PathVariable Integer userId, @PathVariable Integer gameId) {
        gameService.skipTile(userId, gameId);
        OutgoingMessage response = new OutgoingMessage("skip", null);
        sender.convertAndSend("/queue/user/" + userId, response);

        if (gameService.hasAllAnswers(gameId)) {
            response = new OutgoingMessage("resume", null);
            sender.convertAndSend("/topic/game/" + gameId, response);
        }
    }

    @MessageMapping("/user/{userId}/game/{gameId}/draw")
    public void drawTile(@PathVariable Integer userId, @PathVariable Integer gameId) {
        boolean drawn = gameService.drawTile(userId, gameId);
        OutgoingMessage response = new OutgoingMessage("draw", drawn);
        sender.convertAndSend("/queue/user/" + userId, response);

        if (gameService.hasAllAnswers(gameId)) {
            response = new OutgoingMessage("resume", null);
            sender.convertAndSend("/topic/game/" + gameId, response);
        }
    }

    @MessageMapping("/user/{userId}/game/{gameId}/leave")
    @SendTo("/topic/game/{gameId}")
    public OutgoingMessage leaveGame(@PathVariable Integer userId, @PathVariable Integer gameId) {
        Integer content = gameService.leaveGame(userId, gameId);
        return new OutgoingMessage("leave", content);
    }
}
