package com.example.minigames;

import java.util.Set;

public class ASet { // to refactor to SetSet? Think about name
    /**
     * JavaSet of cards that are a valid Set
     */
    private final Set<Card> setOfCards;

    /**
     * initializes the set of cards
     * @param setOfCards JavaSet of cards that are a valid set
     */
    public ASet(Set<Card> setOfCards) {
        if (setOfCards.size() != Card.SET_SIZE) {
            throw new IllegalArgumentException("Set not correct size: is " + setOfCards.size()
                    + "should be " + Card.SET_SIZE);
        } else if (!Algorithm.checkIfSet(setOfCards)) {
            throw new IllegalArgumentException("Cards are not a valid set");
        }
        this.setOfCards = Set.copyOf(setOfCards);
    }

    /**
     * @return JavaSet of cards (valid set)
     */
    public Set<Card> getCards () {
        return setOfCards;
    }
}
