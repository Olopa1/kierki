package com.example.common;

import java.io.Serializable;

public class Player implements Serializable{
  private String playerName;
  private int score;
  private Hand hand;
  private boolean firstPlayer;
  private Card lastPlayedCard;

  public Player(String playerName){
    this.playerName = playerName;
    this.score = 0;
    this.hand = new Hand();
    this.firstPlayer = false;
    this.lastPlayedCard = null;
  }

  public void setHand(Hand newHand){
    this.hand = newHand;
  }
  
  public void toggleFirstPlayer(){
    this.firstPlayer = !this.firstPlayer;
  }
  
  public void changeScore(int value){
    this.score += value;
  }

  public int getScore(){
    return this.score;
  }

  public Boolean getFirstPlayer(){
    return this.firstPlayer;
  }

  public Card getLastPlayedCard(){
    return this.lastPlayedCard;
  }

  public Card playCard(int cardIndex){
    this.lastPlayedCard = hand.playCard(cardIndex);
    return this.lastPlayedCard;
  }

  public void addCardToHand(Card card){
    this.hand.takeCard(card);
  }

  public String getPlayerName(){
    return this.playerName;
  }

  public void clearHand(){
    this.hand.emptyHand();
  }
}
