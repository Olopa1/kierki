package com.example.client;

import com.example.common.Deck;
import com.example.common.Hand;
import com.example.exceptions.NotEnoughCardsInDeck;

public class RunClient {
  public static void main(String[] args) {
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
