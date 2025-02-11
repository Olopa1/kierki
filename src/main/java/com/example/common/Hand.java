package com.example.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Hand implements Serializable{
  private ArrayList<Card> cards;
  
  public Hand(){
    this.cards = new ArrayList<>();
  }
 
  public void sortCardsInHand(){
    cards.sort(Comparator.comparing(Card::getColor).thenComparing(Card::getValue));
  }

  public Boolean isHandEmpty(){
    return this.cards.isEmpty();
  }

  public void takeCard(Card card){
    this.cards.add(card);
    this.sortCardsInHand();
  }

  public Card playCard(int cardNumber){
    return this.cards.remove(cardNumber);
  }
  
  public void displayHand(){
    for(Card card : this.cards){
      card.displayCard();
    }
  }

  public ArrayList<Card> getHand(){
    return this.cards;
  }

  public void emptyHand(){
    this.cards.clear();
  }

  public int getNumberOfCards(){
    return this.cards.size();
  }

  public Card getCard(int index){
    return this.cards.get(index);
  }
}
