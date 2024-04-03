package com.example.minigames;

import java.util.Set;

public class Game {
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
     * @param collected
     */
    public boolean setCollected (Set<Card> collected) {
        return position.setCollected(collected);
    }
}
