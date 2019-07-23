import hsa.Console;
import java.awt.*;       
import java.io.*;        
import javax.imageio.*;

/**
 * GamePieces
 * @Author Nawal Mehta
 * @Revised 06/12/2018
 * Class that handles the bank and deck, aswell as updating the output on the display screen (table, coins, face down cards, revealed cards, etc.)
 * */
public class GamePieces{
  
  //initialize variables/attributes
  private int coins;                  //stores all coins that the players don't have
  private String [] deck;             //array that contains all cards
  private int revealedDukes;          //stores number of revealed dukes
  private int revealedCaptains;       //stores number of revealed captains
  private int revealedContessas;      //stores number of revealed contessas
  private int revealedAmbassadors;    //stores number of revealed ambassadors
  private int revealedAssassins;      //stores number of revealed assassins
  
  /*
   * Gamepieces
   * Constructor for the Gamepieces class that stores an array for the deck, the number of total coins and number of cards revealed for each type
   * */
  public GamePieces(){
    coins = 50;
    deck = new String[]{"duke","duke","duke","ambassador","ambassador","ambassador","captain","captain","captain","contessa","contessa","contessa","assassin","assassin","assassin"}; 
    revealedDukes=0;
    revealedCaptains=0;
    revealedContessas=0;
    revealedAmbassadors=0;
    revealedAssassins=0;
  }
  
  /*
   * getTotalCoins
   * Method that returns the number of coins that aren't held by players
   * */
  public Integer getTotalCoins(){
    return (coins);
  }
  
  /*
   * addCoins
   * Method that adds coins to the total number of coins
   * */
  public void addCoins(int numCoins){
    coins=coins+numCoins;
  }
  
  /*
   * addCoins
   * Method that subtracts coins from the total number of coins
   * */
  public void subtractCoins(int numCoins){
    coins=coins-numCoins;
  }
  
  /*
   * distribute
   * Method that randomly distributes a card from the deck
   * */
  public String distribute(){
    String card="";
    while (card.equals("")){
      int element= (int)(Math.random()*14)+1;
      if (!(deck[element].equals(""))){
        card=deck[element];
        deck[element]="";
      }
    }
    return (card);
  }
  
  /*
   * addToDeck
   * Method that adds a card from a player, back into the deck
   * */
  public void addToDeck(String character){
    while(true){
      for (int x=0; x<15; x=x+1){
        if (deck[x]==""){
          deck[x]=character;
          break;
        }
      }
      break;
    }
  }
  
