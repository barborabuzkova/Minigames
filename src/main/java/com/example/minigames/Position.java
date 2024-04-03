package com.example.minigames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Position {
    /**
     * starts as total deck, cards are drawn from this and displayed on the board
     */
    private ArrayList<Card> currentDeck;
    /**
     * cards that are displayed on the board (start top left, moves to the right)
     * should always be a multiple of Cards.SET_SIZE
     */
    private ArrayList<Card> currentlyOnBoard;
    /**
     * ASets that have been collected, contain the cards that are collected off of the board
     */
    private final ArrayList<ASet> collectedSets;

    /**
     * initialzes position - creates all possible cards and puts them into currentDeck, shuffles,
     * places initial cards into the board (starts as Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS)
     */
    public Position () {
        currentDeck = new ArrayList<>((int) Math.pow(Card.SET_SIZE, Card.NUMBER_OF_CHARACTERISTICS));
        //create all permutations of deck I've tried to look this up like 20 times and still can't find it
        //TODO fix - this is a temporary version which is really bad
//        currentDeck = new Card[(int) Math.pow(Card.SET_SIZE, Card.NUMBER_OF_CHARACTERISTICS)];
//        int count = 0;
//        for (int i = 0; i < Card.SET_SIZE; i++) {
//            for (int j = 0; j < Card.SET_SIZE; j++) {
//                for (int k = 0; k < Card.SET_SIZE; k++) {
//                    for (int l = 0; l < Card.SET_SIZE; l++) {
//                        currentDeck[count] = new Card(new int[]{i,j,k,l});
//                        count++;
//                    }
//                }
//            }
//        }

        Collections.shuffle(currentDeck); //shuffles deck
        currentlyOnBoard = new ArrayList<>(Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS);
        for (int i = 0; i < Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS; i++) {
            currentlyOnBoard.add(currentDeck.remove(0));
        }
        collectedSets = new ArrayList<>();
    }

    /**
     * @return Arraylist of cards that are currently on the board
     */
    public ArrayList<Card> getCurrentlyOnBoard() {
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
