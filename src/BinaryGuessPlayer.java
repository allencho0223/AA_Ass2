import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


// Binary-search based guessing player. This player is for task C.
public class BinaryGuessPlayer implements Player {

    public Config config = new Config();
    public Random random = new Random();
    public ArrayList<String> returnedAttValSet = new ArrayList<String>();
    public int alivePerson = 0;
    public Person chosenPerson;
    public BinaryHelper binaryHelper = new BinaryHelper();

  
     // Loads the game configuration from gameFilename, and also store the chosen person.
     
    public BinaryGuessPlayer(String gameFilename, String chosenName) throws IOException {
        
        config.configFileLoader(gameFilename);
        for (Person person : config.personList) {
            if (person.getName().equals(chosenName)) {
                chosenPerson = new Person(person.getName(), person.getPersonAttValSet());
            }
        }
        alivePerson = config.personList.size();

    } // end of BinaryGuessPlayer()

    
    
    public Guess guess() {

        Guess.GuessType guessType = null;
        String guessAttribute = "";
        String guessValue = "";

        // The ideal Attribute/Value set chosen by the Generator to eliminate almost half the persons each round
        returnedAttValSet = binaryHelper.generateIdealBinaryGuess(config.personList, alivePerson);

        // If more than one person is still alive, use ideal Attribute/Value set as the next Guess
        if (alivePerson != 1) {
            guessType = Guess.GuessType.Attribute;
            guessAttribute = returnedAttValSet.get(0);
            guessValue = returnedAttValSet.get(1);

        // Otherwise get the remaining person's name only if there is 1 person left
        } else {

            guessType = Guess.GuessType.Person;
            guessAttribute = "";
            
            for (Person person : config.personList) 
                guessValue = person.getName();
            
        }

        return new Guess(guessType, guessAttribute, guessValue);
        
    } // end of guess()

    
    
    public boolean answer(Guess currGuess) {

        // If GuessType is Person and matches the chosen person's name, return true, else false
        if (currGuess.getType().equals(Guess.GuessType.Person)) {
            if (currGuess.getValue().equals(chosenPerson.getName())) {
                return true;
            }
            return false;

            // If the GuessType is Attribute
        } else {

            // If guessed attribute-value pair matches chosen person's, return true
            if (chosenPerson.getPersonAttValSet().get(currGuess.getAttribute()).equals(currGuess.getValue())) {
                return true;
            }
            // If guessed attribute-value pair doesn't match chosen' person's, return false
            return false;
        }
    } // end of answer()
    
    

    public boolean receiveAnswer(Guess currGuess, boolean answer) {
    	
    	// If the current guess is correct
        if (answer) {
            
        	// and the type of guess is Person, return true
            if (currGuess.getType().equals(Guess.GuessType.Person)) {
                return true;

            //else add to the deadPersons list those that do not match the attribute value pair according to true guess
            //update the personList for next round
            } else {
            
               ArrayList<String> deadPersons =  deadPersonsTrueGuess(currGuess); 
               for (String dead: deadPersons)
                   updatePersonList(dead); 
                    return false;
            }
            

        // if the current guess is false
        } else {
        	 // add to the deadPersons list those that do match the attribute value pair according to false guess
            //  Remove from the personList those who have the false values
            ArrayList<String> deadPerson = deadPersonFalseGuess(currGuess);
            for (String dead: deadPerson)
                updatePersonList(dead); 

        }
        
        return false;
        
        
        
    } // end of receiveAnswer()
    
    
    
   //end of implemented player methods
    
    
    
    
    //********************************************************************************************************************
    
    
    
    
    //start of helper methods
    
    
    
    // If the guess was true, then add values from deadPerson List that do not match the att/values of the guessed person
    public ArrayList<String> deadPersonsTrueGuess(Guess currGuess) 
    {
 	   ArrayList<String> deadPerson = new ArrayList<String>();
 	   for (Person person : config.personList) {
            for (HashMap.Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
                if (entry.getKey().equals(currGuess.getAttribute())
                        && !entry.getValue().equals(currGuess.getValue())) {
                          deadPerson.add(person.getName());
                }
            }
        }
 	   
 	   return deadPerson;
    }
  
    

    // If the guess was false, then add values from the deadPerson List that match the att/values of the guessed person
     public ArrayList<String> deadPersonFalseGuess(Guess currGuess) 
     {
     	  ArrayList<String> deadPerson = new ArrayList<String>();

           for (Person person : config.personList) {
               for (HashMap.Entry<String, String> entry : person.getPersonAttValSet().entrySet()) {
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
     	 for(int i = 0; i < config.personList.size();i++)
     	 {
                  if(config.personList.get(i).getName().equals(deadPerson)){
                      config.personList.remove(i--);
                      if (i < 0) 
                          i = 0;
                      alivePerson--;
                  }
     	 }
     	 
     }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

} // end of class BinaryGuessPlayer
