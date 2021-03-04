package com.mahjong.MahJavaLib;

public class MahjongTile {

    public enum TileType {
        CHARACTERS, 
        DOTS, 
        BAMBOO, 
        DRAGON,
        WIND
    }

    public enum TileContent {
        ONE, 
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        RED,
        GREEN,
        WHITE,
        EAST,
        NORTH,
        WEST,
        SOUTH
    }

    private TileType _type;
    private TileContent _content;

    public MahjongTile(TileType tt, TileContent tc) throws IllegalArgumentException {
        switch (tt) {
            case CHARACTERS:
            case DOTS:
            case BAMBOO:
                switch(tc) {
                    case RED:
                    case GREEN:
                    case WHITE:
                    case EAST:
                    case NORTH:
                    case WEST:
                    case SOUTH:
                        throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);

                    default:
                        this._type = tt;
                        this._content = tc;
                        return;
                }
            case DRAGON:
                switch(tc) {
                    case RED:
                    case GREEN:
                    case WHITE:
                        this._type = tt;
                        this._content = tc;
                        return;

                    default:
                        throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);
                }
            case WIND:
                switch(tc) {
                    case EAST:
                    case NORTH:
                    case WEST:
                    case SOUTH:
                        this._type = tt;
                        this._content = tc;
                        return;

                    default:
                        throw new IllegalArgumentException("Illegal Content " + tc + " given Type " + tt);
                }
            default:
                throw new IllegalArgumentException("Illegal Type: " + tt);
        }
    }

    public TileType getType() {
        return _type;
    }

    public void setType(TileType _type) {
        this._type = _type;
    }

    public TileContent getContent() {
        return _content;
    }

    public void setContent(TileContent _content) {
        this._content = _content;
    }

    @Override
    public String toString() {
        return _type + " - " + _content;
    }
}
