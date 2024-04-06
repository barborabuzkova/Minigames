package com.example.minigames;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {

    /**
     * You should never make instances of Algorithm
     * @throws IllegalStateException when called
     */
    public Algorithm() {
        throw new IllegalStateException("You should never make instances of Algorithm");
    }

    /**
     *
     * @param cards JavaSet of cards of the correct size
     * @return false if cards is not a set, true if it is
     * @throws IllegalArgumentException if cards is not the correct size
     */
    public static boolean checkIfSet (Set<Card> cards) {
        if (cards.size() != Card.SET_SIZE) {
            throw new IllegalArgumentException("Set size incorrect, should be " + Card.SET_SIZE +
                    " is " + cards.size());
        }
        for (int i = 0; i < Card.NUMBER_OF_CHARACTERISTICS; i++) {
            Set<Integer> comparing = new HashSet<>();
            for (Card c: cards) {
                comparing.add(c.getCharacteristics()[i]);
            }
            if (!(comparing.size() == 1 || comparing.size() == Card.SET_SIZE)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param currentlyOnBoard the cards that are currently on the board
     * @return Set of ASets that are on the board
     * @throws IllegalArgumentException if the board is empty
     */
    public static Set<ASet> findAllSets (ArrayList<Card> currentlyOnBoard) {
        if (currentlyOnBoard.isEmpty()) {
            throw new IllegalArgumentException("Board empty");
        }
        Set<ASet> combos = new HashSet<>();
        createCombinations(currentlyOnBoard, combos, new HashSet<>(), 0, new int[Card.SET_SIZE]);
        //System.out.println("all sets found: " + combos); //for testing
        return combos;
    }


    /**
     * Recursively goes through currentlyOnBoard to find all combinations of cards and adds Sets to combos
     * @param currentlyOnBoard cards on the board
     * @param combos where the sets are added
     * @param curr current set of cards being explored
     * @param locInArray which card is being examined, corresponds to allLocs
     * @param allLocs of length Card.SET_SIZE, contains the locations in currentlyOnBoard that are being explored
     */
    private static void createCombinations(ArrayList<Card> currentlyOnBoard, Set<ASet> combos,
                                           Set<Card> curr, int locInArray, int[] allLocs) {
        if (locInArray < 0 || locInArray >= currentlyOnBoard.size()) {
            throw new IllegalArgumentException("location out of bounds for arraylist length");
        }
        for (int i = allLocs[locInArray]; i < currentlyOnBoard.size(); i++) {
            curr.add(currentlyOnBoard.get(i));
            //System.out.println("i've been added"); //for testing
            if (curr.size() == Card.SET_SIZE) {
                if (checkIfSet(curr)) {
                    combos.add(new ASet(curr));
                    //System.out.println("wee i'm a set" + curr.toString()); //for testing
                }
            } else {
                allLocs[locInArray + 1] = i + 1;
                createCombinations(currentlyOnBoard, combos, curr, locInArray + 1, allLocs);
                allLocs[locInArray + 1] = i;
            }
            curr.remove(currentlyOnBoard.get(i));
            //System.out.println("i've been removed"); //for testing
        }
    }
}
