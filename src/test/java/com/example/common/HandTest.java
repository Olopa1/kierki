package com.example.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    private Hand hand;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void setUp() {
        hand = new Hand();
        card1 = new Card(10, Colors.HEARTS);  // ♥ 10
        card2 = new Card(5, Colors.SPADES);   // ♠ 5
        card3 = new Card(12, Colors.CLUBS);   // ♣ Q
    }

    @AfterEach
    void tearDown() {
        hand = null;
    }

    @org.junit.jupiter.api.Test
    void isHandEmpty() {
        assertTrue(hand.isHandEmpty());
        hand.takeCard(card1);
        assertFalse(hand.isHandEmpty());
    }

    @org.junit.jupiter.api.Test
    void takeCard() {
        hand.takeCard(card1);
        assertEquals(1, hand.getNumberOfCards());
        assertEquals(card1, hand.getCard(0));
    }

    @org.junit.jupiter.api.Test
    void playCard() {
        hand.takeCard(card1);
        hand.takeCard(card2);
        Card removedCard = hand.playCard(0);
        assertEquals(card1, removedCard);
        assertEquals(1, hand.getNumberOfCards());
    }

    @org.junit.jupiter.api.Test
    void sortCardsInHand() {
        hand.takeCard(card1);
        hand.takeCard(card2);
        hand.takeCard(card3);
        hand.sortCardsInHand();
        assertEquals(card1, hand.getCard(0)); // ♥ 10
        assertEquals(card3, hand.getCard(1)); // ♣ Q
        assertEquals(card2, hand.getCard(2)); // ♠ 5
    }

    @org.junit.jupiter.api.Test
    void getHand() {
        hand.takeCard(card1);
        hand.takeCard(card2);
        assertEquals(2, hand.getHand().size());
        assertTrue(hand.getHand().contains(card1));
        assertTrue(hand.getHand().contains(card2));
    }

    @org.junit.jupiter.api.Test
    void emptyHand() {
        hand.takeCard(card1);
        hand.takeCard(card2);
        hand.emptyHand();
        assertTrue(hand.isHandEmpty());
    }

    @org.junit.jupiter.api.Test
    void getNumberOfCards() {
        assertEquals(0, hand.getNumberOfCards());
        hand.takeCard(card1);
        assertEquals(1, hand.getNumberOfCards());
        hand.takeCard(card2);
        assertEquals(2, hand.getNumberOfCards());
    }

    @org.junit.jupiter.api.Test
    void getCard() {
        hand.takeCard(card1);
        assertEquals(card1, hand.getCard(0));
    }
}