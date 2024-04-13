package com.example.minigames;

import java.util.ArrayList;
import java.util.Set;

public class Game { //currently just a wrapper for Position
    private Position position;

    /**
     * creates a new game with a position
     */
    public Game() {
        System.out.println("game initialization started");
        this.position = new Position();
        System.out.println("game initialization finished");
    }

    /**
     * @return current position
     */
    public Position getPosition() {
        return position;
    }


    /**
     *
     * @param collected JavaSet of cards that are a potential set
     */
    public ArrayList<Card> setCollected (Set<Card> collected) {
        return position.setCollected(collected);
    }

    /**
     * Calls Position to add cards if there isn't a set
     */
    public ArrayList<Card> addCardsNoSetFound () {return position.addCardsNoSetFound();}

    /**
     * Calls Position to add cards if the board isn't full
     */
    public void addCardsBoardNotFull () {
        position.addCardsBoardNotFull();
    }
}
