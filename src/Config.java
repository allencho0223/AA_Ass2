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
    }

//    public void generateBinaryDecisionTree() {
//
//        ArrayList<String> hairLengthArray = new ArrayList<String>();
//        ArrayList<String> glassesArray = new ArrayList<String>();
//        ArrayList<String> facialHairArray = new ArrayList<String>();
//        ArrayList<String> eyeColorArray = new ArrayList<String>();
//        ArrayList<String> pimplesArray = new ArrayList<String>();
//        ArrayList<String> hatArray = new ArrayList<String>();
//        ArrayList<String> hairColorArray = new ArrayList<String>();
//        ArrayList<String> noseShapeArray = new ArrayList<String>();
//        ArrayList<String> faceShapeArray = new ArrayList<String>();
//
//        // At the beginning, let's just think about hairLength att val set
//        for (int i = 0; i < personas.size(); i++) {
//
//            for (Entry<String, String> entry : personas.get(i).getPersonaAttValSet().entrySet()) {
//                switch (entry.getKey()) {
//                case "hairLength":
//                    hairLengthArray.add(entry.getValue());
//                    break;
//                case "glasses":
//                    glassesArray.add(entry.getValue());
//                    break;
//                case "facialHair":
//                    facialHairArray.add(entry.getValue());
//                    break;
//                case "eyeColor":
//                    eyeColorArray.add(entry.getValue());
//                    break;
//                case "pimples":
//                    pimplesArray.add(entry.getValue());
//                    break;
//                case "hat":
//                    hatArray.add(entry.getValue());
//                    break;
//                case "hairColor":
//                    hairColorArray.add(entry.getValue());
//                    break;
//                case "noseShape":
//                    noseShapeArray.add(entry.getValue());
//                    break;
//                case "faceShape":
//                    faceShapeArray.add(entry.getValue());
//                    break;
//                }
//            }
//
//        }
//
//        LinkedHashMap<String, Integer> mostTakenHairLength = getTheMostTakenValue(hairLengthArray);
//        LinkedHashMap<String, Integer> mostTakenGlasses = getTheMostTakenValue(glassesArray);
//        LinkedHashMap<String, Integer> mostTakenFacialHair = getTheMostTakenValue(facialHairArray);
//        LinkedHashMap<String, Integer> mostTakeneyeColor = getTheMostTakenValue(eyeColorArray);
//        LinkedHashMap<String, Integer> mostTakenpimples = getTheMostTakenValue(pimplesArray);
//        LinkedHashMap<String, Integer> mostTakenHat = getTheMostTakenValue(hatArray);
//        LinkedHashMap<String, Integer> mostTakenHairColor = getTheMostTakenValue(hairColorArray);
//        LinkedHashMap<String, Integer> mostTakenNoseShape = getTheMostTakenValue(noseShapeArray);
//        LinkedHashMap<String, Integer> mostTakenFaceShape = getTheMostTakenValue(faceShapeArray);
//
//        
//        
//        
//        
//        int[] sortedValue = new int[9];
//        
//        
//        sortedValue[0] = receiveMostTakenValue(mostTakenHairLength);
//        sortedValue[1] = receiveMostTakenValue(mostTakenGlasses);
//        sortedValue[2] = receiveMostTakenValue(mostTakenFacialHair);
//        sortedValue[3] = receiveMostTakenValue(mostTakeneyeColor);
//        sortedValue[4] = receiveMostTakenValue(mostTakenpimples);
//        sortedValue[5] = receiveMostTakenValue(mostTakenHat);
//        sortedValue[6] = receiveMostTakenValue(mostTakenHairColor);
//        sortedValue[7] = receiveMostTakenValue(mostTakenNoseShape);
//        sortedValue[8] = receiveMostTakenValue(mostTakenFaceShape);
//
//        for (int i = 0; i < sortedValue.length - 1; i++) {
//            int index = i;
//            for (int j = i + 1; j < sortedValue.length; j++) {
//                if (sortedValue[j] < sortedValue[index]) {
//                    index = j;// searching for lowest index
//                }
//            }
//            int smallerNumber = sortedValue[index];
//            sortedValue[index] = sortedValue[i];
//            sortedValue[i] = smallerNumber;
//        }
//        
//        for (int val : sortedValue) {
//            System.out.println(val);
//        }
//
//    }
//
//    public int receiveMostTakenValue(LinkedHashMap<String, Integer> linkedHashMap) {
//        int entryCount = 0;
//        int mostValue = 0;
//
//        for (Entry<String, Integer> entry : linkedHashMap.entrySet()) {
//            entryCount++;
//            if (entryCount == linkedHashMap.size()) {
//                mostValue = entry.getValue();
//            }
//        }
//
////        System.out.println(mostValue);
//        return mostValue;
//    }
//
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//    public LinkedHashMap<String, Integer> getTheMostTakenValue(ArrayList<String> array) {
//        int initCount = 1;
//
//        LinkedHashMap<String, Integer> mostTakenValue = new LinkedHashMap<String, Integer>();
//
//        for (String list : array) {
//            if (initCount == 1) {
//                mostTakenValue.put(list, initCount++);
//            } else {
//                if (mostTakenValue.containsKey(list)) {
//                    mostTakenValue.put(list, mostTakenValue.get(list).intValue() + 1);
//                } else {
//                    mostTakenValue.put(list, 1);
//                }
//            }
//        }
//
//        Object[] a = mostTakenValue.entrySet().toArray();
//        Arrays.sort(a, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                return ((Map.Entry<String, Integer>) o1).getValue()
//                        .compareTo(((Map.Entry<String, Integer>) o2).getValue());
//            }
//        });
//
//        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
//        for (Object e : a) {
//            sortedMap.put(((Map.Entry<String, Integer>) e).getKey(), ((Map.Entry<String, Integer>) e).getValue());
//        }
//
//        return sortedMap;
//    }

}
