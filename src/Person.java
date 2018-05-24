import java.util.*;


// Person class to create and update a Person with an Attribute Value Set using a HashMap
// Mainly used to update the personList
public class Person {
    
    private String name = "";
    private HashMap<String, String> personAttValSet = new HashMap<String, String>();
    
    public Person(String name, HashMap<String, String> personaAttValSet) {
        this.setName(name);
        this.setPersonAttValSet(personaAttValSet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getPersonAttValSet() {
        return personAttValSet;
    }

    public void setPersonAttValSet(HashMap<String, String> personAttValSet) {
        this.personAttValSet = personAttValSet;
    }

}
