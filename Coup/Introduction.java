import hsa.Console;
import java.awt.*;       
import java.io.*;        
import javax.imageio.*;

/**
 * Introduction
 * @Author Jai Prajapati, Nawal Mehta 
 * @Revised 06/11/2018
 * Class that handles the introduction of the gaem including a loading screen, collecting player information, displaying rules and a countdown method 
 * */

public class Introduction{
  
  //Inititalise variables 
  private Player [] playerDeck; //An array of all players in the game
  private int numPlayers; //stores number of players in the game
  private String [] playerNames; //An array of all player names
  static Console introConsole; //Console for the introduction to be displayed on
  private GamePieces deck; //GamePieces object for all cards and coins 
  
  
  /*
   * Introduction
   * Constructor for the Introduction class that stores a Console and GamePieces object provided as parameters 
   * @param - d - Console - a console for the introduction to run on
   * @param - deck - GamePieces - an existing GamePieces object for cards and coins
   * */
  public Introduction (Console d, GamePieces deck){
    introConsole = d;
    this.deck = deck; 
  }
  
  
  /*
   * loadingScreen
   * Method that displays a home screen for the game on the introConsole
   * */
  public void loadingScreen (){
    //Declare an image
    Image img;
    //Try catch to access an image at the specified file location
    try{
      //initialise the image to the image at the file location
      img = ImageIO.read(new File("LoadingScreen.jpg"));
      //scale the image so it fits in the console
      img = img.getScaledInstance((int)(900), (int)(1000), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Draw the image on the console
    introConsole.drawImage(img, 0, 0, null);
    //Display a rectangle with the words "Welcome to the Game Coup!!!" and "Loading..." in it
    introConsole.setColor(Color.white); 
    introConsole.fillRect(500, 440, 300, 100);
    introConsole.setColor(Color.black); 
    introConsole.setCursor(19, 48); 
    introConsole.println("Welcome to the Game Coup!!!");
    introConsole.setCursor(21, 54); 
    introConsole.println("Loading..."); 
    //Show the loading screen for 5 seconds before clearing the console and moving on
    countdown(5); 
    introConsole.setCursor(0, 0); 
    introConsole.clear(); 
  }
  
  
  /*
   * runIntro
   * Method that runs the introduction and collects information from the players and saves it to variables used for the game
   * */
  public void runIntro(){
    
    //Declare variables
    String numberPlayersToValidate; //String to store the number of players input
    
    //Do while loop to ask the user for the 
    do {
      //Ask the user how many players are playing
      introConsole.print("How many players are playing (minimum 3, maximum 6): ");
      numberPlayersToValidate = introConsole.readLine(); 
    } while ((!DataValidation.validateInteger(numberPlayersToValidate)) && (Integer.parseInt(numberPlayersToValidate) >= 3) && (Integer.parseInt(numberPlayersToValidate) <= 6)); 
    
    //Store the number of players input to the numPlayers variable
    numPlayers = Integer.parseInt(numberPlayersToValidate);
    //Allocate memory for the playerNames object for the number of players 
    playerNames = new String[numPlayers];
    //Allocate memory for the playerDeck object for the number of players 
    playerDeck = new Player [numPlayers];
    
    //For loop to ask the user the name of each player in the game
    for (int x = 0; x < numPlayers; x++){
      //Ask the player for x player's name
      introConsole.println("What is the name of player " + (x+1)+ ":");
      //Save the player's name to the the playerNames String[] at position of the player
      playerNames[x] = introConsole.readLine();
    }
    
    //For loop to initialize each player with their coins, 2 cards and name
    for (int i = 0; i < numPlayers; i++){
      //subtract 2 coins from the deck 
      deck.subtractCoins(2);
      //Distribute two cards from the deck
      String cardOneDistributed = deck.distribute(); 
      String cardTwoDistributed = deck.distribute();  
      //Initialize the Player at index i in the playerDeck[] with 2 coins, the 2 card distributed and the player's name from the playerNames[]
      playerDeck[i] = new Player (2, cardOneDistributed, cardTwoDistributed, playerNames[i]); 
      //Tell all other players to look away other then the player being shown their cards
      countdown(5, introConsole, playerDeck[i].getName());
      //Show the 2 cards on teh console for 3 seconds
      Cards.drawCard(cardOneDistributed, introConsole, 100, 100, 2);
      Cards.drawCard(cardTwoDistributed, introConsole, 300, 300, 2);
      countdown(3);
    }
  }//ortnInur
  
  
  /*
   * getNumPlayers
   * Method that returns the number of players in the game
   * @return - int - returns the number of players (numPlayers)
   * */
  public int getNumPlayers(){
    return (numPlayers);
  }
  
  
  /*
   * getPlayerNames
   * Method that returns the array of player names 
   * @return - String[] - returns the array of player names (playerNames)
   * */
  public String[] getPlayerNames(){
    return (playerNames);
  }
  
  
  /*
   * getPlayerDeck
   * Method that returns the array of Player objects in the game 
   * @return - Player[] - returns the array of Players in the game (playerDeck)
   * */
  public Player[] getPlayerDeck(){
    return (playerDeck);
  }
  
  
  /*
   * getDeck
   * Method that returns the deck used in the introduction 
   * @return - GamePieces - returns the deck (deck)
   * */
  public GamePieces getDeck(){
    return (deck); 
  }
  
  
  /*
   * countdown
   * Method that displays a countdown while telling other players to look away 
   * @param - seconds - int - the number of seconds to countdown
   * @param - c - Console - an existing Console to display the countdown on
   * @param - player - String - name of the Player who is being shown the cards
   * */
  public static void countdown (int seconds, Console c, String player){
    //clear the console and set the cursor
    c.clear(); 
    c.setCursor(2, 2); 
    //display to the player that they are going to be shown their cards and other players must look away
    c.println(player + ": Here are your cards, " + "make sure other player's are not looking"); 
    //For loop to display the countdown
    for (int i = seconds; i > 0; i--){
      //Set the cursor and print i, which is the seconds remaining
      c.setCursor(10, 10); 
      c.println(i);
      //Try catch to attempt to pause the program
      try{
        //Pause the program for 1 seconds (1000 miliseconds)
        Thread.sleep(1000);
      }
      catch (InterruptedException e){
        Thread.currentThread().interrupt();
      }
    }  
  }//nwodtnuoc
  
  
  /*
   * countdown
   * Method that pauses the program for a set number of seconds 
   * @param - seconds - int - the number of seconds to pause the program
   * */
  public static void countdown (int seconds){
    //For loop to count the seconds
    for (int i = 1; i <= seconds; i++){
      //Try catch to attempt to pause the program
      try{
        //Pause the program for 1 seconds (1000 miliseconds)
        Thread.sleep(1000);
      }
      catch (InterruptedException e){
        Thread.currentThread().interrupt();
      }
    }  
  }//nwodtnuoc
  
  
  /*
   * seeRules
   * Method that displays the rules of the game
   * @param - e - Console - existing console to display the rules in
   * */
  public void seeRules(Console e){
    //Display all rules on the console
    e.println("In Coup, you want to be the last player with influence, influence being that you have at least one face-down card in your hand"); 
    e.println(); 
    e.println("Players (3-6) - Start with 2 cards and 2 coins"); 
    e.println();
    e.println("Deck - 50 coins and the following 5 cards (3 each)");          
    e.println("Duke: Take three coins from the deck. Block someone from foreign aid.");
    e.println("Assassin: Pay three coins to assassinate another player’s influence(1)");
    e.println("Contessa: Block an assassination attempt against you");
    e.println("Captain: Take two coins from another player, or block a stealing attempt against you.");
    e.println("Ambassador: Take two random character cards from the deck, interchange any of the two with your hand and return the remaining two cards. Block a stealing attempt against you");
    e.println();
    e.println("On your turn, you can take any of the actions listed above, even if you do not have that card, or you can take one of three other actions:");
    e.println("Income: Take one coin from the deck.");                
    e.println("Foreign aid: Take two coins from the deck.");                
    e.println("Coup: Pay seven coins and launch a coup against an opponent, making that player lose 1 influence. (Mandatory move if you have 10 or more coins)");                
    e.println();
    e.println("When you take one of the character actions on your turn or in counteraction to another player, that character's action goes into effect unless you are called on a bluff. If you are called on a bluff and don’t reveal the appropriate character, you must reveal one card face-up on the table. Face-up characters cannot be used. If you do have the character and reveal it, the challenger loses a card, then you shuffle the revealed card back into the deck and take another one.");                
    e.println();
    e.println("Here are the moves you may enter corresponding to the actions stated above");
    e.println("income");
    e.println("foreign aid");
    e.println("coup");
    e.println("tax");
    e.println("exchange cards");
    e.println("assassinate");
    e.println("steal");
  }
}//seluRees