package com.example.minigames;

public class Card {

    /**
     * number of cards in a set and number of possibilities for each characteristic
     * In regular set, this is 3 because there are 3 numbers, colors, fillings, and shapes
     */
    private final int setSize = 3;
    /**
     * number of characteristics on each card
     * In regular set, this is 4 - number, color, filling, and shape
     */
    private final int numberOfCharacteristics = 4;
    /**
     * the actual values, stored as numbers 0 to setSize - 1, of length numberOfCharacteristics
     */
    private final int[] characteristics;
    private final String id;


    /**
     * initializes the Card with given characteristics
     * @param characteristics - array of length numberOfCharacteristics (4 in standard set)
     *                        with numbers between 0 and setSize - 1 (0 to 2 in standard set)
     */
    public Card(int[] characteristics) {
        this.characteristics = characteristics;
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < numberOfCharacteristics; i++) {
            temp.append(characteristics[i]);
        }
        this.id = temp.toString();
    }

    /**
     * returns characteristics []
     * @return array of length numberOfCharacteristics (4 in standard set)
     *  with numbers between 0 and setSize - 1 (0 to 2 in standard set)
     */
    public int[] getCharacteristics() {
        return characteristics;
    }
}
