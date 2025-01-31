package com.example.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.example.exceptions.NotEnoughCardsInDeck;

public class Deck implements Serializable{
  private ArrayList<Card> cards;
  private static final int[] cardValues = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
  private static final Colors[] cardColors = { Colors.HEARTS, Colors.DIAMONDS, Colors.CLUBS, Colors.SPADES };
  private final Boolean isCustom;
  private final Boolean isEmpty;
  private int[] customValues = {};
  private Colors[] customColors = {};

  public Deck(Boolean emptyDeck) {
    this.isCustom = false;
    this.isEmpty = emptyDeck;
    this.cards = new ArrayList<>();
    this.restoreDeck();
  }

  public Deck(int[] values, Colors[] colors) {
    this.isCustom = true;
    this.isEmpty = false;
    this.cards = new ArrayList<>();
    this.customValues = values;
    this.customColors = colors;
    this.restoreDeck();
  }

  public int occurringOfCard(int value){
    int found = 0;
    for (Card card : this.cards) {
      if(card.getValue() == value) found++;
    }
    return found;
  }

  public void restoreDeck() {
    this.cards.clear();
    if(this.isEmpty) return;
    if (this.isCustom == true) {
      for (Colors colorName : this.customColors) {
        for (int value : this.customValues) {
          this.cards.add(new Card(value, colorName));
        }
      }
    } else {
      for (Colors colorName : cardColors) {
        for (int value : cardValues) {
          this.cards.add(new Card(value, colorName));
        }
      }
    }
  }

  public void shuffle() {
    ArrayList<Card> tempCardsHolder = new ArrayList<>();
    Random pickCard = new Random();
    int currentCard = 0;
    while (!this.cards.isEmpty()) {
      currentCard = pickCard.nextInt(this.cards.size());
      tempCardsHolder.add(this.cards.get(currentCard));
      this.cards.remove(currentCard);
    }
    this.cards = tempCardsHolder;
  }

  public Card deal() throws NotEnoughCardsInDeck {
    if (cards.size() <= 0) {
      throw new NotEnoughCardsInDeck("There is not enough cards in deck!");
    }
    return this.cards.remove(cards.size() - 1);
  }
  
  public void putOnDeck(Card card){
    this.cards.add(card);
  }

  public int countCards(){
    return this.cards.size();
  }

}
