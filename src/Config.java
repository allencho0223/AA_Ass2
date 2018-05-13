import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Config {

    public static String[] ATTRIBUTES = new String[] { "hairLength", "glasses", "facialHair", "eyeColor", "pimples", "hat",
            "hairColor", "noseShape", "faceShape" };

    public List<String> attributeList = Arrays.asList(ATTRIBUTES);

    public HashMap<String, ArrayList<String>> attValSet = new HashMap<String, ArrayList<String>>();

    public static HashMap<String, HashMap<String, String>> persona = new HashMap<String, HashMap<String, String>>();


    public void configFileLoader(String gameFileName) {

        BufferedReader configReader = null;
        BufferedReader valueReader = null;
        String configData = "";
        int lineNo = 1;
        int playerNum = 0;
        int configLine = 11;

        try {
            configReader = new BufferedReader(new FileReader(gameFileName));
            valueReader = new BufferedReader(new FileReader(gameFileName));

            while ((configData = configReader.readLine()) != null) {

                if (lineNo == configLine * (playerNum + 1)) {
                    playerNum++;
                }
                lineNo++;
            }

            // Add attribute and value set instruction
            for (int i = 0; i < 9; i++) {
                String[] tempString = new String[10];
                ArrayList<String> valueList = new ArrayList<String>();

                tempString = valueReader.readLine().split("\\s");
                for (int j = 1; j < tempString.length; j++) {
                    valueList.add(tempString[j]);
                }
                attValSet.put(tempString[0], valueList);
            }

            // 
            while ((configData = valueReader.readLine()) != null) {

                String[] attribute = new String[2];
                
                // Read name
                String name = valueReader.readLine();

                if (name == null) {
                    break;
                }

                // Store attribute and value pair into personaAttValSet
                HashMap<String, String> tempAttValSet = new HashMap<String, String>();
                for (int i = 0; i < 9; i++) {
                    attribute = valueReader.readLine().split("\\s");
                    tempAttValSet.put(attribute[0], attribute[1]);
                }
                persona.put(name, tempAttValSet);

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
            } catch (IOException ioe) {
                System.err.println(ioe.getLocalizedMessage());
            }
        }
        // return persona;
        return;

    }

}
