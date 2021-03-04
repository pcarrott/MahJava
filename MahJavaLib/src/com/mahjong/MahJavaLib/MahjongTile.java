package com.mahjong.MahJavaLib;

public class MahjongTile {
    public enum TileType {
        CHARACTERS, DOTS, BAMBOO, RED_DRAGON,
        GREEN_DRAGON, WHITE_DRAGON, EAST_WIND,
        NORTH_WIND, WEST_WIND, SOUTH_WIND
    }

    public enum TileContent {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE
    }

    private TileType _type;
    private TileContent _content;

    public MahjongTile(TileType type, TileContent content) {
        this._type = type;
        this._content = content;
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
