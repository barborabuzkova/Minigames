package com.example.minigames;

import java.util.*;

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
        for (int i = 0; i < Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS; i++) { //populates currentlyOnBoard
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
                // System.out.println(Arrays.toString(characteristics)); //used to test
            } else {
                createPermutations(characteristics, loc + 1);
            }
        }
    }

    /**
     * @return Arraylist of cards that are currently on the board
     */
    public ArrayList<Card> getCurrentlyOnBoard() { // TODO Can the user get this, change it, and break everything?
        return currentlyOnBoard;
    }

    public ArrayList<ASet> getCollectedSets() {
        return collectedSets;
    }

    /**
     * After calling Algorithm.checkIfSet, removes collected cards from board and puts the set into
     * the collectedSets, if room on board, adds new cards
     * @param collected the set of cards that have been collected
     */
    public boolean setCollected (Set<Card> collected) {
        if (Algorithm.checkIfSet(collected)) {
            collectedSets.add(new ASet(collected));
            currentlyOnBoard.removeAll(collected);
            if (currentlyOnBoard.size() < Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS) {
                for (int i = 0; i < Card.SET_SIZE; i++) {
                    currentlyOnBoard.add(currentDeck.remove(0));
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds cards when there is no set
     */
    public void addCardsNoSetFound () { // TODO read enum
        for (int i = 0; i < Card.SET_SIZE; i++) {
            currentlyOnBoard.add(currentDeck.remove(0));
        }
    }

    /**
     * adds cards to the board if it isn't full
     */
    public void addCardsBoardNotFull () {
//        if (!(currentlyOnBoard.size() < Card.SET_SIZE * Card.NUMBER_OF_CHARACTERISTICS)) {
//            throw new Exception("Board is full"); //TODO what type of exception is this???
//        }
        for (int i = 0; i < Card.SET_SIZE; i++) {
            currentlyOnBoard.add(currentDeck.remove(0));
        }
    }
}


