import java.io.*;
import java.util.*;

public class Config {

    private static String[] ATTRIBUTES = new String[] {
      "hairLength",
      "glasses",
      "facial",
      "eyeColor",
      "pimples",
      "hat",
      "hairColor",
      "noseShape",
      "faceShape"
    };
    
    private static List<String> ATTRIBUTE_LIST = Arrays.asList(ATTRIBUTES);
    
    public static void main(String[] args) {
        
        // Main method is only used for testing purpose
        // Will be removed eventually
        // The file path could also be different
        configFileLoader("config/game1.config");
    }
    
    private static void configFileLoader(String gameFileName) {
        
        BufferedReader configReader = null;
        BufferedReader valueReader = null;
        Persona[] persona = null;
        String configData ="";
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
            
            persona = new Persona[playerNum];
            
            // Reset BufferedReader object for reuse
            
            playerNum = 0;
            
            // Skip attribute example lines
            for (int i = 0; i < 9; i++) {
                configData = valueReader.readLine();
            }
            
            while ((configData = valueReader.readLine()) != null) {
                String[] attribute = new String[2];
                
                /**
                 * value array:
                 * [0]: hairLength
                 * [1]: glasses
                 * [2]: facialHair
                 * [3]: eyeColor
                 * [4]: pimples
                 * [5]: hat
                 * [6]: hairColor
                 * [7]: noseShape
                 * [8]: faceShape
                 */
                String[] value = new String[9];
                
                String name = valueReader.readLine();
                
                for (int i = 0; i < value.length; i++) {
                    attribute = valueReader.readLine().split("\\s");
                    value[i] = attribute[1];
                }
                
                // Generate personas based on the number of written data of config file
                persona[playerNum++] = new Persona(name, value[0], value[1], value[2], 
                        value[3], value[4], value[5], value[6], value[7], value[8]);
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
        
    }

}
