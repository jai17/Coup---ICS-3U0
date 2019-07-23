import hsa.Console; 
import java.awt.*; 
import java.io.*;        
import javax.imageio.*;
import java.util.*; 

/**
 * TurnSequence
 * @Author Jai Prajapati and Nawal Mehta
 * @Revised 06/10/2018
 * Class that handles the main turn based events based on player input, utilising all other classes as part of the main program
 * */

public class TurnSequence{
  
  //Variable Delcaration
  static ArrayList <String> commands; //An ArrayList to store all valid commands 
  
  
  /*
   * TurnSequence
   * Constructor for the TurnSeuquence class that initializes the commands
   * */
  public TurnSequence(){
    
    //Initialize commands ArrayList
    commands = new ArrayList<>();
    
    //Add all string actions to the command ArrayList
    commands.add("income");
    commands.add("foreign aid");
    commands.add("coup");
    commands.add("tax");
    commands.add("steal");
    commands.add("exchange cards");
    commands.add("assassinate"); 
    
  }
  
  
  /*
   * onTurn 
   * Method that handles a single player's turn by checking if they must coup and executes the action they would like to perform
   * @param playerNumber - int - the number of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public void onTurn (int playerNumber, Player [] players, GamePieces deck, Console display){
    
    //Variable declaration
    String player1Name; //String to store the name of player1
    Player player = players[playerNumber - 1]; //Player object of the player whose turn it is
    String player2Name; //String to store ame of player2
    Player counterPlayer; //Player object of the counterPlayer
    String action = ""; //String to store the user input of intended action
    String counterAction; //String to store the user input of the counter acttion
    int secondPlayer; //int to store the number of the second Player 
    boolean mandatoryCoup = false; //boolean used to check wether the player has to coup
    boolean validAction = true; //boolean to check wether the player has entered a valid action
    
    //Clear the display 
    display.clear(); 
    //Display whose turn it is on the console
    display.println("It is now " + player.getName() + "'s turn");
    //Check if the player has to coup using the checkMandatoryCoup() method from the Player class and saves the output to the mandatoryCoup boolean
    mandatoryCoup = player.checkMandatoryCoup();
    
    //If the player must coup, make the player coup 
    if (mandatoryCoup){
      //Display that the player must coup
      player.mandatoryCoup(display);
      //While loop to ensure the user coups a valid player in the game
      while (true){
        //Ask the user who they would like to coup 
        display.println(player.getName() + ": Who would you like to coup (insert player's name)"); 
        player2Name = display.readLine();
        //Set the counterPlayer object to the Player from the player object with the name the user entered, using findPlayeTwo method
        counterPlayer = findPlayerTwo(players, player2Name); 
        //Check if the counterPlayer is an actual player, break the while loop if so
        if (!((counterPlayer.getName()).equals(""))){
          break; 
        }
      }
      
      //run coup method from the Player class so that the current player can coup
      player.coup(counterPlayer, deck, display);
      
    } else {
      
      //While loop used to ask the user which action they would like to perform until a valid action is entered
      while (validAction){
        
        //Ask the user what action they would like to take
        display.println("What action would you like to take");
        action = display.readLine();
        
        //For loop to check if the action input by the user is similar to any of the valid actions in commands, if so the while loop ends
        for(int i = 0; i < commands.size(); i++){
          //Check if the action is equal to current command beign checked, in which case, validAction is set false and the while loop will end 
          if(getSimilarity(commands.get(i), action) < 2){
            validAction = false; 
          }
          
          //Check if the player has enough coins to assassinate if they choose to do so, if they don't, validAction is set to true and the while loop continues
          if (action.equalsIgnoreCase("assassinate")){
            if (!(player.getMoney()>=3)){
              validAction = true;
              display.println("You do not have enough coins to assassinate");
            }
          }
          
          //Check if the player has enough coins to coup if they choose to do so, if they don't, validAction is set to true and the while loop continues
          if (action.equalsIgnoreCase("coup")){
            if (!(player.getMoney()>=7)){
              validAction= true;
              display.println("You do not have enough coins to coup");
            }
          }
        }
        
        //If validAction is true, tell the player they have entered an invalid action and muist enter again
        if (validAction == true){
          display.println("You have not entered a valid action, enter again");
        }
      }
      //Run the method runAction with the player's action, and player/deck/console information
      runAction(action, player, players, deck, display); 
    }
  }
  
  
  /*
   * runaction 
   * Method that handles a single player's action they would like to perform
   * @param action - String - the action the player would like to play 
   * @param player - Player - the Player object of the player whose turn it is  
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void runAction (String action, Player player, Player [] players, GamePieces deck, Console display){
    //Variable declaration 
    int commandNumber = 0; //int used to store the command number chosen
    
    //For loop to determine which action from the commands ArrayList the player has chosen
    for(int i = 0; i < commands.size(); i++){
      //If the action the player wants to play is similar to a command, sets the commandNumber to the current command at i in the commands ArrayList
      if(getSimilarity(commands.get(i), action) < 2){
        commandNumber = i;
      }
    }
    
    //Switch case to determine which action to perform based on the commandNumber
    switch (commandNumber){
      case 0:
        //Run the income method
        income(player, deck, display); 
        break; 
      case 1:
        //Run the foreignAid method
        foreignAid(player, players, deck, display); 
        break; 
      case 2:
        //Run the coup method
        coup (player, players, deck, display); 
        break; 
      case 3:
        //Run the tax method
        tax (player, players, deck, display); 
        break;
      case 4:
        //Run the steal method 
        steal (player, players, deck, display); 
        break; 
      case 5:
        //Run the exchangeCards method
        exchangeCards (player, players, deck, display);
        break;
      case 6:
        //Run the assassinate method
        assasinate (player, players, deck, display);
        break;
    }      
  }
  
  /*
   * income 
   * Method that handles the income action by adding one coin to the player's hand
   * @param player - Player - the Player object of the player whose turn it is  
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void income (Player player, GamePieces deck, Console display){
    display.clear(); 
    display.println(player.getName() + " has incomed 1 coin");
    player.income(deck);
  }
  
  /*
   * foreignAid 
   * Method that handles the foreign aid action by adding two coins to the player's hand after any blocks are handled
   * @param player - Player - the Player object of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void foreignAid (Player player , Player [] players, GamePieces deck, Console display){
    //Variable declaration
    String player2Name; //String to store the opposing player's name
    Player counterPlayer; //Player object for the opposing player 
    String bluff; //String to store the bluff input 
    String block; //String to store the block input 
    String blockType; //String to store the blockType input 
    
    //Clear the console and display that the player wants to foreign aid
    display.clear(); 
    display.println(player.getName() + " wants to foreign aid 2 coins");
    //Ask all other players if anyone would like to block the foreign aid
    display.println("Would anyone like to block this action (Y or N)"); 
    block = display.readLine(); 
    
    //If someone would like to block, ask who and if the player would like to call a bluff, and return results for each case
    if (block.equalsIgnoreCase("y")){
      //Ask who would like to block
      display.println("Who would like to block this action?");
      player2Name = display.readLine(); 
      //Find the Player object of the player blocking and save it to counterPlayer
      counterPlayer = findPlayerTwo(players, player2Name); 
      //Ask the player whose turn it is if they would like to call a bluff
      display.println(player.getName() + ": Would you like to call a bluff on the block (Y or N)"); 
      bluff = display.readLine();
      //If the player would like to call a bluff, make the counter player reveal their card accordingly
      if (bluff.equalsIgnoreCase("y")){
        //If the counter player has the card, they reveal it and get a new card while the player whose turn it is loses a card
        if (counterPlayer.hasCard("duke")){
          counterPlayer.trueBluff(deck, "duke", display);
          player.revealCard(deck, display); 
        } 
        //If the counter player doesnt have the card, they lose a card while the player whose turn it is foreign aids 
        else {
          //Clear the console and display that the counter player doesn't have the card and must reveal a card
          display.clear(); 
          display.println(counterPlayer.getName() + " does not have a duke and must reveal a card");
          Introduction.countdown(3); 
          //Make the counter player reveal a card
          counterPlayer.revealCard(deck, display);
          display.clear(); 
          //Allow the player whose turn it is to foreign aid and display that they do so
          player.foreignAid(deck); 
          display.println(player.getName() + " has succesfully foreign aided two coins"); 
        }
      } 
      //The player's turn ends if they do not call a bluff
      else {
        display.println(player.getName() + ": Your turn has ended");
      }
    } 
    //The player is allowed to foreign aid 
    else {
      //Allow the player whose turn it is to foreign aid and display that they do so
      player.foreignAid(deck); 
      display.println(player.getName() + " has succesfully foreign aided two coins");  
    }
  }
  
  
  /*
   * coup 
   * Method that handles the coup action by removing 7 coins from the player's hand and makes the couped player reveal a card
   * @param player - Player - the Player object of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void coup (Player player , Player [] players, GamePieces deck, Console display){
    //Variable declaration
    String player2Name; //String to store the opposing player's name
    Player counterPlayer; //Player object for the opposing player 
    String bluff; //String to store the bluff input 
    String block; //String to store the block input 
    String blockType; //String to store the blockType input
    String playerToCoup; //String to store the name of the person being couped
    
    //Clear the console and display that the player whose turn it is wants to coup
    display.clear(); 
    display.println(player.getName() + " wants to coup");
    
    //While loop to ask the player who they would like to coup, which will loop until a valid name is entered
    while (true){
      //Ask the player who they would like to coup
      display.println(player.getName() + ": Who would you like to coup (insert player's name)"); 
      player2Name = display.readLine();
      //Find the Player object of the player being couped and save it to counterPlayer
      counterPlayer = findPlayerTwo(players, player2Name); 
      
      //Break the while loop if a valid player has been entered
      if (!(counterPlayer.equals(""))){
        break; 
      } else {
        display.println("A valid player has not been entered, please enter a valid name");
      }
    }
    //Allow the player to coup using the coup method from the Player class
    player.coup(counterPlayer, deck, display);
  }
  
  
  /*
   * tax 
   * Method that handles the tax action by adding 3 coins to the player's hand after any bluff calls are handled
   * @param player - Player - the Player object of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void tax (Player player , Player [] players, GamePieces deck, Console display){
    //Variable declaration
    String player2Name; //String to store the opposing player's name
    Player counterPlayer; //Player object for the opposing player 
    String bluff; //String to store the bluff input 
    String block; //String to store the block input 
    String blockType; //String to store the blockType input 
    
    //Clear the console and display that the player wants to tax
    display.clear();
    display.println(player.getName() + " wants to tax 3 coins");
    
    //Ask if anyone would like to call a bluff
    display.println("Would anyone like call a bluff on this action (Y or N)"); 
    bluff = display.readLine();
    
    /*
     * If someone would like to call a bluff, ask who and if the player has a duke, make them reveal it and get a 
     * new card while the other player loses a card OR the player lose's a card if they dont have the duke
     * */
    if (bluff.equalsIgnoreCase("y")){
      //Ask who would like to call the bluff
      display.println("Who would like to call a bluff on this action?");
      player2Name = display.readLine();
      //Find the Player object of the player calling the bluff and save it to counterPlayer
      counterPlayer = findPlayerTwo(players, player2Name);
      //If the player has a duke, make them reveal and get a new card while the counter player loses a card 
      if (player.hasCard("duke")){
        //Clear the console and display the player actually has a duke
        display.clear();
        display.println(player.getName() + " actually has a duke");
        //Make the player reveal a duke and get a new card 
        player.trueBluff(deck, "duke", display);
        //Make the counter player lose a card
        counterPlayer.revealCard(deck, display); 
        //Allow the player to tax and display that they did so
        Cards.tax(deck, player); 
        display.println(player.getName() + " has succesfully taxed 3 coins");
      }
      //If the player doesn't have a duke, make them lose a card 
      else {
        //Clear the console and display the player doesn't have a duke
        display.clear();
        display.println(player.getName() + " doesnt have a duke, must reveal a card");
        Introduction.countdown(3);
        //Make the player lose a card
        player.revealCard(deck, display);
      }
    } 
    //If no one called a bluff, allow the player to tax
    else {
      //Allow the player to tax and display that they did so
      Cards.tax(deck, player); 
      display.println(player.getName() + " has succesfully taxed 3 coins");
    } 
  }
  
  
  /*
   * steal 
   * Method that handles the steal action by adding 2 coins to the player's hand after any bluff or block calls are handled
   * @param player - Player - the Player object of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void steal (Player player , Player [] players, GamePieces deck, Console display){
    //Variable declaration
    String player2Name; //String to store the opposing player's name
    Player counterPlayer; //Player object for the opposing player 
    String bluff; //String to store the bluff input 
    String block; //String to store the block input 
    String blockType; //String to store the blockType input 
    
    //Clear the console and display that the player would like to steal
    display.clear(); 
    display.println(player.getName() + " would like to steal");
    
    //While loop to ask the player who they would like to steal from which loops until a player with at least 2 coins is found
    while(true){
      //Ask the player who they would like to steal from
      display.println("Who would you like to steal from?"); 
      player2Name = display.readLine();
      //Find the Player object of the player being stolen from and save it to counterPlayer
      counterPlayer = findPlayerTwo(players, player2Name);
      //If the counterPlayer has at least 2 coins, break the while loop, if not, tell the player to choose another player 
       if (counterPlayer.getMoney()>=2){
         break;
       } else {
         display.println(player2Name+" doesn't have 2 coins, enter another player");
       }
    }
    
    //Ask the player being stolen from if they want to block, call a bluff or accept 
    display.println(counterPlayer.getName() + ": Would you like to block, call a bluff or accept (block, bluff or accept)"); 
    block = display.readLine();
    
    //If the counter player wants to block, ask them with which card, then ask the player if they would like to call a bluff, then return results
    if (block.equalsIgnoreCase("block")){
      //Ask the counter player which card they want to block with
      display.println("Would you like to block with ambassador or captain (C or A)");
      blockType = display.readLine();
      //Ask the player if they want to call a bluff on the block
      display.println(player.getName() + " Would you like to call a bluff (Y or N)");
      bluff = display.readLine();
      
      //If the player wants to call a bluff, make the counter player and player reveal cards accordingly 
      if (bluff.equalsIgnoreCase("y")){
        //If the counter player chooses to block with captain, check if they have a captain and display cards accordingly
        if (blockType.equalsIgnoreCase("c")){
          //If the counter player has a captain, make them reveal it and get a new card while the player loses a card
          if (counterPlayer.hasCard("captain")){
            display.clear(); 
            display.println(player2Name +" actually has a captain");
            counterPlayer.trueBluff(deck, "captain", display);
            player.revealCard(deck, display); 
          } 
          //If the counter player does not have a captain, make them lose a card and allow the player to steal
          else {
            display.clear(); 
            display.println(player2Name +" doesn't have a captain");
            Introduction.countdown(3);
            counterPlayer.revealCard(deck, display); 
            player.addMoney(2); 
            counterPlayer.subtractMoney(2);
            display.println(player.getName() + " has succesfully stolen 2 coins from " + counterPlayer.getName());
          }
        } 
        //If the counter player chooses to block with ambassador, check if they have a ambassador and display cards accordingly
        else if (blockType.equalsIgnoreCase("a")){
          //If the counter player has a ambassador, make them reveal it and get a new card while the player loses a card
          if (counterPlayer.hasCard("ambassador")){
            display.clear();
            display.println(player2Name+" actually has an ambassador"); 
            counterPlayer.trueBluff(deck, "ambassador", display);
            player.revealCard(deck, display);
          } 
          //If the counter player does not have a ambassador, make them lose a card and allow the player to steal
          else {
            display.clear(); 
            display.println(player2Name+" doesn't have a captain");
            Introduction.countdown(3);
            counterPlayer.revealCard(deck, display);
            Cards.steal(player, counterPlayer);
          }
        }
      } 
      //If the player does not want to call a bluff, the player's turn has ended
      else {
        display.println(player.getName() + "'s turn has ended");
      }
    } 
    //If the counter player wants to call a bluff, make the player and counter player reveal cards accordingly 
    else if (block.equalsIgnoreCase("bluff")){
      //If the player has a captain, make them reveal it, get a new card and steal while the counter player loses a card
      if (player.hasCard("captain")){
        display.clear(); 
        display.println(player.getName()+" actually has a captain");
        Introduction.countdown(3);
        player.trueBluff(deck, "captain", display);
        counterPlayer.revealCard(deck, display);
        Cards.steal(player, counterPlayer);
      } 
      //If the player does not have a captain, make them lose a card
      else {
        display.clear(); 
        display.println(player.getName() +" doesn't have a captain");
        Introduction.countdown(3);
        player.revealCard(deck, display); 
      }  
    }
    //If no one wants to block or bluff, allow the player to steal
    else {
      String output = Cards.steal(player, counterPlayer);
      display.println(output);
      Introduction.countdown(3); 
    }
  }
  
  
  /*
   * exchangeCards 
   * Method that handles the exchange cards action by allowing a player to switch their cards with 2 cards from the deck after bluff calls are handled
   * @param player - Player - the Player object of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void exchangeCards (Player player , Player [] players, GamePieces deck, Console display){
    //Variable declaration
    String player2Name; //String to store the opposing player's name
    Player counterPlayer; //Player object for the opposing player 
    String bluff; //String to store the bluff input 
    String block; //String to store the block input 
    String blockType; //String to store the blockType input
    
    //Clear the console and display that the player would like to exchange cards
    display.clear(); 
    display.println(player.getName() + " would like to exchange cards with the court deck");
    
    //Ask other players if they would like to call a bluff
    display.println("Would anyone like call a bluff on this action (Y or N)"); 
    bluff = display.readLine();
    
    //If a player would like to call a bluff, ask who and reveal cards accordingly 
    if (bluff.equalsIgnoreCase("y")){
      //Ask who owuld like to call the bluff
      display.println("Who would like to call a bluff on this action?");
      player2Name = display.readLine();
      //Find the Player object of the player being stolen from and save it to counterPlayer
      counterPlayer = findPlayerTwo(players, player2Name);
      
      //If the player has an ambassador, reveal it and give them a new card, while making the counter player lose a card 
      if (player.hasCard("ambassador")){
        //Clear the console an display that the player has an ambassador
        display.clear(); 
        display.println(player.getName() +" does have an ambassador");
        Introduction.countdown(3);
        //Make the player reveal a card and recieve a new card 
        player.trueBluff(deck, "ambassador", display);
        //Make the counter player lose a card
        counterPlayer.revealCard(deck, display);
        //Display that the player's turn has ended 
        display.println(player.getName() + "'s turn has ended");
      } 
      //If the player does not have an ambassador, make them lose a card
      else {
        //Clear the console and display the player does not have an ambassador
        display.clear(); 
        display.println(player.getName() +" doesn't have an ambassador");
        Introduction.countdown(3);
        //Make the player lose a card 
        player.revealCard(deck, display); 
      }
    } 
    //If no one calls a bluff, allow the player to exhange cards
    else {
      //Allow the player to exchange cards
      Cards.exchangeCards(deck, player, display);
      //Clear the console and display that the player has ambassadored 
      display.clear(); 
      display.println(player.getName() + " has succesfully ambassadored");
    }
  }
  
  
  /*
   * assassinate 
   * Method that handles the steal action by adding 2 coins to the player's hand after any bluff or block calls are handled
   * @param player - Player - the Player object of the player whose turn it is 
   * @param players - Player[] - existing Player[] of Player objects
   * @param deck - GamePieces - existing GamePieces object
   * @param display - Player - existing Console to use as the display
   */
  public static void assasinate (Player player , Player [] players, GamePieces deck, Console display){
    //Variable declaration
    String player2Name; //String to store the opposing player's name
    Player counterPlayer; //Player object for the opposing player 
    String bluff; //String to store the bluff input 
    String block; //String to store the block input 
    String blockType; //String to store the blockType input

    //Clear the console and display that the player would like to assassinate
    display.clear();
    display.println(player.getName() + " would like to assasinate");
    //Ask gthe player who they would like to assassinate 
    display.println("Who would you like to assasinate?");
    player2Name = display.readLine();
    //Find the Player object of the player being assassinated and save it to counterPlayer
    counterPlayer = findPlayerTwo(players, player2Name);
    
    //Ask the counter player if they would like to block, call a bluff or accept
    display.println(counterPlayer.getName() + ": Would you like to block with contessa, call a bluff or accept (block, bluff, accept))");
    block = display.readLine();
    
    //If the counter player wants to block, ask the player if they want to block and make the player and counter player reveal cards accordingly
    if (block.equalsIgnoreCase("block")){
      //Ask the player if they would like to call a bluff on the block
      display.println(player.getName() + " Would you like to call a bluff (Y or N)");
      bluff = display.readLine();
      
      //If the player would like to call a bluff, check if the counter player has a contessa and reveal cards accordingly
      if (bluff.equalsIgnoreCase("y")){
        //If the counter player has a contessa, make them reveal it and get a new card, while the player reveals a card
        if (counterPlayer.hasCard("contessa")){
          display.clear(); 
          display.println(player2Name +" actually has a contessa");
          Introduction.countdown(3);
          counterPlayer.trueBluff(deck, "contessa", display);
          player.revealCard(deck, display);
        } 
        //If the counter player doesn't have a contessa, make them lose a card followed by being assassinated by the player
        else {
          display.clear(); 
          display.println(player2Name +" doesn't have a contessa");
          Introduction.countdown(3);
          counterPlayer.revealCard(deck, display);
          String assasinateOutput = Cards.assasinate(player, counterPlayer, deck, display);
          display.println(assasinateOutput);
        }
      }
      //The player's turn ends and is displayed on the console
      else {
        display.println(player.getName() + "'s turn has ended");
      }
    }
    //If the counter player wants to call a bluff, check if the player has an assassin and reveal cards accordingly
    else if (block.equalsIgnoreCase("bluff")){
      //If the player has an assassin, make the counter player reveal a card followed by being assassinated by the player 
      if (player.hasCard("assassin")){
        display.clear(); 
        display.println(player.getName() +" actually has an assassin");
        Introduction.countdown(3);
        player.trueBluff(deck, "assassin", display);
        counterPlayer.revealCard(deck, display);
        String assasinateOuput = Cards.assasinate(player, counterPlayer, deck, display);
        display.println(assasinateOuput); 
      } 
      //If the player does not have an assassin, make them lose a card
      else {
        display.clear(); 
        display.println(player.getName() +" doesn't have an assassin");
        Introduction.countdown(3);
        player.revealCard(deck, display);
        display.println(player.getName() + "'s turn has ended");
      }
    }
    //If the counter player chooses to accept, allow the player to assassinate the counter player 
    else {
      String assasinateOuput = Cards.assasinate(player, counterPlayer, deck, display);
      display.println(assasinateOuput);
      Introduction.countdown(3); 
    } 
  }
  
  
  /*
   * findPlayerTwo 
   * Method that finds a Player object from an array of Player objects given the name of the player
   * @param players - Player[] - existing Player[] of Player objects
   * @param playerTwoName - String - name of the player to find
   * @return Player - returns the player that was found
   */
  public static Player findPlayerTwo (Player [] players, String playerTwoName){
    //Variable declaration
    boolean foundPlayer; //boolean to store wether or not a player was found
    Player counterPlayer = new Player (0, "", "", ""); //Player object to store the found player
    
    //For loop to find a player by comparing the playerTwoName String to the names of Player objects in the players (Player[]) object 
    for (int i = 0; i < players.length; i++){
      //If a Player object's name matches the playerTwoName, set the counterPlayer to the Player object matched
      if ((players[i].getName()).equalsIgnoreCase(playerTwoName)){
        counterPlayer = players[i];
      }
    }
    return counterPlayer; 
  }
  
  
  /*
   * getSimilarity 
   * Method that gets the similarity between 2 strings and returns an int, 0 means the strings are the same and 
   * every number greater than 0 indicates a one letter difference
   * @param command - String - the excpected String
   * @param action - String - the string input by the user 
   * @return int - returns the similarity between the strings (0 means the strings are the same)
   */
  public static int getSimilarity(String command, String action) {
    //Variable declaration 
    String s1 = command.toLowerCase(); //String to store the command in all lowercase 
    String s2 = action.toLowerCase(); //String to store the action in all lowercase 
    int similarity = command.length(); //Set the initial similarity to the length of the command
    
    //For loop to check for similarity by comparing each character of s1 and s2, decreasing similarity by 1 for every match
    for (int i = 0; i < s1.length(); i++){
      if (i < s2.length()){
        if ((Character.toString(s1.charAt(i))).equals(Character.toString(s2.charAt(i)))){
          similarity -=1;
        }
      }
    }
    return (similarity);
  }
}//ssalc