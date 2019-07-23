import hsa.Console; 
import java.awt.*; 
import java.io.*;        
import javax.imageio.*;

/**
 * Player
 * @Author Jai Prajapati, Nawal Mehta 
 * @Revised 06/12/2018
 * Class that contains static methods for game actions for each card as well as contains methods to draw cards onto the console
 * */

public class Cards {
  
  public Cards (){
  }
  
  /*******************************************Duke Methods****************************************/
  
  /*
   * tax 
   * Method that handles the tax action of the duke by subtracting 3 coins from the deck and adding 3 coins to the player's hand
   * @param coins - GamePieces - existing GamePieces class
   * @param playerName - Player - existing Player class object of the player that is doing the tax action 
   */
  public static void tax (GamePieces coins, Player playerName){
    //Subtract 3 coins from the GamePieces total coins
    coins.subtractCoins(3);
    //Add 3 coins to the player's total coins
    playerName.addMoney(3);
  }
  
  
  /*
   * blockForeignAid 
   * Method that handles the block foreign aid action by returning a string stating who blocks who
   * @param player1 - Player - The player blocking foreign aid
   * @param player2 - Player - The player being blocked from using foreign aid 
   * @return - String - states who has blocked who from performing foreign aid
   */
  public static String blockForeignAid (Player player1, Player player2){
    return(player1.getName() + " has blocked " + player2.getName() + " from using foreign aid");
  }
  
  
  /*
   * drawDuke 
   * Method that draws the duke on the console provided at a specified location at a specified size
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */
  public static void drawDuke (Console display, int x, int y, double scale){
    Image img; //Declare an image object 
    //Try catch to access an image at the specified file location
    try{
      //Initialise the image to the image at the file location
      img= ImageIO.read(new File("Duke.jpg"));
      //Scale the image to the specified scale factor
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Display the image on the console
    display.drawImage(img, x, y, null);
  }
  
  /*******************************************Ambassador Methods****************************************/
  
  
  /*
   * exchangeCards 
   * Method that allows a player to exchange their cards with two cards from the deck of cards
   * @param display - Console - The console the card is being drawn on
   * @param player - Player - existing player object of the player performing the exchange cards action
   * @param deck - GamePieces - existing GamePieces object 
   */
  public static void exchangeCards (GamePieces deck, Player player, Console display){
    //Clear the display console
    display.clear(); 
    String cardOneDistributed = ""; //String to contain the first card distributed by the deck 
    String cardTwoDistributed = ""; //String to contain the second card distributed by the deck 
    String playerCardOne = ""; //String to contain the players first card
    String playerCardTwo = ""; //String to contain the players second card
    String cardToKeepOne = ""; //String to contain the first card the player wants to keep
    String cardToKeepTwo = ""; //String to contain the second card the player wants to keep
    int playerNumCards = player.getNumberCards(); //Set the amount of cards the player has
    
    //Distribute two cards from the deck and save them to the card distributed variables
    cardOneDistributed = deck.distribute(); 
    cardTwoDistributed = deck.distribute(); 
    
    //For loop to remove the players current cards (1 or 2) to be exchanged temporarily, saving them to the players cards variables
    for (int i = 1; i <= (player.getNumberCards()); i++){
      if ((i == 1)&& !(player.getCard(1).equals(""))){
        playerCardOne = player.removeCardNumber(1); 
      } else if((i == 1)&& !(player.getCard(2).equals(""))){
        playerCardOne = player.removeCardNumber(2); 
      }
      if (i == 2){
        playerCardTwo = player.removeCardNumber(2); 
      }
    }
    Introduction.countdown(5, display, player.getName()); 
    //Draw the cards from the player's hand as well as deck on the console in a square (3 or 4 cards depending on player's initial amount of cards)
    drawCard (cardOneDistributed, display, 100, 100, 1);     
    drawCard (cardTwoDistributed, display, 300, 100, 1); 
    if (!playerCardOne.equals("")){
      player.subtractNumberCards(); 
      drawCard (playerCardOne, display, 100, 300, 1);
    }
    if (!playerCardTwo.equals("")){
      player.subtractNumberCards(); 
      drawCard (playerCardTwo, display, 300, 300, 1);     
    }
    
    //If the player had 1 card initially, ask them which one card they would like to keep and add it to the player's hand
    if (playerNumCards == 1){
      display.println("Which card would you like to keep (name of card)?"); 
      cardToKeepOne = display.readLine();
      player.addToCards(cardToKeepOne);
      
      //If the player had 2 cards initially, ask them which two cards they would like to keep and add them to the player's hand
    } else if (playerNumCards == 2){
      display.println("Which two cards would you like to keep (name of card)?"); 
      cardToKeepOne = display.readLine(); 
      player.addToCards(cardToKeepOne); 
      cardToKeepTwo = display.readLine(); 
      player.addToCards(cardToKeepTwo); 
    }
    
    /*
     * Return unwanted cards back into the deck based on the ones the player wants to keep. This is done by comparing the distributed
     * cards and initial player cards to the ones the player chose to keep
     * */
    if (playerNumCards == 2){
      if (!cardOneDistributed.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(cardOneDistributed);
      } else if (!cardTwoDistributed.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(cardTwoDistributed);
      } else if (!playerCardOne.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(playerCardOne); 
      } else if (!playerCardTwo.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(playerCardTwo); 
      }
      if (!cardOneDistributed.equalsIgnoreCase(cardToKeepTwo)){
        deck.addToDeck(cardOneDistributed);
      } else if (!cardTwoDistributed.equalsIgnoreCase(cardToKeepTwo)){
        deck.addToDeck(cardTwoDistributed);
      } else if (!playerCardOne.equalsIgnoreCase(cardToKeepTwo)){
        deck.addToDeck(playerCardOne); 
      } else if (!playerCardTwo.equalsIgnoreCase(cardToKeepTwo)){
        deck.addToDeck(playerCardTwo); 
      }
    } else {
      if (!cardOneDistributed.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(cardOneDistributed);
      } 
      if (!cardTwoDistributed.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(cardTwoDistributed);
      } 
      if (!playerCardOne.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(playerCardOne); 
      } 
      if (!playerCardTwo.equalsIgnoreCase(cardToKeepOne)){
        deck.addToDeck(playerCardTwo); 
      }
    }
  }
  
  
  /*
   * drawAmbassador 
   * Method that draws the Ambassador on the console provided at a specified location at a specified size
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */
  public static void drawAmbassador (Console display, int x, int y, double scale){
    Image img; //Declare an image object 
    //Try catch to access an image at the specified file location
    try{
      //Initialise the image to the image at the file location
      img= ImageIO.read(new File("Ambassador.jpg"));
      //Scale the image to the specified scale factor
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Display the image on the console
    display.drawImage(img, x, y, null);
  }
  
  /*******************************************Assassin Methods****************************************/
  
  
  /*
   * assasinate 
   * Method that handles the assasinate action by returning a string stating who assasinates who as well as subtracting
   * 3 coins from the player assasinating's money count and making the assasinated player reveal a card
   * @param player1 - Player - The player assasinating
   * @param player2 - Player - The player being assasinated 
   * @param deck - GamePieces - exisiting GamePieces object 
   * @param display - Console - The console on which to display the actions 
   * @return - String - states who has assasinated who
   */
  public static String assasinate (Player player1, Player player2, GamePieces deck, Console display){
    //Make the assasinated player reveal a card
    player2.revealCard(deck, display);
    //Make the assasinating player lose 3 coins
    player1.subtractMoney(3); 
    return(player1.getName() + " has assasinated " + player2.getName());
  }
  
  
  /*
   * drawAssasin 
   * Method that draws the Assassin on the console provided at a specified location at a specified size
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */
  public static void drawAssasin (Console display, int x, int y, double scale){
    Image img; //Declare an image object 
    //Try catch to access an image at the specified file location
    try{
      //Initialise the image to the image at the file location
      img= ImageIO.read(new File("Assasin.jpg"));
      //Scale the image to the specified scale factor
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Display the image on the console
    display.drawImage(img, x, y, null);
  }
  
  /*******************************************Captain Methods****************************************/
  
  
  /*
   * steal 
   * Method that handles the stealing action by returning a string stating who steals from who as well as adding
   * 2 coins to the player stealing's money count and subtracting 2 coins from the player who got robbed
   * @param player1 - Player - The player stealing
   * @param player2 - Player - The player being stolen from 
   * @return - String - states who has stolen from who
   */
  public static String steal (Player player1, Player player2){
    //Add 2 coins to the player stealing
    player1.addMoney(2);
    //Subtract 2 coins from the player being stolen from
    player2.subtractMoney(2);
    return(player1.getName() + " has stolen two coins from " + player2.getName());
  }
  
  
  /*
   * blockSteal 
   * Method that returns a String stating who has blocked who from stealing coins 
   * @param player1 - Player - The player blocking
   * @param player2 - Player - The player stealing 
   * @return - String - states who has blocked who from stealing coins
   */
  public static String blockSteal (Player player1, Player player2){
    return(player1.getName() + " has blocked " + player2.getName() + " from stealing coins");
  }
  
  
  /*
   * drawCaptain 
   * Method that draws the Captain on the console provided at a specified location at a specified size
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */
  public static void drawCaptain (Console display, int x, int y, double scale){
    Image img; //Declare an image object 
    //Try catch to access an image at the specified file location
    try{
      //Initialise the image to the image at the file location
      img= ImageIO.read(new File("Captain.jpg"));
      //Scale the image to the specified scale factor
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Display the image on the console
    display.drawImage(img, x, y, null);
  }
  
  /*******************************************Captain Methods****************************************/
  
  
  /*
   * blockAssassination 
   * Method that returns a String stating who has blocked who from assasinating them 
   * @param player1 - Player - The player blocking assassination
   * @param player2 - Player - The player assassinating 
   * @return - String - states who has blocked who from assassinating them
   */
  public static String blockAssassination (Player player1, Player player2){
    return(player1.getName() + " has prevented " + player2.getName() + " from committing the assassination");
  }
  
  
  /*
   * drawContessa 
   * Method that draws the Contessa on the console provided at a specified location at a specified size
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */
  public static void drawContessa (Console display, int x, int y, double scale){
    Image img; //Declare an image object 
    //Try catch to access an image at the specified file location
    try{
      //Initialise the image to the image at the file location
      img= ImageIO.read(new File("Contessa.jpg"));
      //Scale the image to the specified scale factor
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Display the image on the console
    display.drawImage(img, x, y, null);
  }
  
  /*******************************************Drawing Methods****************************************/
  
  /*
   * drawFaceDownCard 
   * Method that draws a face down card on the console provided at a specified location at a specified size
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */
  public static void drawFaceDownCard (Console display, int x, int y, double scale){
    Image img; //Declare an image object 
    //Try catch to access an image at the specified file location
    try{
      //Initialise the image to the image at the file location
      img= ImageIO.read(new File("BackCard.jpg"));
      //Scale the image to the specified scale factor
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    //Display the image on the console
    display.drawImage(img, x, y, null);
  }
  
  
  /*
   * drawCard 
   * Method that draws a specific card on the console provided at a specified location at a specified size
   * @param card - String - the name of the card to be displayed
   * @param display - Console - The console the card is being drawn on
   * @param x - int - The x coordinate of the top left of the image on the console
   * @param y - int - The y coordinate of the top left of the image on the console 
   * @param scale - double - The scale applied to the image, making it bigger (scale>1) or smaller (scale<1)
   */ 
  public static void drawCard (String card, Console display, int x, int y, double scale){
    if (card.equalsIgnoreCase("duke")){
      drawDuke(display, x, y, scale); 
    } else if (card.equalsIgnoreCase("ambassador")){
      drawAmbassador(display, x, y, scale); 
    } else if (card.equalsIgnoreCase("assassin")){
      drawAssasin(display, x, y, scale); 
    } else if (card.equalsIgnoreCase("captain")){
      drawCaptain(display, x, y, scale); 
    } else if (card.equalsIgnoreCase("contessa")){
      drawContessa(display, x, y, scale); 
    } else if (card.equalsIgnoreCase("card")){
      drawFaceDownCard(display, x, y, scale); 
    }
  }
  
}