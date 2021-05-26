package MahJava.game;

import MahJava.exceptions.WallIsEmptyException;
import MahJava.tile.Tile;
import MahJava.tile.TileContent;
import MahJava.tile.TileType;

import java.util.*;

public class Board {
    private Deque<Tile> wall;

    public Board() {
        this.wall = generateWall();
    }

    private Deque<Tile> generateWall() {
        List<Tile> list = new ArrayList<>();
        // Iterate through all possible tile types and generate their corresponding tiles
        for (TileType tt : TileType.values()) {
            if (!tt.isSpecialType()) {
                // For the normal tile types, we only want to generate the number contents
                TileContent.getNumbers().forEach(tc -> addFourTileCopies(list, new Tile(tt, tc)));
            } else if (tt == TileType.DRAGON) {
                // For dragons, we only want the colors
                TileContent.getColors().forEach(tc -> addFourTileCopies(list, new Tile(tt, tc)));
            } else {
                // And for winds, the directions
                TileContent.getDirections().forEach(tc -> addFourTileCopies(list, new Tile(tt, tc)));
            }
        }

        Collections.shuffle(list);
        return new ArrayDeque<>(list);
    }

    private static void addFourTileCopies(List<Tile> list, Tile e) {
        for (int i = 0; i < 4; i++) {
            list.add(e);
        }
    }

    public Deque<Tile> getWall() {
        return this.wall;
    }

    public boolean isWallEmpty() {
        // The last 14 pieces are what is called the Dead Wall.
        // These tiles can only be used when a Kong is declared.
        // Otherwise, the game ends when only these 14 tiles remain.
        return this.wall.size() <= 14;
    }

    public Tile removeTileFromLiveWall() throws WallIsEmptyException {
        if (this.wall.isEmpty()) {
            throw new WallIsEmptyException();
        }

        return this.wall.removeFirst();
    }

    public Tile removeTileFromDeadWall() throws WallIsEmptyException {
        if (this.wall.isEmpty()) {
            throw new WallIsEmptyException();
        }

        return this.wall.removeLast();
    }

    public void reset() {
        this.wall = this.generateWall();
    }

    @Override
    public String toString() {
        return "Board Wall = " + wall;
    }
}