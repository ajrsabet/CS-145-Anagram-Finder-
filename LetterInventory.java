/*
* Programmer: Adam Sabet
* Class: CS145 with Jaramiah Ramsey
* Date Due: 6/9/2023
* Assignment 3

    * LetterInventory tracks the letters remaining from the phrase while searching
    * for anagrams. It is also used to create temporary inventories of each word 
    * from the list to compare to the phrase and subtract letters. 
*/
import java.util.*;

public class LetterInventory {
    private Map<Character, Integer> inventory;
    private static int instanceCount = 0;

    /*-----------------------------------------------------------------------*/
    /* default constructor */
    public LetterInventory(String word) {
        // I don't quite understand HashMaps but I spent hours trying to make a
        // ArrayList work then found a suggestion for HashMap
        inventory = new HashMap<>();
        word = word.toLowerCase();

        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) {
                inventory.put(c, inventory.getOrDefault(c, 0) + 1);
            }
        }
        instanceCount++; // Increment the instance count
    }

    /*-----------------------------------------------------------------------*/
    /* get the count of a letter */
    private int get(char letter) {
        return inventory.getOrDefault(letter, 0);
    }

    /*-----------------------------------------------------------------------*/
    /* get letters */
    private Set<Character> getLetters() {
        return inventory.keySet();
    }

    /*-----------------------------------------------------------------------*/
    /* return size of inventory */
    private int size() {
        int count = 0;
        for (int value : inventory.values()) {
            count += value;
        }
        return count;
    }

    /*-----------------------------------------------------------------------*/
    /* check if inventory is empty */
    public boolean isEmpty() {
        return size() == 0;
    }

    /*-----------------------------------------------------------------------*/
    /* add letters to the inventory */
    public void add(LetterInventory other) {
        for (char letter : other.getLetters()) {
            int count = other.get(letter);
            inventory.put(letter, inventory.getOrDefault(letter, 0) + count);
        }
    }

    /*-----------------------------------------------------------------------*/
    /* subtract letters from the inventory */
    public boolean subtract(LetterInventory other) {
        if (contains(other)) {
            for (char letter : other.getLetters()) {
                int count = other.get(letter);
                inventory.put(letter, inventory.get(letter) - count);
                if (inventory.get(letter) == 0) {
                    inventory.remove(letter);
                }
            }
            return true;
        }
        return false;
    }

    /*-----------------------------------------------------------------------*/
    /* check if the inventory contains all of the letters from another inventory */
    public boolean contains(LetterInventory other) {
        for (char letter : other.getLetters()) {
            int count = other.get(letter);
            if (count > inventory.getOrDefault(letter, 0)) {
                return false;
            }
        }
        return true;
    }

    /*-----------------------------------------------------------------------*/
    /* return how many instances of LetterInventory were created */
    public static int getInstanceCount() {
        return instanceCount;
    }

    /*-----------------------------------------------------------------------*/
    /* reset the instance count */
    public static void resetInstanceCount() {
        instanceCount = 0;
    }
}
