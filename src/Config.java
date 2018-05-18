import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Config {

    public static String[] ATTRIBUTES = new String[] { "hairLength", "glasses", "facialHair", "eyeColor", "pimples",
            "hat", "hairColor", "noseShape", "faceShape" };

    public List<String> attributeList = Arrays.asList(ATTRIBUTES);

    public HashMap<String, ArrayList<String>> attValSet = new HashMap<String, ArrayList<String>>();
    
    public ArrayList<Persona> personas = new ArrayList<Persona>();
    
    public int playerNum = 0;
    
    public void configFileLoader(String gameFileName) {

        BufferedReader configReader = null;
        BufferedReader valueReader = null;
        String configData = "";
        int lineNo = 1;
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

            while ((configData = valueReader.readLine()) != null) {

                String[] attVal = new String[2];

                // Read name
                String name = valueReader.readLine();

                if (name == null) {
                    break;
                }
                
                LinkedHashMap<String, String> tempAttValSet = new LinkedHashMap<String, String>(); 

                for (int i = 0; i < 9; i++) {
                    attVal = valueReader.readLine().split("\\s");
                    tempAttValSet.put(attVal[0], attVal[1]);
                }
                
                Persona tempPersona = new Persona(name, tempAttValSet);
                
                personas.add(tempPersona);

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
