import java.util.ArrayList;
import java.util.HashMap;

// The BinaryHelper Class is used to create helpers that store Attribute and Value strings 
// This is used in order to help the BinaryGuessPlayer find the most ideal attribute and value pair 
// to eliminate half (or almost half) of the persons each round
public class BinaryHelper {

    private String attribute = "";
    private String value = "";

    public BinaryHelper() {

    }

    public BinaryHelper(String attribute, String value) {
        this.setAttribute(attribute);
        this.setValue(value);
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return attribute + " " + value;
    }

    // Generates the ideal attribute value set for the BinaryGuessPlayer
    public ArrayList<String> generateIdealBinaryGuess(ArrayList<Person> personList, int playerNum) {

        // HashMap stores the combined string of attribute and value as its key
        // The HashMap value keeps counts the number of attribute/value set repeats
        HashMap<String, Integer> attValCount = new HashMap<String, Integer>();
        ArrayList<String> returnIdealAttVal = null;

        // Iterate through all persons, and store their attribute value sets in the
        // BinaryHelper HashMap
        for (Person person : personList) {
            for (HashMap.Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
                this.setAttribute(entry.getKey());
                this.setValue(entry.getValue());

                // If the attribute value exists, then increase the count, else count remains at 1
                if (attValCount.containsKey(this.toString())) {
                    attValCount.replace(this.toString(), attValCount.get(this.toString()) + 1);
                } else {
                    attValCount.put(this.toString(), 1);
                }
            }
        }

        // return ideal attribute value set
        returnIdealAttVal = getIdealAttVal(attValCount, playerNum);
        return returnIdealAttVal;

    }

    // Returns the ideal attribute value set in order to remove half the persons
    // each round
    public ArrayList<String> getIdealAttVal(HashMap<String, Integer> binaryAttValHashMap, int playerNum) {
        // declarations
        ArrayList<String> returnAttVal = new ArrayList<String>();
        int attValLine = 2;
        String[] tempAttVal = new String[attValLine];
        int idealCount = playerNum / 2; // set ideal count to half the number of players
        boolean isSetFound = false;

        // for all the players remaining, if the ideal count is matched to a specific attribute/value set
        // if ideal count cannot be found, decrement ideal count and try again until ideal count is matched
        for (int i = 0; i < playerNum; i++) {
            for (HashMap.Entry<String, Integer> entry : binaryAttValHashMap.entrySet()) {
                if (entry.getValue() == idealCount) {
                    tempAttVal = entry.getKey().split("\\s");
                    returnAttVal.add(tempAttVal[0]);
                    returnAttVal.add(tempAttVal[1]);
                    isSetFound = true;
                    break;
                }
            }
            if (isSetFound) {
                break;
            } else {
                idealCount--;
            }
        }
        // return the attribute value as the next guess
        return returnAttVal;
    }
}
