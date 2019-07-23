import java.io.*; 

/*
 * DataValidation 
 * @Author 
 * @Revised 06/11/2018
 * Class with static methods to handle data validation
 * */

public class DataValidation {
  
  public DataValidation(){
  }
  
  /*
   * validateString 
   * Method that checks to see if a string entered by the user is the same as an expected string 
   * @param expectedInput - String - the name of the card to be displayed
   * @param userInput - String - The console the card is being drawn on
   * @return - boolean - returns true or false depending on if the user input string is of the expected string or not
   */ 
  public static boolean validateString (String expectedInput, String userInput){
    
    //boolean to store wether or not the String input is of the expected string 
    boolean valid = true; 
    
    //try catch statement to validate the string, setting valid to false if the expectedInput and userInput are not equal
    try {
      if (!(expectedInput.equalsIgnoreCase(userInput))) {
        throw (new Exception());
      }           
    } catch (Exception exe) {   
      valid = false; 
    }
    
    return (valid); 
  }
  
  
  /*
   * validateInteger 
   * Method that checks to see if an integer entered by the user is a valid integer 
   * @param input - String - the user input of the number in a string
   * @return - boolean - returns true or false depending on if the user input is a vald integer or not
   */ 
  public static boolean validateInteger (String input){
    
    //boolean to store wether or not the integer input is valid
    boolean valid = true; 
    
    //try catch statement to validate the number, setting valid to false if a NumberFormatException is thrown
    try {
      int test = Integer.parseInt(input); 
    } catch (NumberFormatException e){
      valid = false; 
    }
    
    return (valid); 
  }
  
  
  /*
   * validateDouble 
   * Method that checks to see if a double entered by the user is a valid double number 
   * @param input - String - the user input of the number in a string
   * @return - boolean - returns true or false depending on if the user input is a vald double value or not
   */ 
  public static boolean validateDouble (String input){
    
    //boolean to store wether or not the integer input is valid
    boolean valid = true; 
    
    //try catch statement to validate the number, setting valid to false if a NumberFormatException is thrown
    try {
      double test = Double.parseDouble(input); 
    } catch (NumberFormatException e){
      valid = false; 
    }
    
    return (valid); 
  }
}