package com.example.minigames;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {
    public static Set<ASet> findAllSets (ArrayList<Card> currentlyOnBoard) {
        if (currentlyOnBoard.isEmpty()) {
            throw new IllegalArgumentException("Board empty");
        }
        Set<ASet> combos = new HashSet<>();
        createCombinations(currentlyOnBoard, combos, new HashSet<>(), 0, new int[Card.SET_SIZE]);
        System.out.println("all sets found: " + combos);
        return combos;
    }

//    public static void main(String[] args) {
//        Position p = new Position();
//        findAllSets(p.getCurrentlyOnBoard());
//    }

    private static void createCombinations(ArrayList<Card> currentlyOnBoard, Set<ASet> combos,
                                           Set<Card> curr, int locInArray, int[] allLocs) {
        if (locInArray < 0 || locInArray >= currentlyOnBoard.size()) {
            throw new IllegalArgumentException("location out of bounds for arraylist length");
        }
        for (int i = allLocs[locInArray]; i < currentlyOnBoard.size(); i++) {
            curr.add(currentlyOnBoard.get(i));
            System.out.println("i've been added");
            if (curr.size() == Card.SET_SIZE) {
                if (checkIfSet(curr)) {
                    combos.add(new ASet(curr));
                    System.out.println("wee i'm a set" + curr.toString());
                }
            } else {
                allLocs[locInArray + 1] = i + 1;
                createCombinations(currentlyOnBoard, combos, curr, locInArray + 1, allLocs);
                allLocs[locInArray + 1] = i;
            }
            curr.remove(currentlyOnBoard.get(i));
            System.out.println("i've been removed");
        }
    }

    public static boolean checkIfSet (Set<Card> cards) {
        if (cards.size() != Card.SET_SIZE) {
            throw new IllegalArgumentException("Set size incorrect, should be " + Card.SET_SIZE +
                    " is " + cards.size());
        }
        Set<Integer> comparing = new HashSet<>();
        for (int i = 0; i < Card.NUMBER_OF_CHARACTERISTICS; i++) {
            for (Card c: cards) {
                comparing.add(c.getCharacteristics()[i]);
            }
            if (!(comparing.size() == 1 || comparing.size() == Card.SET_SIZE)) {
                return false;
            }
        }
        return true;
    }
}
