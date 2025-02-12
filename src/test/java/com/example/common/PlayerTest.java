package com.example.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private Card card1;
    private Card card2;
    private Hand hand;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
        card1 = new Card(10, Colors.HEARTS);  // ♥ 10
        card2 = new Card(5, Colors.SPADES);   // ♠ 5
        hand = new Hand();
        hand.takeCard(card1);
        hand.takeCard(card2);
    }

    @AfterEach
    void tearDown() {
        player = null;
        hand = null;
    }

    @Test
    void setHand() {
        player.setHand(hand);
        assertEquals(hand, player.getHand());
    }

    @Test
    void toggleFirstPlayer() {
        assertFalse(player.getFirstPlayer());
        player.toggleFirstPlayer();
        assertTrue(player.getFirstPlayer());
        player.toggleFirstPlayer();
        assertFalse(player.getFirstPlayer());
    }

    @Test
    void changeScore() {
        assertEquals(0, player.getScore());
        player.changeScore(10);
        assertEquals(10, player.getScore());
        player.changeScore(-5);
        assertEquals(5, player.getScore());
    }

    @Test
    void getScore() {
        assertEquals(0, player.getScore());
        player.changeScore(3);
        assertEquals(3, player.getScore());
    }

    @Test
    void getFirstPlayer() {
        assertFalse(player.getFirstPlayer());
        player.toggleFirstPlayer();
        assertTrue(player.getFirstPlayer());
    }

    @Test
    void getLastPlayedCard() {
        assertNull(player.getLastPlayedCard());
        player.setHand(hand);
        player.playCard(0);
        assertEquals(card1, player.getLastPlayedCard());
    }

    @Test
    void playCard() {
        player.setHand(hand);
        Card playedCard = player.playCard(0);
        assertEquals(card1, playedCard);
        assertEquals(1, player.getNumberOfCards());
    }

    @Test
    void addCardToHand() {
        assertEquals(0, player.getNumberOfCards());
        player.addCardToHand(card1);
        assertEquals(1, player.getNumberOfCards());
        assertEquals(card1, player.getCard(0));
    }

    @Test
    void getPlayerName() {
        assertEquals("TestPlayer", player.getPlayerName());
    }

    @Test
    void clearHand() {
        player.setHand(hand);
        assertEquals(2, player.getNumberOfCards());
        player.clearHand();
        assertEquals(0, player.getNumberOfCards());
        assertTrue(player.getHand().getHand().isEmpty());
    }

    @Test
    void getHand() {
        player.setHand(hand);
        assertEquals(hand, player.getHand());
    }

    @Test
    void getCard() {
        player.setHand(hand);
        assertEquals(card1, player.getCard(0));
        assertEquals(card2, player.getCard(1));
    }

    @Test
    void getNumberOfCards() {
        assertEquals(0, player.getNumberOfCards());
        player.setHand(hand);
        assertEquals(2, player.getNumberOfCards());
    }
}