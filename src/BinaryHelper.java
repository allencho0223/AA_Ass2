
public class BinaryHelper {
    
    private String attribute ="";
    private String value ="";
    
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

}
