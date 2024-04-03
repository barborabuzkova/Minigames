package com.example.minigames;

import java.util.HashSet;
import java.util.Set;

public class ASet {
    /**
     * JavaSet of cards that are a valid Set
     */
    private Set<Card> setOfCards;

    /**
     * initializes the set of cards
     * @param setOfCards JavaSet of cards that are a valid set
     */
    public ASet(Set<Card> setOfCards) {
        this.setOfCards = setOfCards;
        //throw error if set isn't valid
        //assert this.setOfCards.size() == Card.SET_SIZE;
    }

//    /**
//     * initalizes the set of cards, used by algorithm
//     * @param cards array of cards that are a valid set
//     */
//    public ASet(Card[] cards) {
//        this.setOfCards = new HashSet<>();
//        for (Card i: cards) {
//            setOfCards.add(i);
//        }
//    }

    /**
     * @return JavaSet of cards (valid set)
     */
    public Set<Card> getCards () {
        return setOfCards;
    }
}
