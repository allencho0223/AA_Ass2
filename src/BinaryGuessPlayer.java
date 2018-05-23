import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map.Entry;

/**
 * Binary-search based guessing player. This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player {

    public Config c = new Config();
    public Random r = new Random();
    public ArrayList<String> returnedAttValSet = new ArrayList<String>();
    public static int alivePerson = 0;
    public Person chosenPerson;

    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename
     *            Filename of game configuration.
     * @param chosenName
     *            Name of the chosen person for this player.
     * @throws IOException
     *             If there are IO issues with loading of gameFilename. Note you can
     *             handle IOException within the constructor and remove the "throws
     *             IOException" method specification, but make sure your
     *             implementation exits gracefully if an IOException is thrown.
     */
    public BinaryGuessPlayer(String gameFilename, String chosenName) throws IOException {

        c.configFileLoader(gameFilename);

        for (Person person : c.personList) {
            if (person.getName().equals(chosenName)) {
                chosenPerson = new Person(person.getName(), person.getPersonAttValSet());
            }
        }
        System.out.println("chosenPerson: " + chosenPerson.getName());
        alivePerson = c.personList.size();

        // returnedAttValSet = c.generateBinaryDecisionTree(gameFilename, alivePerson);

    } // end of BinaryGuessPlayer()

    //
    public Guess guess() {

        Guess.GuessType guessType = null;
        String guessAttribute = "";
        String guessValue = "";

        returnedAttValSet = c.generateBinaryDecisionTree(c.personList, alivePerson);
        // Guess attribute
        if (alivePerson != 1) {

            guessType = Guess.GuessType.Attribute;

            guessAttribute = returnedAttValSet.get(0);

            guessValue = returnedAttValSet.get(1);

            // Get the last person's name only if there is 1 person left
        } else {

            guessType = Guess.GuessType.Person;

            guessAttribute = "";

            for (Person person : c.personList) {
                guessValue = person.getName();
            }
        }

        // placeholder, replace
        return new Guess(guessType, guessAttribute, guessValue);
    } // end of guess()

    public boolean answer(Guess currGuess) {

        // If mType == Person
        if (currGuess.getType().equals(Guess.GuessType.Person)) {
            if (currGuess.getValue().equals(chosenPerson.getName())) {
                return true;
            }
            return false;

            // Else, mType == Attribute
        } else {

            // If guessed attribute-value pair matches chosen person's one, return true
            for (Entry<String, String> entry : chosenPerson.getPersonAttValSet().entrySet()) {
                if (currGuess.getAttribute().equals(entry.getKey()) && currGuess.getValue().equals(entry.getValue())) {
                    return true;
                }
            }

            // If guessed attribute-value pair doesn't match chosen' person's one, return
            // false
            return false;
        }
    } // end of answer()

    public boolean receiveAnswer(Guess currGuess, boolean answer) {

        if (answer) {
            // If mType == Person
            // Always return true because the guessed person is the last person left in the list
            if (currGuess.getType().equals(Guess.GuessType.Person)) {
                System.out.println("guessed person: " + currGuess.getValue());
                return true;

                // Else, mType == Attribute, and the chosen person has guessed attribute-value pair
                // Remove people who don't have the guessed attribute-value pair from the list
            } else {

                ArrayList<String> deadPerson = new ArrayList<String>();

                // Remove personList who don't have guessed value
                if (currGuess.getType().equals(Guess.GuessType.Attribute)) {
                    for (Person person : c.personList) {
                        for (Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
                            if (entry.getKey().equals(currGuess.getAttribute())
                                    && !entry.getValue().equals(currGuess.getValue())) {
                                deadPerson.add(person.getName());
                            }
                        }
                    }

                    for (int i = 0; i < c.personList.size(); i++) {
                        for (int j = 0; j < deadPerson.size(); j++) {
                            if (c.personList.get(i).getName().equals(deadPerson.get(j))) {
                                c.personList.remove(i--);
                                if (i < 0) {
                                    i = 0;
                                }
                                alivePerson--;
                            }
                        }
                    }

                    return false;
                }

            }
            // False, meaning that chosen person doesn't have the guessed attribute-value
            // pair
            // Hence, remove people who have the guessed attribute-value pair from the list
        } else {
            // Kill personList who have guessed value that chosen person doesn't have
            ArrayList<String> deadPerson = new ArrayList<String>();

            for (Person person : c.personList) {
                for (Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
                    if (entry.getKey().equals(currGuess.getAttribute())
                            && entry.getValue().equals(currGuess.getValue())) {
                        deadPerson.add(person.getName());
                    }
                }
            }

            for (int i = 0; i < c.personList.size(); i++) {
                for (int j = 0; j < deadPerson.size(); j++) {
                    if (c.personList.get(i).getName().equals(deadPerson.get(j))) {
                        c.personList.remove(i--);
                        if (i < 0) {
                            i = 0;
                        }
                        alivePerson--;
                    }
                }
            }

        }
        return false;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
