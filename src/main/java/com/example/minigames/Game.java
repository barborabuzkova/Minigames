package com.example.minigames;

import java.util.Set;

public class Game {
    private Position position;

    /**
     * creates a new game with a position
     */
    public Game() {
        this.position = new Position();
    }

    /**
     *
     * @param collected
     */
    public boolean setCollected (Set<Card> collected) {
        return position.setCollected(collected);
    }
}
