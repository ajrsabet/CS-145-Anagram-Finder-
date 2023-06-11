/*
* Programmer: Adam Sabet
* Class: CS145 with Jaramiah Ramsey
* Date Due: 5/30/2023
* Lab 6: 20 Questions

* This program will do the following: 
    * This program takes a list from a text file to create a dictionary to find 
    * all anagram phrases that match a given word or phrase.  The AnagramMain 
    * prompts the user for phrases and then passes those phrases to this 
    * AnagramsManager then prints all anagrams for those phrases.  

* For extra credit I: 
    * I created a custom dictionary list called dictFoodList.txt with over 
    * 200 words related to food.
*/

import java.util.*;

public class AnagramsManager {
    private Set<String> dictionary;
    private List<List<String>> anagramCollection;
    private Set<String> allWords;

    /*-----------------------------------------------------------------------*/
    /* default constructor */
    public AnagramsManager(Set<String> dictionaryIn) {
        dictionary = dictionaryIn;
        anagramCollection = new ArrayList<>();
        allWords = new TreeSet<>();
    }

    /*-----------------------------------------------------------------------*/
    /*
     * getWords finds all of the words that can be made with the phrase from the
     * user input. These are not the anagrams
     */
    public Set<String> getWords(String phrase) {
        LetterInventory inventory = new LetterInventory(phrase);

        // reset lists from previous entries to prevent duplications
        anagramCollection = new ArrayList<>();
        allWords = new TreeSet<>();

        // Loop through each word in the dictionary
        for (String testWord : dictionary) {
            LetterInventory testWordLetters = new LetterInventory(testWord);
            if (inventory.contains(testWordLetters)) {
                allWords.add(testWord);
            }
        }
        return allWords;
    }

    /*-----------------------------------------------------------------------*/
    /*
     * getAnagrams starts the recursive method that finds all of the anagrams
     * from the users phrase
     */
    public List<List<String>> getAnagrams(String phrase) {
        LetterInventory inventory = new LetterInventory(phrase);
        List<String> tempAnagram = new ArrayList<>();

        // start the recursive method
        getAnagramsHelper(inventory, tempAnagram);
        return anagramCollection;
    }

    /*-----------------------------------------------------------------------*/
    /* getAnagramsHelper is the recursive search for anagrams */
    private void getAnagramsHelper(LetterInventory inventory,
            List<String> tempAnagram) {

        // trigger exit. Once the anagram is complete then this adds the words
        // to tempAnagram ArrayList working from the bottom to the top of the 
        // tree
        if (inventory.isEmpty()) {
            anagramCollection.add(new ArrayList<>(tempAnagram));
            return;
        }

        // loop through the allWords list. This is used instead of the dictionary
        // to save processing time since we already sorted out the words in the 
        // getWords function
        for (String word : allWords) {
            LetterInventory wordInventory = new LetterInventory(word);
            if (inventory.contains(wordInventory)) {
                // remove letters from inventory
                inventory.subtract(wordInventory);
                
                // add to temp anagram list
                tempAnagram.add(word);
                
                // trigger the next level of recussion. 
                getAnagramsHelper(inventory, tempAnagram);
                
                // replace letters in inventory for the next anagram search
                inventory.add(wordInventory);
                
                // remove last word from the tempAnagram for the next search
                tempAnagram.remove(tempAnagram.size() - 1);
            }
        }
    }

    /*-----------------------------------------------------------------------*/
    /* print all anagrams. no word number limit */
    public void print(String phrase) {
        System.out.println(phrase);
        List<List<String>> anagrams = getAnagrams(phrase);
        for (List<String> anagram : anagrams) {
            System.out.println(anagram);
        }
    }

    /*-----------------------------------------------------------------------*/
    /* print all anagrams. max indicates how many words can be in the anagram */
    public void print(String phrase, int max) {
        System.out.println(phrase);
        List<List<String>> anagrams = getAnagrams(phrase);
        for (List<String> anagram : anagrams) {
            if (anagram.size() <= max) {
                System.out.println(anagram);
            }
        }
    }

}
