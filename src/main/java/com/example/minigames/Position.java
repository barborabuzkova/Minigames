package com.example.minigames;

import java.util.*;

public class Position {
    /**
     * starts as total deck, cards are drawn from this and displayed on the board
     */
    private List<Card> currentDeck;
    /**
     * cards that are displayed on the board (start top left, moves to the right)
     * should always be a multiple of Cards.SET_SIZE
     */
    private List<Card> currentlyOnBoard;
    /**
     * ASets that have been collected, contain the cards that are collected off of the board
     */
    private final List<ASet> collectedSets;
    private int increment = 0;

    /**
     * initializes position - creates all possible cards and puts them into currentDeck, shuffles,
     * places initial cards into the board (starts as Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS)
     */
    public Position () {
        System.out.println("position initialization started");
        currentDeck = new ArrayList<>((int) Math.pow(Card.SET_SIZE, Card.NUMBER_OF_CHARACTERISTICS));
        createPermutations(new int[Card.NUMBER_OF_CHARACTERISTICS], 0); // recursively create deck
        System.out.println("Cards made, deck size: " + currentDeck.size());
        System.out.println("Cards: " + currentDeck.toString());
        //shuffleCards();
        Collections.shuffle(currentDeck); //shuffle deck
        System.out.println("Cards shuffled: " + currentDeck.toString());
        currentlyOnBoard = new ArrayList<>(Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS);
        for (int i = 0; i < Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS; i++) { //populate initial deck
            currentlyOnBoard.add(currentDeck.remove(0));
        }
        collectedSets = new ArrayList<>();
        System.out.println("position initialization finished");
    }

    /**
     * recursively creates all cards
     */
    private void createPermutations(int[] characteristics, int loc) {
        if (loc < 0 || loc >= Card.NUMBER_OF_CHARACTERISTICS) {
            throw new IllegalArgumentException("location out of bounds for array length");
        }
        for (int i = 0; i < Card.SET_SIZE; i++) {
            characteristics[loc] = i;
            if (loc == Card.NUMBER_OF_CHARACTERISTICS - 1) {
                currentDeck.add(new Card(characteristics));
                System.out.println(Arrays.toString(characteristics)); //used to test
            } else {
                createPermutations(characteristics, loc + 1);
            }
        }
    }

    //doesn't work
//    private void shuffleCards() {
//        for (int i = currentDeck.size() - 1; i >= 0; i--) {
//            Card temp = currentDeck.remove(i);
//            int loc = (int) (Math.random() * i);
//            currentDeck.add(i, currentDeck.remove(loc));
//            currentDeck.add(loc, temp);
//        }
//    }


    /**
     * @return Arraylist of cards that are currently on the board
     */
    public List<Card> getCurrentlyOnBoard() {
        return currentlyOnBoard;
    }

    /**
     * Removes collected cards from board and put the set into collectedSets
     * @param collected the set of cards that have been collected
     */
    public boolean setCollected (Set<Card> collected) {
        if (Algorithm.checkIfSet(collected)) {
            collectedSets.add(new ASet(collected));
            currentlyOnBoard.removeAll(collected);
            return true;
        } else {
            return false;
        }
    }
}
