import java.io.IOException;
import java.util.Random;

/**
 * Random guessing player. This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player {

    public Persona[] personas = null;
    public Persona chosenPersona = null;
    public Config config = new Config();
    public Random r = new Random();
    
    public String[] att = new String[] { "hairLength", "glasses", "facialHair", "eyeColor", "pimples", "hat",
            "hairColor", "noseShape", "faceShape" };

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
    public RandomGuessPlayer(String gameFilename, String chosenName) throws IOException {
        // Load and assign data into persona primitive array
        personas = config.configFileLoader(gameFilename);

        // Ascertain if chosenName exists in the persona list
        for (int i = 0; i < personas.length; i++) {
            if ((personas[i].identifyPersona("name").contains(chosenName))) {
                chosenPersona = personas[i];
            }
        }

    } // end of RandomGuessPlayer()

    public Guess guess() {
        String guessAttribute = "";
        String guessValue = "";
        
        
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

} // end of class RandomGuessPlayer
