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

    public RandomGuessPlayer(String gameFilename, String chosenName) throws IOException {

        c.configFileLoader(gameFilename);

        for (Person person : c.personList) {
            if (person.getName().equals(chosenName)) {
                chosenPerson = new Person(person.getName(), person.getPersonAttValSet());
            }
        }
        System.out.println("chosenPerson: " + chosenPerson.getName());
        alivePersona = c.personList.size();

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
                for (Person person : c.personList) {
                    guessValue = person.getName();
                }
            } else {

                // Persona's name - random
                guessValue = c.personList.get(r.nextInt(c.personList.size())).getName();

            }
        } else {
            /**
             * Else - guess type is attribute: attribute == the attribute of persona value
             * == value corresponding to the attribute
             */

            // Get random attribute
//            guessAttribute = c.attributeList.get(r.nextInt(c.attributeList.size()));
            
            

            // Check which values does the attribute have from the attribute value pair
            // instruction
            ArrayList<String> tempAttributes = new ArrayList<String>();
            ArrayList<String> tempValues = new ArrayList<String>();
            
            for (HashMap.Entry<String, ArrayList<String>> entry : c.attValSet.entrySet()) {
                tempAttributes.add(entry.getKey());
            }
            
            guessAttribute = tempAttributes.get(r.nextInt(tempAttributes.size()));
            

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
            if (currGuess.getValue().equals(chosenPerson.getName())) {
                return true;
            }

            // If they do not match, return false
            return false;

            // Else - mType == Attribute
        } else {

            // If guessed attribute and value match the chosen persona's, return true
            for (Entry<String, String> entry : chosenPerson.getPersonAttValSet().entrySet()) {
                if (currGuess.getAttribute().equals(entry.getKey()) && currGuess.getValue().equals(entry.getValue())) {
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
         * and remove personList who don't have the value, otherwise, answer == false
         * 
         */

        // If the answer is true
        if (answer) {

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

                for(int i = 0; i < c.personList.size();i++){
                    for (int j = 0; j < deadPerson.size(); j++) {
                        if(c.personList.get(i).getName().equals(deadPerson.get(j))){
                            c.personList.remove(i--);
                            if (i < 0) {
                                i = 0;
                            }
                            alivePersona--;
                        }
                    }
                }

                return false;
            }

            // If the player correctly guessed the opponent's chosen person, return true
            return true;

            // If the answer is false
        } else {

            // If the player has incorrect guess on the opponent's player,
            // Remove the incorrect guessed person from the list and return false
            if (currGuess.getType().equals(Guess.GuessType.Person)) {

                String deadPerson = "";
                for (Person person : c.personList) {
                    if (person.getName().equals(currGuess.getValue())) {
                        deadPerson = person.getName();
                        break;
                    }
                }

                for(int i = 0; i < c.personList.size();i++){
                    for (int j = 0; j < 1; j++) {
                        if(c.personList.get(i).getName().equals(deadPerson)){
                            c.personList.remove(i--);
                            if (i < 0) {
                                i = 0;
                            }
                            alivePersona--;
                        }
                    }
                }

                // If the player has incorrect guess on the opponent's value
                // Remove the personList who don't have the value
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

                for(int i = 0; i < c.personList.size();i++){
                    for (int j = 0; j < deadPerson.size(); j++) {
                        if(c.personList.get(i).getName().equals(deadPerson.get(j))){
                            c.personList.remove(i--);
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
