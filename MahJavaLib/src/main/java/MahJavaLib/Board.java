package MahJavaLib;

import java.util.*;

import MahJavaLib.Tile.*;

public class Board {


    private HashMap<Tile, Integer> _wall = new HashMap<>();

    public Board() {
        generateWall();
    }

    private void generateWall() {
        // Iterate through all possible tile types and generate their corresponding tiles
        for (TileType tt : TileType.values()) {
            if (!tt.isSpecialType()) {
                // For the normal tile types, we only want to generate the number contents
                for (TileContent tc : TileContent.getNumbers()) {
                    this._wall.put(new Tile(tt, tc), 4);
                }
            } else if (tt == TileType.DRAGON) {
                // For dragons, we only want the colors
                for (TileContent tc : TileContent.getColors()) {
                    this._wall.put(new Tile(tt, tc), 4);
                }
            } else {
                // And for winds, the directions
                for (TileContent tc : TileContent.getDirections()) {
                    this._wall.put(new Tile(tt, tc), 4);
                }
            }
        }
    }

    public Map<Tile, Integer> getWall() {
        return this._wall;
    }

    public boolean isWallEmpty() {
        return this._wall.isEmpty();
    }

    public Tile removeTileFromWall() throws IllegalStateException {
        if (this._wall.isEmpty()) {
            throw new IllegalStateException();
        }

        try {
            Tile tile = getRandomTile();
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

    private Tile getRandomTile() throws IllegalStateException {
        ArrayList<Tile> contents = new ArrayList<>(getWall().keySet());
        Random r = new Random();
        Tile tile = contents.get(r.nextInt(contents.size()));
        if (this._wall.get(tile) <= 0) {
            throw new IllegalStateException();
        }
        return tile;
    }

    @Override
    public String toString() {
        return "Board Wall = " + _wall;
    }
}