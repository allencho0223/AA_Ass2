import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {

    public HashMap<String, ArrayList<String>> attValSet = new HashMap<String, ArrayList<String>>();

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

                HashMap<String, String> tempAttValSet = new HashMap<String, String>();

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
        
    }


}//end of config class
