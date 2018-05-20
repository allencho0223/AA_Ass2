import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
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
    public static int alivePersona = 0;
    public Persona chosenPersona;

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

        c.configFileLoader(gameFilename);

        for (Persona persona : c.personas) {
            if (persona.getName().equals(chosenName)) {
                chosenPersona = new Persona(persona.getName(), persona.getPersonaAttValSet());
            }
        }

        alivePersona = c.personas.size();

    } // end of RandomGuessPlayer()

    @SuppressWarnings("static-access")
    public Guess guess() {

        // Make a guess
        String guessAttribute = "";
        String guessValue = "";
        ArrayList<Guess.GuessType> tempGuessType = new ArrayList<Guess.GuessType>();
        Guess.GuessType guessType = null;

        /**
         * Value. if mType = Attribute, then it is the value of the associated
         * attribute). if mType = Person, then this is the guessed person's name.
         */

        for (Guess.GuessType type : guessType.values()) {
            tempGuessType.add(type);
        }

        if (alivePersona == 1) {
            guessType = Guess.GuessType.Person;
        } else {
            guessType = tempGuessType.get(r.nextInt(tempGuessType.size()));
        }

        /**
         * If guess type is person: att == "" value == person's name
         */
        if (guessType == Guess.GuessType.Person) {

            guessAttribute = "";

            if (alivePersona == 1) {
                // get the last person's name - must be the chosen persona
                for (Persona persona : c.personas) {
                    guessValue = persona.getName();
                }
            } else {

                // Persona's name - random
                guessValue = c.personas.get(r.nextInt(c.personas.size())).getName();

            }
        } else {
            /**
             * Else - guess type is attribute: attribute == the attribute of persona value
             * == value corresponding to the attribute
             */

            // Get random attribute
            guessAttribute = c.attributeList.get(r.nextInt(c.attributeList.size()));

            // Check which values does the attribute have from the attribute value pair
            // instruction
            ArrayList<String> tempValues = new ArrayList<String>();

            for (HashMap.Entry<String, ArrayList<String>> entry : c.attValSet.entrySet()) {
                if (entry.getKey().equals(guessAttribute)) {
                    tempValues = entry.getValue();
                }
            }

            // Get a random value corresponding to the chosen attribute
            guessValue = tempValues.get(r.nextInt(tempValues.size()));
        }

        return new Guess(guessType, guessAttribute, guessValue);

    } // end of guess()

    public boolean answer(Guess currGuess) {

        // If mType == Person
        if (currGuess.getType().equals(Guess.GuessType.Person)) {

            // Compare value with chosen person's name
            // If correct, return yes
            if (currGuess.getValue().equals(chosenPersona.getName())) {
                return true;
            }

            // If they do not match, return false
            return false;

            // Else - mType == Attribute
        } else {

            // If guessed attribute and value match the chosen persona's, return true
            for (Entry<String, String> entry : chosenPersona.getPersonaAttValSet().entrySet()) {
                if (currGuess.getAttribute().equals(entry.getKey()) && currGuess.getValue().equals(entry.getValue())) {
                    System.out.println("currGuessAtt: " + currGuess.getAttribute());
                    System.out.println("entryAtt: " + entry.getKey());
                    System.out.println("currGuessVal: " + currGuess.getValue());
                    System.out.println("entryVal: " + entry.getValue() + "\n\n");
                    return true;
                }
            }

            // Else, return false
            return false;
        }
    } // end of answer()

    public boolean receiveAnswer(Guess currGuess, boolean answer) {

        /**
         * 
         * There are 2 cases as regards the guess phase 1. If the player chose mType as
         * person, and guessed the opponent's player correctly, answer == true, and
         * finish the game, otherwise, answer == false 2. If the player chose mType as
         * attribute, and guessed the opponent's player value correctly, answer == true,
         * and remove personas who don't have the value, otherwise, answer == false
         * 
         */

        // If the answer is true
        if (answer) {

            ArrayList<String> deadPersona = new ArrayList<String>();

            // Kill personas who don't have guessed value
            if (currGuess.getType().equals(Guess.GuessType.Attribute)) {
                for (Persona persona : c.personas) {
                    for (Entry<String, String> entry : persona.getPersonaAttValSet().entrySet()) {
                        if (entry.getKey().equals(currGuess.getAttribute())
                                && !entry.getValue().equals(currGuess.getValue())) {
                            System.out.println(persona.getName() + " is dead in true");
                            System.out.println(entry.getKey() + " is the att in true");
                            System.out.println(entry.getValue() + " is the val in true\n\n");
                            deadPersona.add(persona.getName());
                        }
                    }
                }

                for(int i = 0; i < c.personas.size();i++){
                    for (int j = 0; j < deadPersona.size(); j++) {
                        if(c.personas.get(i).getName().equals(deadPersona.get(j))){
                            c.personas.remove(i--);
                            if (i < 0) {
                                i = 0;
                            }
                            alivePersona--;
                        }
                    }
                }

                return false;
            }

            // If the player correctly guessed the opponent's chosen persona, return true
            return true;

            // If the answer is false
        } else {

            // If the player has incorrect guess on the opponent's player,
            // remove the incorrect guessed persona from the list and return false
            if (currGuess.getType().equals(Guess.GuessType.Person)) {

                String deadPersona = "";
                for (Persona persona : c.personas) {
                    if (persona.getName().equals(currGuess.getValue())) {
                        deadPersona = persona.getName();
                        System.out.println(deadPersona + " will be dead soon");
                        break;
                    }
                }

                for(int i = 0; i < c.personas.size();i++){
                    for (int j = 0; j < 1; j++) {
                        if(c.personas.get(i).getName().equals(deadPersona)){
                            c.personas.remove(i--);
                            if (i < 0) {
                                i = 0;
                            }
                            alivePersona--;
                        }
                    }
                }

                // If the player has incorrect guess on the opponent's value
                // Remove the personas who don't have the value
            } else {

                // Kill personas who don't have guessed value that chosen persona has
                ArrayList<String> deadPersona = new ArrayList<String>();

                for (Persona persona : c.personas) {
                    for (Entry<String, String> entry : persona.getPersonaAttValSet().entrySet()) {
                        if (entry.getKey().equals(currGuess.getAttribute())
                                && entry.getValue().equals(currGuess.getValue())) {
                            System.out.println(persona.getName() + " is dead in false");
                            System.out.println(entry.getKey() + " is the att in false");
                            System.out.println(entry.getValue() + " is the val in false\n\n");
                            deadPersona.add(persona.getName());
                        }
                    }
                }

                for(int i = 0; i < c.personas.size();i++){
                    for (int j = 0; j < deadPersona.size(); j++) {
                        if(c.personas.get(i).getName().equals(deadPersona.get(j))){
                            c.personas.remove(i--);
                            if (i < 0) {
                                i = 0;
                            }
                            alivePersona--;
                        }
                    }
                }

            }
            return false;
        }
    } // end of receiveAnswer()

} // end of class RandomGuessPlayer
