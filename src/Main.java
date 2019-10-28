/**
 * Author: John Palmer
 */

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static String[] wordBank;
    private static int usedWordCount = 0;

    public static void main(String[] args) {
        wordBank = setWordBank();

        boolean keepPlaying = true;

        while (keepPlaying == true){

            runGame(pickRandomWord(wordBank));

            keepPlaying = playAgainPrompt(keepPlaying);

        }

        if (usedWordCount >= 10)
            System.out.println("That's all the words! Thanks for playing :)");

    }

    /**
     * initializes the word bank for the game
     * @return the completed word bank with the 10 words shown below
     */
    public static String[] setWordBank(){
        String[] wordBank = new String[] {"purchase", "study", "window", "television",
                "lamp", "bathroom", "laptop", "cellphone", "cake", "garage"};

        return wordBank;
    }

    /**
     * uses Random to create a random integer to use in selecting a word
     * @param wordBank array of different words that are randomly selected to be used in the game
     * @return a random word from the word bank
     */
    public static String pickRandomWord(String[] wordBank){
        Random random = new Random();
        int randInt;

        String word;
        boolean isUsed = false;

        do {

            randInt = random.nextInt(10);

            word = wordBank[randInt];

            isUsed = (word == " ");

        } while (isUsed);

        wordBank[randInt] = " ";
        usedWordCount++;

        return word;
    }


    /**
     * initializes the available letters in an array and keeps track of the number of incorrect guesses the user has left
     * calls the methods displayGame, checkGuess, and gameCheckWin
     * @param word the word that the user is trying to guess
     */
    public static void runGame(String word){

        char[] availableLetters = new char[] {'a','b','c','d','e','f','g','h',
                'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        boolean gameOver = false;
        boolean correctGuess = false;

        int guessesLeft = 7;

        char[] visibleLetters = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            visibleLetters[i] = '-';
        }

        while(gameOver == false) {

            displayGame(availableLetters, visibleLetters, guessesLeft, word);
            correctGuess = checkGuess(availableLetters,visibleLetters,word);
            gameOver = gameCheckWin(visibleLetters, word);

            System.out.println();
            System.out.println();
            System.out.println();

            if (correctGuess == false)
                guessesLeft--;

            if (guessesLeft <= 0) {
                gameOver = true;
                System.out.println("You are out of guesses, game over");
            }

        }
    }

    /**
     * prompts the user if they would like to continue playing the game
     * @param keepPlaying used to check if the user can still be playing
     * @return boolean value true if the user would like to keep playing, false if not
     */
    public static boolean playAgainPrompt(boolean keepPlaying){


        System.out.println("Do you want to play again? (y/n)");
        Scanner kb = new Scanner(System.in);
        char userInput = kb.next().charAt(0);

        if (userInput == 'n') {
            keepPlaying = false;
            System.out.println("Goodbye :)");
        }

        else if (userInput == 'y')
            keepPlaying = true;

        else{
            System.out.println("Invalid input, please try again");
            playAgainPrompt(keepPlaying);
            }
        if (usedWordCount >= 10)
            keepPlaying = false;

        return keepPlaying;
    }

    /**
     * displays the available letters, the visible letters of the word being guessed, and the guesses the user has left
     * @param availableLetters list of letters the user can still guess
     * @param visibleLetters shows letters in the word being guessed if the user has guessed them
     * @param guessesLeft how many incorrect guesses the user has left before they lose
     * @param word the word that the user is trying to guess
     */
    public static void displayGame(char[] availableLetters, char[] visibleLetters, int guessesLeft, String word){
        System.out.println();
        System.out.println("You have " + guessesLeft + " incorrect guesses left");

        System.out.print("Available Letters: ");
        for (int k = 0; k < 25; k++) {
            System.out.print(availableLetters[k] + " ");
        }

        System.out.println();
        System.out.println();

        for (int i = 0; i < word.length(); i++) {
            System.out.print(visibleLetters[i] + " ");
        }
        System.out.println();
        System.out.println();
        System.out.println("Enter your guess:");
    }

    /**
     * checks to see if there are any dashes ( - ) left in the word being guessed
     * @param visibleLetters shows letters in the word being guessed if the user has guessed them
     * @param word the word that the user is trying to guess
     * @return true if the user has guessed the word correctly yet, false if otherwise
     */
    public static boolean gameCheckWin(char[] visibleLetters, String word) {

        boolean gameOver;
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (visibleLetters[i] == '-')
                count++;
        }

        if (count == 0) {
            gameOver = true;
            for (int i = 0; i < word.length(); i++) {
                System.out.print(visibleLetters[i] + " ");
            }
            System.out.println();
            System.out.println("You got it, good job!");
        } else
            gameOver = false;
        return gameOver;
    }

    /**
     *
     * @param availableLetters list of letters the user can still guess
     * @param visibleLetters shows letters in the word being guessed if the user has guessed them
     * @param word the word that the user is trying to guess
     * @return true if the user guesses a letter that is in the word
     */
    public static boolean checkGuess(char[] availableLetters, char[] visibleLetters, String word){
        boolean correctGuess = false;
        Scanner kb = new Scanner(System.in);
        boolean isUsed = true;
        char userGuess = ' ';

        while (isUsed) {

            userGuess = kb.nextLine().charAt(0);
            isUsed = true;

            for (int i = 0; i < 26; i++) {
                if (userGuess == availableLetters[i]) {
                    isUsed = false;
                    break;
                }
            }
            System.out.println("That has already been guessed, enter a different letter");
        }

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == userGuess) {

                visibleLetters[i] = userGuess;
                correctGuess = true;
            }
        }

        for(int j = 0; j < availableLetters.length; j++) {
            if (availableLetters[j] == userGuess)
                availableLetters[j] = '-';
        }

        if(correctGuess == true)
            System.out.println("Nice! " + userGuess + " is in the word");

        return correctGuess;
    }

}


