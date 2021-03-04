package com.mahjong.MahJavaLib;

import java.util.ArrayList;
import java.util.Random;

public class MahjongHand {
    private ArrayList<MahjongTile> _hand = new ArrayList<>();

    public MahjongHand(ArrayList<MahjongTile> hand) throws IllegalArgumentException {
        if(hand.size() > 14) {throw new IllegalArgumentException("Hand must contain 14 tiles");}
        else {this._hand = hand;}
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
        if (this._hand.size() >= 14) {
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

    public static MahjongHand generateRandomHand() {
        Random random = new Random();
        MahjongTile tile = null;

        MahjongTile.TileType[] tileTypes = MahjongTile.TileType.values();
        MahjongTile.TileContent[] tileContents = MahjongTile.TileContent.values();

        ArrayList<MahjongTile> tiles = new ArrayList<>();

        for(int i = 0; i < 14; i++) {
            try {
                tile = new MahjongTile(tileTypes[random.nextInt(tileTypes.length)], tileContents[random.nextInt(tileContents.length)]);
            } catch (IllegalArgumentException e) {
                //Nothing needs to be done because tile will remain null and therefore the loop will try again until success
            }
            if(tile != null) {tiles.add(tile);}
            else {i--;}
            tile = null;
        }

        try{
            return new MahjongHand(tiles);
        }
        catch (IllegalArgumentException e) {
            //This will never happen because the loop is correctly limited so it will always generate a 14-piece hand
            //However, if it did happen, it would keep trying until it produced a valid hand
            return generateRandomHand();
        }
    }

    public static void main(String[] args) {
        MahjongHand hand = generateRandomHand();

        System.out.println("Correct usage\n" + hand + "\nNumber of pieces in hand is: " + hand.handSize());
    }
};