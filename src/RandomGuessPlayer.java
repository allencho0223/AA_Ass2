import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Random guessing player. This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player {

    public Persona[] personas = null;
    public List<Persona> personaList;
    public Persona chosenPersona = null;
    public Config config = new Config();
    public Random r = new Random();
    public int alivePersona = 0;

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

        personaList = new ArrayList<Persona>(Arrays.asList(personas));

        alivePersona = personas.length;
    } // end of RandomGuessPlayer()

    public Guess guess() {

        // Make a guess
        String guessAttribute = "";
        String guessValue = "";
        ArrayList<String> tempValues = new ArrayList<String>();
        ArrayList<Guess.GuessType> tempGuessType = new ArrayList<Guess.GuessType>();
        Guess.GuessType guessType = null;

        /**
         * Value. if mType = Attribute, then it is the value of the associated
         * attribute). if mType = Person, then this is the guessed person's name.
         */

        for (Guess.GuessType type : guessType.values()) {
            tempGuessType.add(type);
        }

        guessType = tempGuessType.get(r.nextInt(tempGuessType.size()));

        /**
         * If guess type is person: att == "" value == person's name
         */
        if (guessType == Guess.GuessType.Person) {

            guessAttribute = "";

            guessValue = personas[r.nextInt(personas.length)].name;

        } else {
            /**
             * Else - guess type is attribute: attribute == the attribute of persona value
             * == value corresponding to the attribute
             */
            guessAttribute = config.attributeList.get(r.nextInt(config.attributeList.size()));

            for (String value : config.attValSet.get(guessAttribute)) {
                tempValues.add(value);
            }

            guessValue = config.attValSet.get(guessAttribute).get(r.nextInt(tempValues.size()));

        }

        return new Guess(guessType, guessAttribute, guessValue);

    } // end of guess()

    public boolean answer(Guess currGuess) {

        String chosenValue = "";
        int isRemoved = 0;
        // If mType == Person
        if (currGuess.getType() == Guess.GuessType.Person) {

            // Compare value with chosen person's name
            if (chosenPersona.name == currGuess.getValue()) {
                return true;
            }
            return false;

            // Else - mType == Attribute
        } else {

            // Check how many personas have the value
            for (int i = 0; i < personas.length; i++) {
                // Check how many personas have the value corresponding to the attribute
                chosenValue = personas[i].identifyPersona(currGuess.getAttribute());
                if (chosenValue == currGuess.getValue()) {
                    personaList.remove(i--);
                    alivePersona--;
                    isRemoved++;
                }
            }
            if (isRemoved > 0) {
                return true;
            } else {
                return false;
            }
        }
    } // end of answer()

    public boolean receiveAnswer(Guess currGuess, boolean answer) {
        
        if (currGuess.getValue() == chosenPersona.name) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    } // end of receiveAnswer()

} // end of class RandomGuessPlayer
