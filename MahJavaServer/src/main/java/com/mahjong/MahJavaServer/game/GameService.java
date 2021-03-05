package com.mahjong.MahJavaServer.game;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameService {
    public Integer createGame(String username) {
        return new Random(username.length()).nextInt();
    }

    public Integer joinGame(Integer gameId) {
        return gameId;
    }

    public void drawTile() {}

    public void discardTile() {}

    public void deleteGame() {}
}
