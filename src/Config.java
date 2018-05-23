import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Config {

    public static String[] ATTRIBUTES = new String[] { "hairLength", "glasses", "facialHair", "eyeColor", "pimples",
            "hat", "hairColor", "noseShape", "faceShape" };

    public List<String> attributeList = Arrays.asList(ATTRIBUTES);

    public LinkedHashMap<String, ArrayList<String>> attValSet = new LinkedHashMap<String, ArrayList<String>>();

    public ArrayList<Person> personList = new ArrayList<Person>();

    public int playerNum = 0;

    public int noOfAttribute = 0;

    public void configFileLoader(String gameFileName) {

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

            while ((configData = configReader.readLine()) != null) {

                if (lineNo == configLine * (playerNum + 1)) {
                    playerNum++;
                }
                lineNo++;
            }

            while ((configData = attributeReader.readLine()) != null) {
                if (configData.isEmpty()) {
                    break;
                }
                noOfAttribute++;
            }

            // Add attribute and value set instruction
            for (int i = 0; i < noOfAttribute; i++) {
                String[] tempString = new String[10];
                ArrayList<String> valueList = new ArrayList<String>();

                tempString = valueReader.readLine().split("\\s");
                for (int j = 1; j < tempString.length; j++) {
                    valueList.add(tempString[j]);
                }
                attValSet.put(tempString[0], valueList);
            }

            while ((configData = valueReader.readLine()) != null) {

                String[] attVal = new String[2];

                // Read name
                String name = valueReader.readLine();

                if (name == null) {
                    break;
                }

                LinkedHashMap<String, String> tempAttValSet = new LinkedHashMap<String, String>();

                for (int i = 0; i < noOfAttribute; i++) {
                    attVal = valueReader.readLine().split("\\s");
                    tempAttValSet.put(attVal[0], attVal[1]);
                }

                Person tempPerson = new Person(name, tempAttValSet);

                personList.add(tempPerson);

            }

        } catch (IOException ioe) {
            System.err.println(ioe.getLocalizedMessage());
        } finally {
            try {
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
        // return persona;
    }

    public LinkedHashMap<String, String> generateBinaryDecisionTree(String gameFileName) {

        BufferedReader attValReader = null;
        LinkedHashMap<String, Integer> binaryAttValHashMap = new LinkedHashMap<String, Integer>();
        LinkedHashMap<String, String> returnAttVal = new LinkedHashMap<String, String>(); 
        int attValLine = 2;
        String[] tempAttVal = new String[attValLine];

        try {
            attValReader = new BufferedReader(new FileReader(gameFileName));

            // Skip att val pair instruction lines
            for (int i = 0; i < noOfAttribute + 1; i++) {
                attValReader.readLine();
            }

            // Run a few times based on the persona number in the config file
            for (int i = 0; i < playerNum; i++) {
                // Skip player name
                attValReader.readLine();
                // Store att val pair into binaryAttVal LinkedHashMap
                for (int j = 0; j < noOfAttribute; j++) {
                    tempAttVal = attValReader.readLine().split("\\s");
                    BinaryHelper bh = new BinaryHelper(tempAttVal[0], tempAttVal[1]);
                    
                    if (binaryAttValHashMap.containsKey(bh.toString())) {
                        binaryAttValHashMap.replace(bh.toString(), binaryAttValHashMap.get(bh.toString()) + 1);
                    } else {
                        binaryAttValHashMap.put(bh.toString(), 1);
                    }
                    
                }
                // Skip empty line
                attValReader.readLine();
            }
            
            int idealCount = playerNum / 2;
            
//            for (Entry<String, Integer> entry : binaryAttValHashMap.entrySet()) {
//                
//                while (true) {
//                    if (entry.getValue() == idealCount) {
//                        tempAttVal = entry.getKey().split("\\s");
//                        returnAttVal.put(tempAttVal[0], tempAttVal[1]);
//                        break;
//                    } else if (entry.getValue() == idealCount - 1) {
//                        
//                        
//                    } else if (entry.getValue() == idealCount + 1) {
//                        
//                    } else {
//                        idealCount--;
//                    }
//                }
//            }

        } catch (IOException e) {
            System.err.println("IOException in generateBinaryDecisionTree method");
        }
        
        return returnAttVal;

    }

}
