package com.mahjong.MahJavaServer.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GameController {
    @Autowired
    GameService gameService;

    @MessageMapping("/user/{userId}/game/create")
    @SendTo("/topic/user/{userId}")
    public Integer createGame(@PathVariable Integer userId) {
        return gameService.createGame(userId);
    }

    @MessageMapping("/game/join/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public Integer joinGame(@PathVariable Integer gameId) {
        return gameService.joinGame(gameId);
    }

    @MessageMapping("/game/start")
    @SendTo("/topic/game")
    public void startGame() {
        gameService.startGame();
    }

    @MessageMapping("/game/discard")
    @SendTo("/topic/game")
    public void discardTile() {
        gameService.discardTile();
    }

    @MessageMapping("/game/skip")
    public void skipTile() {
        gameService.skipTile();
    }

    @MessageMapping("/game/draw")
    @SendTo("/topic/game")
    public void drawTile() {
        gameService.drawTile();
    }

    @MessageMapping("/game/leave")
    @SendTo("/topic/game")
    public void leaveGame() {
        gameService.leaveGame();
    }
}
