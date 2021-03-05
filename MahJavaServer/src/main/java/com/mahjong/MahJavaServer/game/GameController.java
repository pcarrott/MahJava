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

    @MessageMapping("/game/draw")
    @SendTo("/topic/game")
    public void drawTile() {
        gameService.drawTile();
    }

    @MessageMapping("/game/discard")
    @SendTo("/topic/game")
    public void discardTile() {
        gameService.discardTile();
    }

    @MessageMapping("/game/delete")
    @SendTo("/topic/game")
    public void deleteGame() {
        gameService.deleteGame();
    }
}