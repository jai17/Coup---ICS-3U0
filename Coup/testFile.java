import hsa.Console;
import java.awt.*;       
import java.io.*;        
import javax.imageio.*;

public class Coup {
  
  static Console c;
  static Console d;
  static Console e; 
  
  public static void main (String [] args){
    c= new Console(33,80);
    d= new Console();
    e= new Console();
    
    GamePieces s1= new GamePieces();
    
    Introduction s2= new Introduction(d, s1);
    s2.runIntro();
    s1.drawTable(c);
    s1.drawBoard(c, s2.getNumPlayers());
    s1.drawBank(c);
    
    for (int i=0; i<s2.getNumPlayers(); i=i+1){
      s1.drawAllCoins(c, i, s2.getNumPlayers(), s2.getPlayerDeck(), s2.getPlayerNames());
    }
    
    s1.drawRemainingCards(c);
      
    
    
    
  }
}