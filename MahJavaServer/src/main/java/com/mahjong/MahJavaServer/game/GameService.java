package com.mahjong.MahJavaServer.game;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GameService {
    public Integer createGame(Integer userId) {
        return new Random().nextInt();
    }

    public Integer joinGame(Integer userId, Integer gameId) {
        return userId;
    }

    public boolean startGame(Integer gameId) {
        return true;
    }

    public boolean discardTile(Integer userId, Integer gameId) {
        return true;
    }

    public void skipTile(Integer userId, Integer gameId) {}

    public boolean drawTile(Integer userId, Integer gameId) {
        return true;
    }

    public boolean hasAllAnswers(Integer gameId) {
        return true;
    }

    public Integer leaveGame(Integer userId, Integer gameId) {
        return gameId;
    }
}
