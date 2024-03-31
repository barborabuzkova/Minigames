package com.example.minigames;

import java.util.Arrays;

public class Card {

    /**
     * number of cards in a set and number of possibilities for each characteristic
     * In regular set, this is 3 because there are 3 numbers, colors, fillings, and shapes
     */
    public static final int SET_SIZE = 3;
    /**
     * number of characteristics on each card
     * In regular set, this is 4 - number, color, filling, and shape
     */
    public static final int NUMBER_OF_CHARACTERISTICS = 4;
    /**
     * the actual values, stored as numbers 0 to setSize - 1, of length numberOfCharacteristics
     */
    private final int[] characteristics;


    /**
     * initializes the Card with given characteristics
     * @param characteristics - array of length numberOfCharacteristics (4 in standard set)
     *                        with numbers between 0 and setSize - 1 (0 to 2 in standard set)
     */
    public Card(int[] characteristics) {
        this.characteristics = characteristics;
    }

    /**
     * returns characteristics []
     * @return array of length numberOfCharacteristics (4 in standard set)
     *  with numbers between 0 and setSize - 1 (0 to 2 in standard set)
     */
    public int[] getCharacteristics() {
        return characteristics;
    }

    @Override
    public String toString() {
        return Arrays.toString(characteristics);
    }
}
