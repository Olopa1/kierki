package com.example.common;

import com.example.exceptions.NotEnoughCardsInDeck;

public class Table {
  private Player players[];
  private Deck startingDeck;
  private Deck discardedDeck;
  private int trick;//lewa
  private Colors trumpColor;

  public Table(Player player1, Player player2, Player player3, Player player4) {
    this.players = new Player[] { player1, player2, player3, player4 };
    this.startingDeck = new Deck(false);
    this.discardedDeck = new Deck(true);
    this.trumpColor = null;
    this.players[0].toggleFirstPlayer();
    this.trick = 1;
  }

  public void playGame(OutcomeFunction function) throws NotEnoughCardsInDeck {
    startingDeck.shuffle();
    this.dealCards();

    for(Player player : this.players){
      player.playCard(0);
    }
    for(Player player : this.players){
      discardedDeck.putOnDeck(player.getLastPlayedCard());
    }
    while(!this.determinePoints(function)){
      for(Player player : this.players){
        player.playCard(0);
      }

      for(Player player : this.players){
        discardedDeck.putOnDeck(player.getLastPlayedCard());
      }
      this.printPoints();
    }
    this.printPoints();
    discardedDeck.restoreDeck();
    startingDeck.restoreDeck();
    for(Player player : this.players){
      player.clearHand();
    }
  }
  
  private void printPoints(){
    for(Player player : this.players){
      System.out.println("Player: " + player.getPlayerName() + "have: " + player.getScore() + " points."); 
    }
  }

  public boolean determinePoints(OutcomeFunction function) {
    if (function.detrminePoints(this.players, this.discardedDeck, this.trick, this.trumpColor)) {
      startingDeck.restoreDeck();
      discardedDeck.restoreDeck();
      this.clearPlayerHands();
      this.trick = 1;
      return true;
    }
    this.trick++;
    return false;
  }

  private void clearPlayerHands() {
    for (Player player : players) {
      player.clearHand();
    }
  }

  private void dealCards() throws NotEnoughCardsInDeck {
    int deckSize = this.startingDeck.countCards();
    try {
      for (int i = 0; i < deckSize; i++) {
        this.players[i % 4].addCardToHand(this.startingDeck.deal());
      }
    } catch (NotEnoughCardsInDeck e) {
      throw e;
    }
  }

  private Player findBiggestCard() {
    Player currentFirstPlayer = null;
    Player biggestPlayed = currentFirstPlayer;
    Colors trumpCard;
    for (Player player : this.players) {
      if (player.getFirstPlayer()) {
        currentFirstPlayer = player;
        break;
      }
    }

    trumpCard = currentFirstPlayer.getLastPlayedCard().getColor(); // karta atutowa

    for (Player player : this.players) {
      if (currentFirstPlayer == player) {
        continue;
      }
      if (player.getLastPlayedCard().getColor() == trumpCard) {
        if (player.getLastPlayedCard().getValue() > biggestPlayed.getLastPlayedCard().getValue()) {
          biggestPlayed = player;
        }
      }
    }
    currentFirstPlayer.toggleFirstPlayer();
    biggestPlayed.toggleFirstPlayer();
    return biggestPlayed;
  }

  private Player findBiggestCard(Colors trumpCardColor) {
    Player currentFirstPlayer = null;
    Player biggestPlayed = currentFirstPlayer;
    Colors trumpCard = trumpCardColor;
    for (Player player : this.players) {
      if (player.getFirstPlayer()) {
        currentFirstPlayer = player;
        break;
      }
    }

    for (Player player : this.players) {
      if (currentFirstPlayer == player) {
        continue;
      }
      if (player.getLastPlayedCard().getColor() == trumpCard) {
        if (player.getLastPlayedCard().getValue() > biggestPlayed.getLastPlayedCard().getValue()) {
          biggestPlayed = player;
        }
      }
    }
    currentFirstPlayer.toggleFirstPlayer();
    biggestPlayed.toggleFirstPlayer();
    return biggestPlayed;
  }
}
