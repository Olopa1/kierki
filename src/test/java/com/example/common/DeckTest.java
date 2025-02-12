package com.example.common;

import com.example.exceptions.NotEnoughCardsInDeck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp(){
        deck = new Deck(false); // Standardowa talia
    }

    @AfterEach
    void tearDown(){
        deck = null;
    }

    @org.junit.jupiter.api.Test
    void occurringOfCard() {
        int count = deck.occurringOfCard(2); // Sprawdzamy, ile dwójek jest w talii
        assertEquals(4, count, "W standardowej talii powinny być 4 karty o wartości 2.");
    }

    @org.junit.jupiter.api.Test
    void restoreDeck() throws NotEnoughCardsInDeck {
        deck.deal(); // Usuwamy jedną kartę
        deck.restoreDeck(); // Przywracamy talię
        assertEquals(52, deck.countCards(), "Po przywróceniu talii powinno być 52 karty.");
    }

    @org.junit.jupiter.api.Test
    void shuffle() {
        Deck originalDeck = new Deck(false);
        deck.shuffle();
        assertNotEquals(originalDeck, deck, "Po przetasowaniu talia powinna zmienić kolejność kart.");
    }

    @org.junit.jupiter.api.Test
    void deal() throws NotEnoughCardsInDeck {
        int initialSize = deck.countCards();
        Card card = deck.deal();
        assertNotNull(card, "Pobrana karta nie powinna być null.");
        assertEquals(initialSize - 1, deck.countCards(), "Po dobraniu karty, talia powinna mieć jedną kartę mniej.");
    }

    @org.junit.jupiter.api.Test
    void putOnDeck() {
        Card card = new Card(10, Colors.HEARTS);
        int initialSize = deck.countCards();
        deck.putOnDeck(card);
        assertEquals(initialSize + 1, deck.countCards(), "Po dodaniu karty talia powinna mieć jedną kartę więcej.");
    }

    @org.junit.jupiter.api.Test
    void countCards() {
        assertEquals(52, deck.countCards(), "Nowa standardowa talia powinna mieć 52 karty.");
    }

    @org.junit.jupiter.api.Test
    void dealThrowsExceptionWhenEmpty() {
        Deck emptyDeck = new Deck(true);
        assertThrows(NotEnoughCardsInDeck.class, emptyDeck::deal, "Powinna zostać rzucona NotEnoughCardsInDeck, gdy talia jest pusta.");
    }
}