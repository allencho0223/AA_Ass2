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

    public ArrayList<String> generateBinaryDecisionTree(ArrayList<Person> personList, int playerNum) {

        LinkedHashMap<String, Integer> binaryAttValHashMap = new LinkedHashMap<String, Integer>();
        int attValLine = 2;
        String[] tempAttVal = new String[attValLine];
        ArrayList<String> returnAttVal = new ArrayList<String>();
        
//        System.out.println("alive person number: " + playerNum);
//        for (Person person : personList) {
//            System.out.println("alive person: " + person.getName());
//        }
        
        for (int i = 0; i < personList.size(); i++) {
            for (Entry<String, String> entry : personList.get(i).getPersonAttValSet().entrySet()) {
                tempAttVal[0] = entry.getKey();
                tempAttVal[1] = entry.getValue();
                BinaryHelper bh = new BinaryHelper(tempAttVal[0], tempAttVal[1]);
                
                if (binaryAttValHashMap.containsKey(bh.toString())) {
                    binaryAttValHashMap.replace(bh.toString(), binaryAttValHashMap.get(bh.toString()) + 1);
                } else {
                    binaryAttValHashMap.put(bh.toString(), 1);
                }
            }
        }
        
//        for (Entry<String, Integer> entry : binaryAttValHashMap.entrySet()) {
//            System.out.println("att-val pair: " + entry.toString());
//            System.out.println("number: " + entry.getKey());
//        }
        
      int idealCount = playerNum / 2;
      boolean isSetFound = false;

      for (int i = 0; i < playerNum; i++) {
          for (Entry<String, Integer> entry : binaryAttValHashMap.entrySet()) {
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
      
      return returnAttVal;

    }

}
