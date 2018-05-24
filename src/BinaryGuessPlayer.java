import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

// Binary-search based guessing player. This player is for task C.

public class BinaryGuessPlayer implements Player {

    public Config c = new Config();
    public Random r = new Random();
    public ArrayList<String> returnedAttValSet = new ArrayList<String>();
    public int alivePerson = 0;
    public Person chosenPerson;
    public BinaryHelper binaryHelper = new BinaryHelper();

  
     // Loads the game configuration from gameFilename, and also store the chosen person.
     
    public BinaryGuessPlayer(String gameFilename, String chosenName) throws IOException {
        
        c.configFileLoader(gameFilename);
        for (Person person : c.personList) {
            if (person.getName().equals(chosenName)) {
                chosenPerson = new Person(person.getName(), person.getPersonAttValSet());
            }
        }
        alivePerson = c.personList.size();

    } // end of BinaryGuessPlayer()

    public Guess guess() {

    	System.out.println(" ");
    	
        Guess.GuessType guessType = null;
        String guessAttribute = "";
        String guessValue = "";

        returnedAttValSet = binaryHelper.generateBinaryDecisionTree(c.personList, alivePerson);

        if (alivePerson != 1) {
            guessType = Guess.GuessType.Attribute;
            guessAttribute = returnedAttValSet.get(0);
            guessValue = returnedAttValSet.get(1);

            // Get the last person's name only if there is 1 person left
        } else {

            guessType = Guess.GuessType.Person;
            guessAttribute = "";
            
            for (Person person : c.personList) 
                guessValue = person.getName();
            
        }

        return new Guess(guessType, guessAttribute, guessValue);
        
    } // end of guess()

    
    
    public boolean answer(Guess currGuess) {

        // If mType == Person
        if (currGuess.getType().equals(Guess.GuessType.Person)) {
            if (currGuess.getValue().equals(chosenPerson.getName())) {
                return true;
            }
            return false;

            // Else, mType == Attribute
        } else {

            // If guessed attribute-value pair matches chosen person's one, return true

            if (chosenPerson.getPersonAttValSet().get(currGuess.getAttribute()).equals(currGuess.getValue())) {
                return true;
            }
            // If guessed attribute-value pair doesn't match chosen' person's one, return
            // false
            return false;
        }
    } // end of answer()

    public boolean receiveAnswer(Guess currGuess, boolean answer) {
    	
    	// If the current guess is correct
        if (answer) {
            
        	// and the type of guess is Person, return true
            if (currGuess.getType().equals(Guess.GuessType.Person)) {
                System.out.println("guessed person: " + currGuess.getValue());
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
        	
            ArrayList<String> deadPerson = deadPersonFalseGuess(currGuess);
            for (String dead: deadPerson)
                updatePersonList(dead); 

        }
        
        return false;
        
        
        
    } // end of receiveAnswer()
    
    
    
   //end of implemented player methods
    
    
    
    
    //********************************************************************************************************************
    
    
    
    
    //start of helper methods
    
    
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
  
    

     // Populate deadPerson list with persons who do not have chosen persons attributes
     public ArrayList<String> deadPersonFalseGuess(Guess currGuess) 
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
     

     // Kill person in personList who is not the Player's chosen person
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

} // end of class BinaryGuessPlayer
