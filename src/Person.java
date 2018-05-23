import java.util.*;

public class Person {
    
    private String name = "";
    private LinkedHashMap<String, String> personAttValSet = new LinkedHashMap<String, String>();
    
    public Person(String name, LinkedHashMap<String, String> personaAttValSet) {
        this.setName(name);
        this.setPersonAttValSet(personaAttValSet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedHashMap<String, String> getPersonAttValSet() {
        return personAttValSet;
    }

    public void setPersonAttValSet(LinkedHashMap<String, String> personAttValSet) {
        this.personAttValSet = personAttValSet;
    }

}
