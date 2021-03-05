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

    @MessageMapping("/game/create")
    @SendTo("/topic/game")
    public Integer createGame(String username) {
        return gameService.createGame(username);
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
