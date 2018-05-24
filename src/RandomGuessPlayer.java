import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;



//Random guessing player. This player is for task B.
public class RandomGuessPlayer implements Player {

    public Config c = new Config();
    public Random r = new Random();
    public static int alivePerson = 0;
    public Person chosenPerson;

    
    // Loads the game configuration from gameFilename, and also store the chosen person.
    public RandomGuessPlayer(String gameFilename, String chosenName) throws IOException {

        c.configFileLoader(gameFilename);

        for (Person person : c.personList) {
            if (person.getName().equals(chosenName)) {
                chosenPerson = new Person(person.getName(), person.getPersonAttValSet());
            }
        }
        alivePerson = c.personList.size();

    } // end of RandomGuessPlayer()
    
 
    
    
    public Guess guess() {

        String guessAttribute = "";
        String guessValue = "";
        
        //declare array of the two GuessTypes
        Guess.GuessType[] guessTypes = Guess.GuessType.values();
        Guess.GuessType guessType = null;
   
        // the guessType is chosen randomly from enum values to ensure that this player is random each time. 
        // this allows the player to randomly choose the correct person in one guess (very rarely)        
        if (alivePerson == 1) 
            guessType = Guess.GuessType.Person;
        else 
            guessType = guessTypes[r.nextInt(guessTypes.length)];

       
        // if GuessType Person is randomly chosen:
        if (guessType == Guess.GuessType.Person) {
        	guessValue = personGuessValue(guessValue, guessAttribute);
        
        // if GuessType Attribute is randomly chosen:	
        } else {
        	guessAttribute =  getGuessAttribute(guessAttribute);
            guessValue = getGuessValue(guessAttribute);
           
        }
        
        return new Guess(guessType, guessAttribute, guessValue);


    } // end of guess()
    
    
    
    
    // If GuessType is Person, compare value with chosen person's name. If correct, return true, else return false
    // If GuessType Attribute and value match the chosen persona's, return true, else return false
    public boolean answer(Guess currGuess) {

        if (currGuess.getType().equals(Guess.GuessType.Person)) 
        {
            if (currGuess.getValue().equals(chosenPerson.getName())) 
                return true;
            return false;
            
        } else {
        	
            for (Entry<String, String> entry : chosenPerson.getPersonAttValSet().entrySet()) {
                if (currGuess.getAttribute().equals(entry.getKey()) && currGuess.getValue().equals(entry.getValue())) 
                    return true;
            }
            return false;
        }
    } // end of answer()

    
    
    
    
    public boolean receiveAnswer(Guess currGuess, boolean answer) {

        // If the answer is true 
        if (answer) {

        	//add to deadPerson list those that do not match the attribute value pair according to true guess
            //update the personList for next round
            if (currGuess.getType().equals(Guess.GuessType.Attribute)) {
                ArrayList<String> deadPersons = deadPersonsTrueGuess(currGuess); 
                for (String dead: deadPersons)
                updatePersonList(dead); 
                return false;
            }

            // If the player correctly guessed the opponent's chosen person, return true
            return true;

            // If the answer is false
        } else {

            // and the GuessType is Person
            // Remove the incorrect guessed person from the list and return false
            if (currGuess.getType().equals(Guess.GuessType.Person)) {

                String deadPerson = "";
                for (Person person : c.personList) {
                    if (person.getName().equals(currGuess.getValue())) {
                        deadPerson = person.getName();
                        break;
                    }
                }
                updatePersonList(deadPerson); 
                
                // If the player has incorrect guess on the opponent's value
                // Remove the personList who do not have that value
            } else {

                ArrayList<String> deadPerson = deadPersonsFalseGuess(currGuess); 
                for (String dead: deadPerson)
                updatePersonList(dead); 
             
            }
            return false;
        }
    } // end of receiveAnswer()
    
    
    //end of implemented Player Methods
    
    
    
    
    //********************************************************************************************************************
    
    

    
    //start of Helper methods:
    
    
    
    //  Get the last person's name if only 1 person left otherwise guess a random person from the personList
    public String personGuessValue(String guessValue, String guessAttribute) {
    	
    	guessAttribute = "";
    	
        if (alivePerson == 1) {
            for (Person person : c.personList) 
                guessValue = person.getName();
            
        } else 
            guessValue = c.personList.get(r.nextInt(c.personList.size())).getName();
        
        return guessValue;
    }
    
    
    // Guess Type is Attribute
    // Get random attribute from the config attribute value set and choose an attribute randomly
    public String getGuessAttribute(String guessAttribute) {
	   
		    ArrayList<String> tempAttributes = new ArrayList<String>();		    
		    for (HashMap.Entry<String, ArrayList<String>> entry : c.attValSet.entrySet()) 
		        tempAttributes.add(entry.getKey());
		    
		    guessAttribute = tempAttributes.get(r.nextInt(tempAttributes.size()));
		    
		    return guessAttribute;
		    
    }
   
   

   // Guess Type is Attribute
   // Get random value from the config attribute value set and choose a value randomly
   public String getGuessValue(String guessAttribute) {
	   
	   String guessValue = null;
	   
	   ArrayList<String> tempValues = new ArrayList<String>();
	   for (HashMap.Entry<String, ArrayList<String>> entry : c.attValSet.entrySet()) {
	       if (entry.getKey().equals(guessAttribute)) 
	           tempValues = entry.getValue();
	   }
	   
	   guessValue = tempValues.get(r.nextInt(tempValues.size()));
	   return guessValue;
   
   }
    
   
   // If the guess was true, then add values from deadPerson List that do not match the att/values of the guessed person
   public ArrayList<String> deadPersonsTrueGuess(Guess currGuess) 
   {
	   ArrayList<String> deadPerson = new ArrayList<String>();
	   for (Person person : c.personList) {
           for (Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
               if (entry.getKey().equals(currGuess.getAttribute())
                       && !entry.getValue().equals(currGuess.getValue())) {
                         deadPerson.add(person.getName());
               }
           }
       }
	   
	   return deadPerson;
   }
 
   

    // If the guess was false, then add values from the deadPerson List that match the att/values of the guessed person
    public ArrayList<String> deadPersonsFalseGuess(Guess currGuess) 
    {
    	  ArrayList<String> deadPerson = new ArrayList<String>();

          for (Person person : c.personList) {
              for (Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
                  if (entry.getKey().equals(currGuess.getAttribute())
                          && entry.getValue().equals(currGuess.getValue())) {
                      deadPerson.add(person.getName());
                  }
              }
          }
          
          return deadPerson;
    	
    }
    

    // Remove the dead person in personList who is not the Player's chosen person
    public void updatePersonList(String deadPerson) 
    {
    	 for(int i = 0; i < c.personList.size();i++)
    	 {
                 if(c.personList.get(i).getName().equals(deadPerson)){
                     c.personList.remove(i--);
                     if (i < 0) 
                         i = 0;
                     alivePerson--;
                 }
    	 }
    	 
    }
    

} // end of class RandomGuessPlayer
