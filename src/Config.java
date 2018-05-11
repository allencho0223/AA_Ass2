import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Config {

    public String[] ATTRIBUTES = new String[] { "hairLength", "glasses", "facial", "eyeColor", "pimples", "hat",
            "hairColor", "noseShape", "faceShape" };

    public List<String> attributeList = Arrays.asList(ATTRIBUTES);

    public Map<String, ArrayList<String>> attValSet = new HashMap<String, ArrayList<String>>();

    public static void main(String[] args) {
        Config c = new Config();
        Random r = new Random();
        c.configFileLoader("game1.config");
        String guessAttribute = "";
        String guessValue = "";
        ArrayList<String> tempValues = new ArrayList<String>();

        guessAttribute = c.attributeList.get(r.nextInt(c.attributeList.size()));

        for (String value : c.attValSet.get(guessAttribute)) {
            tempValues.add(value);
        }

        guessValue = c.attValSet.get(guessAttribute).get(r.nextInt(tempValues.size()));

        System.out.println(guessAttribute);
        System.out.println(guessValue);

        
    }

    public Persona[] configFileLoader(String gameFileName) {

        BufferedReader configReader = null;
        BufferedReader valueReader = null;
        Persona[] persona = null;
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

            persona = new Persona[playerNum];

            playerNum = 0;

            // Skip attribute example lines
            for (int i = 0; i < 9; i++) {
                String[] tempString = new String[10];
                ArrayList<String> valueList = new ArrayList<String>();

                tempString = valueReader.readLine().split("\\s");
                for (int j = 1; j < tempString.length; j++) {
                    valueList.add(tempString[j]);
                }
                attValSet.put(tempString[0], valueList);
            }

            for (Map.Entry<String, ArrayList<String>> entry : attValSet.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }

            while ((configData = valueReader.readLine()) != null) {

                String[] attribute = new String[2];
                /**
                 * value array: [0]: hairLength [1]: glasses [2]: facialHair [3]: eyeColor [4]:
                 * pimples [5]: hat [6]: hairColor [7]: noseShape [8]: faceShape
                 */
                String[] value = new String[9];

                String name = valueReader.readLine();
                if (name == null) {
                    break;
                }
                for (int i = 0; i < value.length; i++) {
                    attribute = valueReader.readLine().split("\\s");
                    value[i] = attribute[1];
                }
                // Generate personas based on the number of written data of config file
                persona[playerNum++] = new Persona(name, value[0], value[1], value[2], value[3], value[4], value[5],
                        value[6], value[7], value[8]);

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
        return persona;

    }

}
