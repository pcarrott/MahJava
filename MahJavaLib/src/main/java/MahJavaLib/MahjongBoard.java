package MahJavaLib;

import java.util.*;

public class MahjongBoard {


    private HashMap<MahJavaLib.MahjongTile, Integer> _wall = new HashMap<>();

    public MahjongBoard() {
        generateWall();
    }

    private void generateWall() {
        int i = 0;
        for (MahJavaLib.MahjongTile.TileType tt = MahJavaLib.MahjongTile.TileType.CHARACTERS; i < 5; tt = tt.next(), i++) {
            switch (tt) {
                case CHARACTERS:
                case DOTS:
                case BAMBOO:
                    for (MahJavaLib.MahjongTile.TileContent tc = MahJavaLib.MahjongTile.TileContent.ONE; tc != MahJavaLib.MahjongTile.TileContent.RED; tc = tc.next()) {
                        this._wall.put(new MahJavaLib.MahjongTile(tt, tc), 4);
                    }
                    break;
                case DRAGON:
                    for (MahJavaLib.MahjongTile.TileContent tc = MahJavaLib.MahjongTile.TileContent.RED; tc != MahJavaLib.MahjongTile.TileContent.EAST; tc = tc.next()) {
                        this._wall.put(new MahJavaLib.MahjongTile(tt, tc), 4);
                    }
                    break;
                case WIND:
                    for (MahJavaLib.MahjongTile.TileContent tc = MahJavaLib.MahjongTile.TileContent.EAST; tc != MahJavaLib.MahjongTile.TileContent.ONE; tc = tc.next()) {
                        this._wall.put(new MahJavaLib.MahjongTile(tt, tc), 4);
                    }
                    break;
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