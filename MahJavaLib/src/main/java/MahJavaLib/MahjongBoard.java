package MahJavaLib;

import java.util.*;

public class MahjongBoard {


    private HashMap<MahJavaLib.MahjongTile, Integer> _wall = new HashMap<>();

    public MahjongBoard() {
        generateWall();
    }

    private void generateWall() {
        // Iterate through all possible tile types and generate their corresponding tiles
        for (MahJavaLib.MahjongTile.TileType tt : MahjongTile.TileType.values()) {
            if (!tt.isSpecialType()) {
                // For the normal tile types, we only want to generate the number contents
                for (MahjongTile.TileContent tc : MahjongTile.TileContent.getNumbers()) {
                    this._wall.put(new MahJavaLib.MahjongTile(tt, tc), 4);
                }
            } else if (tt == MahjongTile.TileType.DRAGON) {
                // For dragons, we only want the colors
                for (MahjongTile.TileContent tc : MahjongTile.TileContent.getColors()) {
                    this._wall.put(new MahJavaLib.MahjongTile(tt, tc), 4);
                }
            } else {
                // And for winds, the directions
                for (MahjongTile.TileContent tc : MahjongTile.TileContent.getDirections()) {
                    this._wall.put(new MahJavaLib.MahjongTile(tt, tc), 4);
                }
            }
        }
    }

    public Map<MahJavaLib.MahjongTile, Integer> getWall() {
        return this._wall;
    }

    public boolean isWallEmpty() {
        return this._wall.isEmpty();
    }

    public MahJavaLib.MahjongTile removeTileFromWall() throws IllegalStateException {
        if (this._wall.isEmpty()) {
            throw new IllegalStateException();
        }

        try {
            MahJavaLib.MahjongTile tile = getRandomTile();
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

    private MahJavaLib.MahjongTile getRandomTile() throws IllegalStateException {
        ArrayList<MahJavaLib.MahjongTile> contents = new ArrayList<>(getWall().keySet());
        Random r = new Random();
        MahJavaLib.MahjongTile tile = contents.get(r.nextInt(contents.size()));
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

        MahJavaLib.MahjongTile removedTile;
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