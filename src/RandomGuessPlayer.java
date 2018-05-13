import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Random guessing player. This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player {

    public Config c = new Config();
    public Random r = new Random();
    public int alivePersona = 0;
    public String chosenPersona = "";

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
    @SuppressWarnings("static-access")
    public RandomGuessPlayer(String gameFilename, String chosenName) throws IOException {

        c.configFileLoader(gameFilename);

        for (String name : c.persona.keySet()) {
            if (name.equals(chosenName)) {
                chosenPersona = name;
                break;
            }
        }

        if (chosenPersona == null) {
            System.out.println("Invalid player name - Not selected!");
            return;
        } else {
            System.out.println("chosen persona: " + chosenPersona);
        }

        alivePersona = c.persona.size();

    } // end of RandomGuessPlayer()

    @SuppressWarnings("static-access")
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

            List<String> keys = new ArrayList<String>(c.persona.keySet());

            // Persona's name - random
            guessValue = keys.get(r.nextInt(keys.size()));

        } else {
            /**
             * Else - guess type is attribute: attribute == the attribute of persona value
             * == value corresponding to the attribute
             */
            guessAttribute = c.attributeList.get(r.nextInt(c.attributeList.size()));

            for (HashMap.Entry<String, ArrayList<String>> entry : c.attValSet.entrySet()) {
                if (entry.getKey().equals(guessAttribute)) {
                    tempValues = entry.getValue();
                }
            }

            guessValue = tempValues.get(r.nextInt(tempValues.size()));

        }

        return new Guess(guessType, guessAttribute, guessValue);

    } // end of guess()

    @SuppressWarnings("static-access")
    public boolean answer(Guess currGuess) {

        int isDead = 0;

        // If mType == Person
        if (currGuess.getType() == Guess.GuessType.Person) {

            // Compare value with chosen person's name
            if (chosenPersona == currGuess.getValue()) {
                return true;
            }
            return false;

            // Else - mType == Attribute
        } else {

            // Check how many personas have the value

            ArrayList<String> deadPersona = new ArrayList<String>();

            for (HashMap.Entry<String, HashMap<String, String>> entry : c.persona.entrySet()) {
                System.out.println("getAtt: " + entry.getValue().containsKey(currGuess.getAttribute()));
                System.out.println("getVal: " + entry.getValue().containsValue(currGuess.getValue()));
                if (entry.getValue().containsKey(currGuess.getAttribute())
                        && entry.getValue().containsValue(currGuess.getValue())) {
                    deadPersona.add(entry.getKey());
                }
            }

            for (int i = 0; i < deadPersona.size(); i++) {
                System.out.println("dead persona: " + deadPersona.get(i));
            }

            for (Iterator<Map.Entry<String, HashMap<String, String>>> it = c.persona.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, HashMap<String, String>> entry = it.next();
                for (int i = 0; i < deadPersona.size(); i++) {
                    if (entry.getKey().equals(deadPersona.get(i))) {
                        it.remove();
                        isDead++;
                    }
                }
            }

            System.out.println("Alive persona: " + c.persona.size());
            
            for (Map.Entry<String, HashMap<String, String>> alivePersona : c.persona.entrySet()) {
                System.out.println("alive persona: " + alivePersona.getKey());
            }
            System.out.println();
            if (isDead > 0) {
                return true;
            } else {
                return false;
            }
        }
    } // end of answer()

    public boolean receiveAnswer(Guess currGuess, boolean answer) {

        if (currGuess.getValue() == chosenPersona) {
            answer = true;
        } else {
            answer = false;
        }
        return answer;
    } // end of receiveAnswer()

} // end of class RandomGuessPlayer
