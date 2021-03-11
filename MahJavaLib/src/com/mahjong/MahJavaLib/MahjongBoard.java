package com.mahjong.MahJavaLib;

import java.util.*;

public class MahjongBoard {


    private HashMap<MahjongTile, Integer> _wall = new HashMap<>();

    public MahjongBoard() {
        generateWall();
    }

    private void generateWall() {
        int i = 0;
        for (MahjongTile.TileType tt = MahjongTile.TileType.CHARACTERS; i < 5; tt = tt.next(), i++) {
            switch (tt) {
                case CHARACTERS:
                case DOTS:
                case BAMBOO:
                    for (MahjongTile.TileContent tc = MahjongTile.TileContent.ONE; tc != MahjongTile.TileContent.RED; tc = tc.next()) {
                        this._wall.put(new MahjongTile(tt, tc), 4);
                    }
                    break;
                case DRAGON:
                    for (MahjongTile.TileContent tc = MahjongTile.TileContent.RED; tc != MahjongTile.TileContent.EAST; tc = tc.next()) {
                        this._wall.put(new MahjongTile(tt, tc), 4);
                    }
                    break;
                case WIND:
                    for (MahjongTile.TileContent tc = MahjongTile.TileContent.EAST; tc != MahjongTile.TileContent.ONE; tc = tc.next()) {
                        this._wall.put(new MahjongTile(tt, tc), 4);
                    }
                    break;
            }
        }
    }

    public Map<MahjongTile, Integer> getWall() {
        return this._wall;
    }

    public boolean isWallEmpty() {
        return this._wall.isEmpty();
    }

    public MahjongTile removeTileFromWall() throws IllegalStateException {
        if (this._wall.isEmpty()) {
            throw new IllegalStateException();
        }

        try {
            MahjongTile tile = getRandomTile();
            Integer value = this._wall.get(tile);

            this._wall.replace(tile, --value);
            if (value <= 0) {
                this._wall.remove(tile);
            }
            System.out.println("Tile value in wall - " + this._wall.get(tile) + "; value = " + value);
            return tile;

        } catch (IllegalStateException e) {
            //Try to get a random tile until success. Doesn't take many tries as 0-value tiles are removed from wall
            return getRandomTile();
        }
    }

    private MahjongTile getRandomTile() throws IllegalStateException {
        ArrayList<MahjongTile> contents = new ArrayList<>(getWall().keySet());
        Random r = new Random();
        MahjongTile tile = contents.get(r.nextInt(contents.size()));
        if (this._wall.get(tile) <= 0) {
            throw new IllegalStateException();
        }
        return tile;
    }

    @Override
    public String toString() {
        return "Board Wall = " + _wall;
    }

    public static void main(String[] args) {
        MahjongBoard board = new MahjongBoard();

        MahjongTile removedTile;
        int i = 0;

        try {
            for (i = 0; i < 1000; i++) {
                removedTile = board.removeTileFromWall();
                System.out.println("RemovedTile number i " + i + " - " + removedTile);
            }
        } catch (IllegalStateException e) {
            System.out.println("Wall is Empty. Game Over; isEmpty = " + board.getWall().isEmpty() + "; wall = " + board.getWall() + "; i = " + i);
        }
    }
}