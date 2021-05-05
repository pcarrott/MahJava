package MahJavaLib.game;

import java.util.*;

import MahJavaLib.tile.*;
import MahJavaLib.exceptions.WallIsEmptyException;

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
        return this.wall.isEmpty();
    }

    public Tile removeFirstTileFromWall() throws WallIsEmptyException {
        if (this.wall.isEmpty()) {
            throw new WallIsEmptyException();
        }

        return this.wall.removeFirst();
    }

    public Tile removeLastTileFromWall() throws WallIsEmptyException {
        if (this.wall.isEmpty()) {
            throw new WallIsEmptyException();
        }

        return this.wall.removeLast();
    }

    @Override
    public String toString() {
        return "Board Wall = " + wall;
    }
}