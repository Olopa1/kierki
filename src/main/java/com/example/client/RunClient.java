package com.example.client;

import com.example.common.Deck;
import com.example.common.Hand;
import com.example.common.Player;
import com.example.exceptions.NotEnoughCardsInDeck;
import com.example.common.Table;
import com.example.common.OutcomeFunctionSet;

public class RunClient {
  public static void main(String[] args) {
    Player players[] = {new Player("player1"),new Player("player2"),new Player("player3"),new Player("player4")};
    Table table = new Table(players[0],players[1],players[2],players[3]);
    static OutcomeFunction functions[] = {firstDeal(),secondDeal(),thirdDeal(),fourthDeal(),fifthDeal(),sixthDeal(),seventhDeal()}
    table.playGame(firstDeal()); 

  }

  public static void temp(){
    System.out.println("Client\n");
    Deck test = new Deck(false);
    test.shuffle();
    Hand myHand = new Hand();
    try{
    for(int i = 0; i < 10 ;i++){
      myHand.takeCard(test.deal());
    }
    }catch(NotEnoughCardsInDeck e){
      e.getMessage();
    }
    myHand.displayHand();
  }

}
