package com.example.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Manages player's hand of cards.
 */
public class Hand implements Serializable {
    private ArrayList<Card> cards;

    /**
     * Creates empty hand.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Sorts cards in hand.
     */
    public void sortCardsInHand() {
        cards.sort(Comparator.comparing(Card::getColor).thenComparing(Card::getValue));
    }

    public Boolean isHandEmpty() {
        return this.cards.isEmpty();
    }

    /**
     * Inserts a card into hand.
     *
     * @param card Card to be added.
     */
    public void takeCard(Card card) {
        this.cards.add(card);
        this.sortCardsInHand();
    }

    /**
     * Removes card from hand.
     *
     * @param cardNumber Index of card to be removed.
     * @return Card from a given position.
     */
    public Card playCard(int cardNumber) {
        return this.cards.remove(cardNumber);
    }

    /**
     * Displays a hand.
     */
    public void displayHand() {
        for (Card card : this.cards) {
            card.displayCard();
        }
    }

    public ArrayList<Card> getHand() {
        return this.cards;
    }

    public void emptyHand() {
        this.cards.clear();
    }

    public int getNumberOfCards() {
        return this.cards.size();
    }

    public Card getCard(int index) {
        return this.cards.get(index);
    }
}
