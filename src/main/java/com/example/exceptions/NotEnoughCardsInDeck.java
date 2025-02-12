package com.example.exceptions;

/**
 * Exception that occurs when there is not enough cards in deck.
 */
public class NotEnoughCardsInDeck extends Exception {
    public NotEnoughCardsInDeck(String errorMessage) {
        super(errorMessage);
    }

}
