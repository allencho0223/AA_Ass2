import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Binary-search based guessing player. This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player {

    public Config c = new Config();
    public Random r = new Random();
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
        c.generateBinaryDecisionTree(gameFilename);

        for (Person person : c.personList) {
            if (person.getName().equals(chosenName)) {
                chosenPerson = new Person(person.getName(), person.getPersonaAttValSet());
            }
        }

        alivePerson = c.personList.size();
        
//        c.generateBinaryDecisionTree();
    } // end of BinaryGuessPlayer()

    public Guess guess() {

        
        

        // placeholder, replace
        return new Guess(Guess.GuessType.Person, "", "Placeholder");
    } // end of guess()

    public boolean answer(Guess currGuess) {

        // placeholder, replace
        return false;
    } // end of answer()

    public boolean receiveAnswer(Guess currGuess, boolean answer) {

        // placeholder, replace
        return true;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
