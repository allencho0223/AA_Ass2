import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {

    // Declare field (global) variables and objects for use
    public HashMap<String, ArrayList<String>> attValSet = new HashMap<String, ArrayList<String>>();
    public ArrayList<Person> personList = new ArrayList<Person>();
    public int playerNum = 0;
    public int noOfAttribute = 0;

    public void configFileLoader(String gameFileName) {

        // declaring BufferedReader to read the gameFile
        BufferedReader configReader = null;
        BufferedReader valueReader = null;
        BufferedReader attributeReader = null;
        String configData = "";
        int lineNo = 1;
        int configLine = 11;

        try {
            configReader = new BufferedReader(new FileReader(gameFileName));
            valueReader = new BufferedReader(new FileReader(gameFileName));
            attributeReader = new BufferedReader(new FileReader(gameFileName));

            // Check how many persons exist in the config file
            while ((configData = configReader.readLine()) != null) {

                if (lineNo == configLine * (playerNum + 1)) {
                    playerNum++;
                }
                lineNo++;
            }

            // Counting the number of attributes
            while ((configData = attributeReader.readLine()) != null) {
                if (configData.isEmpty()) {
                    break;
                }
                noOfAttribute++;
            }

            // Adding attribute and value set instructions
            for (int i = 0; i < noOfAttribute; i++) {
                String[] tempString = new String[10];
                ArrayList<String> valueList = new ArrayList<String>();

                tempString = valueReader.readLine().split("\\s");
                for (int j = 1; j < tempString.length; j++) {
                    valueList.add(tempString[j]);
                }
                attValSet.put(tempString[0], valueList);
            }

            // Read person details (name, attribute - value pair) and store them into Person class 
            while ((configData = valueReader.readLine()) != null) {

                String[] attVal = new String[2];

                // Read names from gameFile
                String name = valueReader.readLine();

                if (name == null) {
                    break;
                }

                // Temporary HashMap to store the Strings for the attributes and values from the gameFile
                HashMap<String, String> tempAttValSet = new HashMap<String, String>();

                // Iterate through the attributes and store the attribute value sets in the HashMap
                for (int i = 0; i < noOfAttribute; i++) {
                    attVal = valueReader.readLine().split("\\s");
                    tempAttValSet.put(attVal[0], attVal[1]);
                }

                // Create a person to add to the personList, 
                // using the name and temporary attribute value sets
                Person tempPerson = new Person(name, tempAttValSet);

                personList.add(tempPerson);

            }
        } catch (IOException ioe) {
            System.err.println(ioe.getLocalizedMessage());
        } finally {
            try {
                
                // Close streams after use
                if (configReader != null) {
                    configReader.close();
                }
                if (valueReader != null) {
                    valueReader.close();
                }
                if (attributeReader != null) {
                    attributeReader.close();
                }
            } catch (IOException ioe) {
                System.err.println(ioe.getLocalizedMessage());
            }
        }

    }

}// end of config class
