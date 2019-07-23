import hsa.Console; 
import java.awt.*;

/**
 * Player
 * @Author Jai Prajapati 
 * @Revised 06/10/2018
 * Class that handles the player object created in the main program. Stores the players name, cards and coins along with
 * methods to manipulate the players cards and coins as well as return queries for information about the player and their cards/money
 * */
public class Player {
  
  private int money;
  private int numCards; 
  private String card1; 
  private String card2; 
  private String playerName; 
  public boolean mandatoryCoup = false;
  
  
  public Player (int money, String card1, String card2, String playerName){
    
    this.money = money; 
    this.card1 = card1; 
    this.card2 = card2; 
    this.playerName = playerName;
    numCards = 0;
    addToCards(card1, card2);
  
  }
  
  public void addToCards (String cardOne, String cardTwo){
    
    String card = cardOne;
    
    for (int i = 0; i <= 1; i++){
      if (i == 0){
        card1 = card;
      } else if (i == 1){
        card2 = card;
      }
      card = cardTwo;
    }
    numCards += 2;
  }
  
  
  public void addToCards (String cardOne){
    
    String card = cardOne;
    
    if (card1.equals("")){
      card1 = card;
    } else if (card2.equals("")){
      card2 = card;
    }
    
    numCards += 1;
  }
  
  public String removeCard (String cardToRemove){
    String cardRemoved;
    if (card1.equalsIgnoreCase(cardToRemove)){
      cardRemoved = card1;
      card1 = "";
      numCards = numCards - 1;
    } else if (card2.equalsIgnoreCase(cardToRemove)){
      cardRemoved = card2; 
      card2 = ""; 
      numCards = numCards - 1;
    } else {
      cardRemoved = "error";
    }
    
    return (cardRemoved);
  }
  
  public String removeCardNumber (int cardToRemove){
    String cardRemoved="";
    if (cardToRemove == 1){
      cardRemoved = card1;
      card1 = "";
    } else if (cardToRemove == 2){
      cardRemoved = card2;
      card2 = ""; 
    }
    return (cardRemoved);
  }
  
  public void subtractNumberCards(){
    numCards = numCards - 1; 
  }
  
  
  public void income (GamePieces deck){
    deck.subtractCoins(1);
    addMoney(1);
  }
  
  
  public void foreignAid (GamePieces deck){
    deck.subtractCoins(2); 
    addMoney(2);
  }
  
  
  public void coup (Player player2, GamePieces deck, Console display){
    subtractMoney(7);
    display.println(player2.getName() + "has been couped by: " + playerName);
    Introduction.countdown(2); 
    player2.revealCard(deck, display);
  }
  
  public void mandatoryCoup (Console display){
    if(mandatoryCoup){
      display.println(playerName + " must coup as he/she has " + money + "coins");
    }
  }
  
  public boolean checkMandatoryCoup(){
    if ((money >= 10) && !mandatoryCoup){
      mandatoryCoup = true;
    } else {
      mandatoryCoup = false;
    }
    return mandatoryCoup;
  }
  
  public int getMoney(){
    return (money);
  }
  
  
  public int getNumberCards(){
    return (numCards);
  }
  
  public void revealCard (GamePieces deck, Console display){
    Introduction.countdown(5, display, playerName);
    String card;
    boolean onOne = false;
    boolean onTwo = false;
    String cardToReveal;
    //display.clear();
    if (!(card1.equalsIgnoreCase(""))){
      card = card1;
      onOne = true;
    } else {
      card = card2;
      onTwo = true;
    }
    for (int i = 1; i <= numCards; i++){
       
      Cards.drawCard(card, display, 100*i, 100*i, 1);
      if (onOne){
        card = card2;
      } else if (onTwo){
        card = card1;
      }
    }
    display.setCursor (20, 10); 
    display.println("Which card would you like to reveal? (Name of card)");
    cardToReveal = display.readLine();
    if (cardToReveal.equalsIgnoreCase(card1)){
      deck.revealCard(card1);
      display.clear(); 
      display.println(playerName + " has revealed a " + card1);
      Cards.drawCard(card1, display, 50, 50, 2);
      //deck.drawRevealedCard(display);
      Introduction.countdown(5); 
      removeCard(cardToReveal);
//card1 = "";
    } else if (cardToReveal.equalsIgnoreCase(card2)){
      deck.revealCard(card2);
      display.clear(); 
      display.println(playerName + " has revealed a " + card2);
      Cards.drawCard(card2, display, 50, 50, 2);
      //deck.drawRevealedCard(display);
      Introduction.countdown(5); 
      removeCard(cardToReveal);
//card2 = "";
    }
  }
  
  
  public void trueBluff (GamePieces deck, String cardToReveal, Console display){
    String newCard = deck.noBluff(cardToReveal, display);
    Introduction.countdown(4); 
    
    removeCard(cardToReveal);
    Introduction.countdown(5, display, playerName); 
    addToCards(newCard);
    display.setCursor(2,2);
    display.println(playerName + ": here is your new card");
    Cards.drawCard(newCard, display, 100, 100, 2);
    Introduction.countdown(5); 
  }
  
  public void addMoney (int x){
    money += x;
  }
  
  public void subtractMoney (int x){
    money -= x;
  }
  public String callBluff (Player player2){
    String sentence = (getName() + "has called bluff on " + player2.getName());
    return sentence;
  }
  
  public String getName(){
    return playerName;
  }
  
  
  public String getCard(int cardNumber){
    String returnCard="";
    if (cardNumber == 1){
      returnCard= card1; 
    } 
    else if (cardNumber == 2){
      returnCard= card2; 
    }
    return (returnCard);
  }
  
  
  public boolean hasCard (String card) {
    if (card1.equalsIgnoreCase(card)){
      return true;
    } else if (card2.equalsIgnoreCase(card)){
      return true; 
    } else {
      return false; 
    }
  }
    
}