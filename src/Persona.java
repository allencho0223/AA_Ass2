import java.util.*;

public class Persona {
    
    private String name = "";
    private LinkedHashMap<String, String> personaAttValSet = new LinkedHashMap<String, String>();
    
    public Persona(String name, LinkedHashMap<String, String> personaAttValSet) {
        this.setName(name);
        this.setPersonaAttValSet(personaAttValSet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedHashMap<String, String> getPersonaAttValSet() {
        return personaAttValSet;
    }

    public void setPersonaAttValSet(LinkedHashMap<String, String> personaAttValSet) {
        this.personaAttValSet = personaAttValSet;
    }

}
