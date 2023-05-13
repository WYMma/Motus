# Motus - Console-based Word Game

Motus is a console-based word game where the player has to guess a randomly selected word from a custom dictionary file. The game allows the player to enter words of a specified length until they either guess the hidden word or exhaust their allowed number of attempts. After each guess, the computer provides feedback by indicating which letters are in the hidden word and whether they are in the correct position or not.

This mini-project was created as part of a programming assignment. The project consists of three classes: Dictionnaire, Jeu, and Motus.

## Classes

### Dictionnaire

The Dictionnaire class models and manages the custom dictionary file used in the game. The class has the following attributes:

- `tailleMot`: the common length of words in the dictionary
- `nbMots`: the number of words in the dictionary
- `collectionMots`: a collection used to store the words from the file

The class has the following methods (besides the constructor and accessors/mutators):

- `rechercheMot`: searches for a given word in the dictionary
- `ajoutMot`: adds a word to the dictionary. The word must contain only uppercase alphabetical letters, respect the common length of words in the dictionary, not already exist in the dictionary, and be inserted in alphabetical order.
- `supprimeMot`: searches for a word in the dictionary and deletes it if it exists.

All these methods return a boolean value of `true` if the operation was successful and `false` otherwise.

### Jeu

The Jeu class contains the following attributes:

- `nbEssais`: the number of attempts allowed per game
- `nbEssaisRestants`: the number of remaining attempts for the player
- `nbPartGagnees`: the number of games won by the player
- `nbPartPerdues`: the number of games lost by the player
- `motCache`: the hidden word that the player needs to guess
- `motTrouve`: the word containing the correctly guessed letters in the right positions (un-guessed letters are represented by '*')

Note: some attributes must be static.

The class has the following methods (besides the accessors and mutators):

- Constructor: initializes the game by selecting a random word from the dictionary
- `testMot`: tests the word entered by the player by first verifying its validity (its length should be correct and it should consist only of alphabetical letters). This method also compares the entered word with the hidden word and adds correctly guessed letters to the `motTrouve` attribute. This method returns `true` if `motTrouve = motCache`.

### Motus

The Motus class is the main class of the project and contains the `main` method that takes a string array `args` as a parameter. `args[0]` can have the value "config" if the user wants to configure the dictionary or "jeu" if they want to start a game. `args[1]` represents the length of the hidden word.

In the dictionary configuration part, the user must choose the action to perform (search, add, or delete a word). In the game part, the user must enter the number of allowed attempts before starting the game. They can play multiple consecutive games.

To run the game, invoke the appropriate methods.

## Conclusion

This mini-project is a simple implementation of the Motus game in console mode. It demonstrates the use of classes and objects in object-oriented programming and the manipulation of files in Java. The project can be extended further to include a graphical user interface, a score system, and a more extensive dictionary file.

## Author

* **Yassin Manita** - *Computer Science Student* - [Yassin Manita](https://github.com/WYMma)

Check out my [LinkedIn](https://tn.linkedin.com/in/yassin-manita12) for more information.
