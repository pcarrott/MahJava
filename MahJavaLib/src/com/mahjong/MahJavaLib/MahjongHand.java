package com.mahjong.MahJavaLib;

import java.util.ArrayList;
import java.util.Random;

public class MahjongHand {
    private ArrayList<MahjongTile> _hand = new ArrayList<>();

    public MahjongHand() {}

    public MahjongHand(ArrayList<MahjongTile> _hand) {
        this._hand = _hand;
    }

    public ArrayList<MahjongTile> getHand() {
        return _hand;
    }

    public void setHand(ArrayList<MahjongTile> _hand) {
        this._hand = _hand;
    }

    public int handSize() {
        return this._hand.size();
    }

    public void addTile(MahjongTile addedTile) {
        if (this._hand.size() > 14) {
            throw new IllegalArgumentException("Hand is full (14 tiles)");
        }
        this._hand.add(addedTile);
    }

    public void discardTile(MahjongTile discardedTile) {
        this._hand.remove(discardedTile);
    }

    @Override
    public String toString() {
        return "Your Hand is: " + _hand;
    }

    public static void main(String[] args) {
        //The correct usage of MahjongHand is to generate a full hand a priori, such as:

        Random random = new Random();

        MahjongTile.TileType[] tileTypes = MahjongTile.TileType.values();
        MahjongTile.TileContent[] tileContents = MahjongTile.TileContent.values();

        ArrayList<MahjongTile> tiles = new ArrayList<>();
        for(int i = 0; i < 14; i++) {
            tiles.add(new MahjongTile(tileTypes[random.nextInt(tileTypes.length)], tileContents[random.nextInt(tileContents.length)]));
        }

        MahjongHand hand = new MahjongHand(tiles);

        System.out.println("Correct usage\n" + hand + "\nNumber of pieces in hand is: " + hand.handSize());
    }
};