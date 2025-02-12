package com.example.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.example.exceptions.NotEnoughCardsInDeck;

/**
 * Manages a single deck of cards. Enables custom decks.
 */
public class Deck implements Serializable {
    private ArrayList<Card> cards;
    private static final int[] cardValues = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    private static final Colors[] cardColors = {Colors.HEARTS, Colors.DIAMONDS, Colors.CLUBS, Colors.SPADES};
    private final Boolean isCustom;
    private final Boolean isEmpty;
    private int[] customValues = {};
    private Colors[] customColors = {};

    /**
     * Creates standard deck.
     *
     * @param emptyDeck Whether a deck should be empty or not.
     */
    public Deck(Boolean emptyDeck) {
        this.isCustom = false;
        this.isEmpty = emptyDeck;
        this.cards = new ArrayList<>();
        this.restoreDeck();
    }

    /**
     * Enables custom version of deck.
     *
     * @param values Values that will be held by cards.
     * @param colors Colors that will be held by cards.
     */
    public Deck(int[] values, Colors[] colors) {
        this.isCustom = true;
        this.isEmpty = false;
        this.cards = new ArrayList<>();
        this.customValues = values;
        this.customColors = colors;
        this.restoreDeck();
    }

    /**
     * Checks if card with given value is in deck.
     *
     * @param value Cards number.
     * @return Number of cards with that value.
     */
    public int occurringOfCard(int value) {
        int found = 0;
        for (Card card : this.cards) {
            if (card.getValue() == value) found++;
        }
        return found;
    }

    /**
     * Restores deck to initial configuration.
     */
    public void restoreDeck() {
        this.cards.clear();
        if (this.isEmpty) return;
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

    /**
     * Shuffles cards in deck.
     */
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

    /**
     * Gives a card from pile.
     *
     * @return Card.
     * @throws NotEnoughCardsInDeck
     */
    public Card deal() throws NotEnoughCardsInDeck {
        if (cards.size() <= 0) {
            throw new NotEnoughCardsInDeck("There is not enough cards in deck!");
        }
        return this.cards.remove(cards.size() - 1);
    }

    /**
     * Puts card in deck.
     *
     * @param card Card that is added.
     */
    public void putOnDeck(Card card) {
        this.cards.add(card);
    }

    /**
     * Counts how many cards is currently in deck.
     *
     * @return Number of cards.
     */
    public int countCards() {
        return this.cards.size();
    }

}