  /*
   * drawTable
   * Method that draws an image of a table on the display console
   * */
  public void drawTable (Console c){
    Image img;
    try{
      img= ImageIO.read(new File("table.jpg"));
      img= img.getScaledInstance(650, 650, Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    c.drawImage(img, 0, 0, null);
  }
  
  /*
   * backToBank
   * Method that adds a players coins back to the bank/total coins, after the player has lost
   * */
  public void backToBank(Player playerDeck[], int numPlayers){
    for (int x=0; x<numPlayers; x=x+1){
      if ((playerDeck[x].getCard(1).equalsIgnoreCase(""))&&(playerDeck[x].getCard(2).equalsIgnoreCase(""))){
        coins= coins + playerDeck[x].getMoney();
        playerDeck[x].subtractMoney(playerDeck[x].getMoney());
      }
    }
  }
  
  /*
   * drawNames
   * Method that outputs each player name in a specific location on the display console
   * */
  public void drawNames(Player playerDeck[], int numPlayers, Console c){
    for (int x=0; x<numPlayers; x=x+1){
      if (x==0){
        c.setCursor(26,13);
        c.print(playerDeck[0].getName());
      }else if (x==1){
        c.setCursor(5,13);
        c.print(playerDeck[1].getName());
      }else if (x==2){
        c.setCursor(26,45);
        c.print(playerDeck[2].getName());
      }else if (x==3){
        c.setCursor(5,45);
        c.print(playerDeck[3].getName());
      }else if (x==4){
        c.setCursor(5,29);
        c.print(playerDeck[4].getName());
      }else if (x==5){
        c.setCursor(26,29);
        c.print(playerDeck[5].getName());
      }
    }
  }
 
  /*
   * drawPlayerCoins
   * Method that draws an image of a coin on the display console
   * */
   public static void drawPlayerCoins (Console display, int x, int y, double scale){
    Image img;
    try{
      img= ImageIO.read(new File("playerCoins.png"));
      img= img.getScaledInstance((int)(150*scale), (int)(150*scale), Image.SCALE_SMOOTH);
    }
    catch(IOException e){
      img=null;
    }
    display.drawImage(img, x, y, null);
  }
   
  /*
   * revealCard
   * Method that changes the value of the revealed cards, depending on which card is being revealed
   * */
  public void revealCard(String cardName){
   if (cardName.equalsIgnoreCase("duke")){
     revealedDukes = revealedDukes+1;
   }else if (cardName.equalsIgnoreCase("captain")){
     revealedCaptains = revealedCaptains+1;
   }else if (cardName.equalsIgnoreCase("ambassador")){
     revealedAmbassadors = revealedAmbassadors+1;
   }else if (cardName.equalsIgnoreCase("contessa")){
     revealedContessas = revealedContessas+1;
   }else if (cardName.equalsIgnoreCase("assassin")){
     revealedAssassins = revealedAssassins+1;
   }
  }
  
  /*
   * drawRevealedCard
   * Method that draws all the revealed cards on the display console
   * */
  public void drawRevealedCard(Console c){
    
    if (revealedDukes==1){
      Cards.drawDuke(c,55,202,0.25);
    }else if (revealedDukes==2){
      Cards.drawDuke(c,55,202,0.25);
      Cards.drawDuke(c,60,207,0.25);
    }else if (revealedDukes==3){
      Cards.drawDuke(c,55,202,0.25);
      Cards.drawDuke(c,60,207,0.25);
      Cards.drawDuke(c,65,212,0.25);
    }
    
    if (revealedCaptains==1){
      Cards.drawCaptain(c,545,202,0.25);
    }else if (revealedCaptains==2){
      Cards.drawCaptain(c,545,202,0.25);
      Cards.drawCaptain(c,550,207,0.25);
    }else if (revealedCaptains==3){
      Cards.drawCaptain(c,545,202,0.25);
      Cards.drawCaptain(c,550,207,0.25);
      Cards.drawCaptain(c,555,212,0.25);
    }
    
    if (revealedAmbassadors==1){
      Cards.drawAmbassador(c,300,202,0.25);
    }else if (revealedAmbassadors==2){
      Cards.drawAmbassador(c,300,202,0.25);
      Cards.drawAmbassador(c,305,207,0.25);
    }else if (revealedAmbassadors==3){
      Cards.drawAmbassador(c,300,202,0.25);
      Cards.drawAmbassador(c,305,207,0.25);
      Cards.drawAmbassador(c,310,212,0.25);
    }
    
    if (revealedContessas==1){
      Cards.drawContessa(c,178,202,0.25);
    }else if (revealedContessas==2){
      Cards.drawContessa(c,178,202,0.25);
      Cards.drawContessa(c,183,207,0.25);
    }else if (revealedContessas==3){
      Cards.drawContessa(c,178,202,0.25);
      Cards.drawContessa(c,183,207,0.25);
      Cards.drawContessa(c,188,212,0.25);
    }
    
    if (revealedAssassins==1){
      Cards.drawAssasin(c,422,202,0.25);
    }else if (revealedAssassins==2){
      Cards.drawAssasin(c,422,202,0.25);
      Cards.drawAssasin(c,427,207,0.25);
    }else if (revealedAssassins==3){
      Cards.drawAssasin(c,422,202,0.25);
      Cards.drawAssasin(c,427,207,0.25);
      Cards.drawAssasin(c,432,212,0.25);
    }
  }
  
  /*
   * getRemainingCards
   * Method that returns the amount of cards remaining within the deck
   * */
  public int getRemainingCards(){
    int cards=0;
    for (int x=0; x<15; x=x+1){
      if (!(deck[x].equalsIgnoreCase(""))){
        cards=cards+1;
      }
    }
    return(cards);
  }
  
  /*
   * drawRemainingCards
   * Method that draws all of the remaining cards, facedown, on the display console
   * */
  public void drawRemainingCards(Console c){
    int cards= getRemainingCards();
    for (int x=0; x<cards; x=x+1){
      Cards.drawFaceDownCard(c,140+(x*40),360,0.25);
    }
  }
    
  /*
   * drawBoard
   * Method that draws all the facedown cards for each player, depending on the amount of players and on how many cards each player has
   * */    
  public void drawBoard(Console c, int numPlayers, Player playerDeck[]){
    if ((numPlayers>=3)&&(numPlayers<=6)){
      if (!(playerDeck[2].getCard(1).equalsIgnoreCase(""))){
        Cards.drawFaceDownCard(c,460,500,0.25);
      }
      if (!(playerDeck[2].getCard(2).equalsIgnoreCase(""))){
        Cards.drawFaceDownCard(c,500,500,0.25);
      }
      if (!(playerDeck[1].getCard(1).equalsIgnoreCase(""))){
        Cards.drawFaceDownCard(c,110,125,0.25);
      }
      if (!(playerDeck[1].getCard(2).equalsIgnoreCase(""))){
        Cards.drawFaceDownCard(c,150,125,0.25);
      }
      if (!(playerDeck[0].getCard(1).equalsIgnoreCase(""))){
        Cards.drawFaceDownCard(c,110,500,0.25);
      }
      if (!(playerDeck[0].getCard(2).equalsIgnoreCase(""))){
        Cards.drawFaceDownCard(c,150,500,0.25);
      }
      if (numPlayers>=4){
        if (!(playerDeck[3].getCard(1).equalsIgnoreCase(""))){
          Cards.drawFaceDownCard(c,460,125,0.25);
        }
        if (!(playerDeck[3].getCard(2).equalsIgnoreCase(""))){
          Cards.drawFaceDownCard(c,500,125,0.25);
        }
        if (numPlayers>=5){
          if (!(playerDeck[4].getCard(1).equalsIgnoreCase(""))){
            Cards.drawFaceDownCard(c,325,125,0.25);
          }
          if (!(playerDeck[4].getCard(2).equalsIgnoreCase(""))){
            Cards.drawFaceDownCard(c,285,125,0.25);
          }
          if (numPlayers==6){
            if (!(playerDeck[5].getCard(1).equalsIgnoreCase(""))){
              Cards.drawFaceDownCard(c,325,500,0.25);
            }
            if (!(playerDeck[5].getCard(2).equalsIgnoreCase(""))){
              Cards.drawFaceDownCard(c,285,500,0.25);
            }
          }
        }
      }
    }
  }
  
  /*
   * drawAllCoins
   * Method that draws all the coins held by each player, on the display console in a specific position
   * */
  public void drawAllCoins(Console c, int element, int numPlayers, Player playerDeck[], String playerNames[]){
    int numCoins=0;
    int playerNum=0;
    for (int x=0; x<numPlayers;x=x+1){
      if (playerNames[element].equalsIgnoreCase(playerNames[x])){
        numCoins = playerDeck[x].getMoney();
        playerNum=x+1;
      }
    }
    if (playerNum==1){
      if (numCoins<=5){
        for (int i=0; i<numCoins; i=i+1){
          drawPlayerCoins(c,107+(i*16), 475,0.1);
        }
      }
      else{
        for (int i=0; i<5; i=i+1){
          drawPlayerCoins(c,107+(i*16), 475,0.1);
        }
        for (int i=0; i<(numCoins-4); i=i+1){
          drawPlayerCoins(c,107+(i*16), 455,0.1);
        }
      }
    }
    else if (playerNum==2){
      if (numCoins<=5){
        for (int i=0; i<numCoins; i=i+1){
          drawPlayerCoins(c,107+(i*16), 170,0.1);
        }
      }
      else{
        for (int i=0; i<5; i=i+1){
          drawPlayerCoins(c,107+(i*16), 170,0.1);
        }
        for (int i=0; i<(numCoins-4); i=i+1){
          drawPlayerCoins(c,107+(i*16), 190,0.1);
        }
      }
    }
    else if (playerNum==3){
      if (numCoins<=5){
        for (int i=0; i<numCoins; i=i+1){
          drawPlayerCoins(c,457+(i*16), 475,0.1);
        }
      }
      else{
        for (int i=0; i<5; i=i+1){
          drawPlayerCoins(c,457+(i*16), 475,0.1);
        }
        for (int i=0; i<(numCoins-4); i=i+1){
          drawPlayerCoins(c,457+(i*16), 455,0.1);
        }
      }
    }
    else if (playerNum==4){
      if (numCoins<=5){
        for (int i=0; i<numCoins; i=i+1){
          drawPlayerCoins(c,457+(i*16), 170,0.1);
        }
      }
      else{
        for (int i=0; i<5; i=i+1){
          drawPlayerCoins(c,457+(i*16), 170,0.1);
        }
        for (int i=0; i<(numCoins-4); i=i+1){
          drawPlayerCoins(c,457+(i*16), 190,0.1);
        }
      }
    }
    else if (playerNum==5){
      if (numCoins<=5){
        for (int i=0; i<numCoins; i=i+1){
          drawPlayerCoins(c,282+(i*16), 170,0.1);
        }
      }
      else{
        for (int i=0; i<5; i=i+1){
          drawPlayerCoins(c,282+(i*16), 170,0.1);
        }
        for (int i=0; i<(numCoins-4); i=i+1){
          drawPlayerCoins(c,282+(i*16), 190,0.1);
        }
      }
    }
    else if (playerNum==6){
      if (numCoins<=5){
        for (int i=0; i<numCoins; i=i+1){
          drawPlayerCoins(c,282+(i*16), 475,0.1);
        }
      }
      else{
        for (int i=0; i<5; i=i+1){
          drawPlayerCoins(c,282+(i*16), 475,0.1);
        }
        for (int i=0; i<(numCoins-4); i=i+1){
          drawPlayerCoins(c,282+(i*16), 455,0.1);
        }
      }
    }
   }
  
  /*
   * noBluff
   * Method that runs when a player was called out on a bluff but wasn't lying; it reveals a card, returns it to the deck and then distributes a new card
   * */
   public String noBluff(String cardName, Console c){
     Cards.drawCard(cardName, c, 50, 50, 2); 
     addToDeck(cardName);
     return (distribute());
   }
   
   /*
   * drawBank
   * Method that draws all the coins not held by the players, on the display console
   * */
   public void drawBank(Console c){
     int coins= getTotalCoins();
     if (coins<=25){
       for (int i=0; i<coins;i=i+1){
         drawPlayerCoins(c,130+(i*16), 320, 0.1);
        }
      }
      else if (coins>25){
        for (int i=0; i<25; i=i+1){
          drawPlayerCoins(c,130+(i*16), 320, 0.1);
        }
        for (int i=0; i<(coins-25); i=i+1){
          drawPlayerCoins(c,130+(i*16), 340, 0.1);
        }
      }
   }
   
   /*
   * updateBoard
   * Method that updates the board after everyturn
   * */
   public void updateBoard(Console c, int numPlayers, Player playerDeck[], String playerNames[]){
     drawTable(c);
     drawNames(playerDeck, numPlayers, c);
     backToBank(playerDeck, numPlayers);
     drawBank(c);
     for (int i=0; i<numPlayers; i=i+1){
      drawAllCoins(c, i, numPlayers, playerDeck, playerNames);
     }
     drawBoard(c, numPlayers, playerDeck);
     drawRemainingCards(c);
     drawRevealedCard(c);
   }
   
   
  public void showRules(Console c, Console e){
    c.setCursor(33, 10);
    c.print("Enter in 'Y' to see Movelist: ");
    if (c.readLine().equalsIgnoreCase("Y")){
     drawRules(e);  
    }
  }
    
  public void drawRules(Console e){
    Image img;
    try{
      img= ImageIO.read(new File("rules.png"));
      img= img.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
    }
    catch(IOException j){
      img=null;
    }
    e.drawImage(img, 0, 0, null);
  }
}