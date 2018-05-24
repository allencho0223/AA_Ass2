import java.util.ArrayList;
import java.util.HashMap;

public class BinaryHelper {
    
    private String attribute ="";
    private String value ="";
    
    public BinaryHelper() {};
    
    
    public BinaryHelper(String attribute, String value) {
        this.setAttribute(attribute);
        this.setValue(value);
    }
    
    public String getAttribute() {
        return attribute;
    }
    
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return attribute + " " + value;
    }
    
    
    
    public ArrayList<String> generateBinaryDecisionTree(ArrayList<Person> personList, int playerNum) {

        HashMap<String, Integer> binaryAttValHashMap = new HashMap<String, Integer>();
        ArrayList<String> returnIdealAttVal = null;
        
        System.out.println("BINARY: alive person number = " + playerNum);
        
        for (Person person: personList) 
        {
            for (HashMap.Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
               setAttribute(entry.getKey());
               setValue(entry.getValue());
                
                if (binaryAttValHashMap.containsKey(this.toString())) 
                    binaryAttValHashMap.replace(this.toString(), binaryAttValHashMap.get(this.toString()) + 1);
                else 
                    binaryAttValHashMap.put(this.toString(), 1);
                
            }
        }

      returnIdealAttVal = getIdealAttVal(binaryAttValHashMap, playerNum); 
      return returnIdealAttVal;

    }
    
    
    public ArrayList<String> getIdealAttVal(HashMap<String, Integer> binaryAttValHashMap, int playerNum) 
    {
    	ArrayList<String> returnAttVal = new ArrayList<String>();
    	int attValLine = 2;
        String[] tempAttVal = new String[attValLine];
    	int idealCount = playerNum / 2;
        boolean isSetFound = false;

        for (int i = 0; i < playerNum; i++) {
            for (HashMap.Entry<String, Integer> entry : binaryAttValHashMap.entrySet()) 
            {
                if (entry.getValue() == idealCount) 
                {
                    tempAttVal = entry.getKey().split("\\s");
                    returnAttVal.add(tempAttVal[0]);
                    returnAttVal.add(tempAttVal[1]);
                    isSetFound = true;
                    break;
                }
            }
            if (isSetFound) 
                break;
            else 
                idealCount--;
           
        }
        
        return returnAttVal;
        
    }
    
    
    
    

}
