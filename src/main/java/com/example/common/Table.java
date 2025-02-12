package com.example.common;

import com.example.exceptions.NotEnoughCardsInDeck;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Table of kierki game.
 */
public class Table implements Serializable {
    private Player players[];
    private Deck startingDeck;
    private Deck discardedDeck;
    private int trick;//lewa
    private Colors trumpColor;
    private int currentPlayer;
    private OutcomeFunction function;

    /**
     * Creates new table.
     *
     * @param player1
     * @param player2
     * @param player3
     * @param player4
     */
    public Table(Player player1, Player player2, Player player3, Player player4) {
        this.players = new Player[]{player1, player2, player3, player4};
        this.startingDeck = new Deck(false);
        this.discardedDeck = new Deck(true);
        this.trumpColor = null;
        this.players[0].toggleFirstPlayer();
        this.trick = 1;
        this.currentPlayer = 0;
    }

    /**
     * Performs next move in the game.
     *
     * @param player    Player who performs a move.
     * @param cardIndex Card that he plays.
     * @return Number of next player.
     */
    public int nextMove(Player player, int cardIndex) {
        if (player != this.players[this.currentPlayer]) return this.currentPlayer;
        System.out.println(this.currentPlayer);

        // tu trzeba sprawdzić czy ruch jest legalny
        if (!isMoveLegal(player, cardIndex)) return this.currentPlayer;

        player.playCard(cardIndex);
        discardedDeck.putOnDeck(player.getLastPlayedCard());
        this.currentPlayer = (this.currentPlayer + 1) % 4;

        for (Player player1 : this.players) {
            if (player1.getNumberOfCards() != player.getNumberOfCards())
                return this.currentPlayer;
        }

        if (!this.determinePoints(function)) {
            this.currentPlayer = this.getFirstPlayer();
            return this.currentPlayer;
        }

        discardedDeck.restoreDeck();
        startingDeck.restoreDeck();
        for (Player player1 : this.players) {
            player1.clearHand();
        }

        return -1;
    }

    /**
     * Determines whether a move is legal or not.
     *
     * @param player    Player, which takes action.
     * @param cardIndex Index of a card, he plays.
     * @return Whether a move is legal or not.
     */
    private boolean isMoveLegal(Player player, int cardIndex) {
        if (this.getCurrentPlayer() == this.getFirstPlayer()) {
            ArrayList<Card> cards = player.getHand().getHand();
            if (function == OutcomeFunctionSet.secondDeal() || function == OutcomeFunctionSet.fifthDeal() || function == OutcomeFunctionSet.seventhDeal()) {
                for (Card card : cards) {
                    if (card.getColor() != Colors.HEARTS)
                        return false;
                }
            }
            return true;
        }

        Card firstCard = this.players[this.getFirstPlayer()].getLastPlayedCard();
        Colors color = firstCard.getColor();
        ArrayList<Card> cards = player.getHand().getHand();

        if (color == player.getCard(cardIndex).getColor())
            return true;
        for (Card card : cards) {
            if (card.getColor() == color)
                return false;
        }

        return true;
    }

    /**
     * Prepares table for the next hand.
     *
     * @param function Outcome function that wil evaluate moves in this deal.
     * @return Index of yhe first player
     * @throws NotEnoughCardsInDeck
     */
    public int nextHand(OutcomeFunction function) throws NotEnoughCardsInDeck { // rozdanie
        startingDeck.shuffle();
        this.dealCards();
        this.function = function;

        this.currentPlayer = this.getFirstPlayer();
        return this.currentPlayer;
    }

    /**
     * Calculates points granted to players after the trick.
     *
     * @param function Outcome function that wil evaluate move.
     * @return Whether a deal has ended or not.
     */
    public boolean determinePoints(OutcomeFunction function) {
        if (function.determinePoints(this.players, this.discardedDeck, this.trick, this.trumpColor)) {
            startingDeck.restoreDeck();
            discardedDeck.restoreDeck();
            this.clearPlayerHands();
            this.trick = 1;
            return true;
        }
        this.trick++;
        return false;
    }

    /**
     * Clears players hands.
     */
    private void clearPlayerHands() {
        for (Player player : players) {
            player.clearHand();
        }
    }

    /**
     * Deals cards from deck to the players.
     *
     * @throws NotEnoughCardsInDeck
     */
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

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getFirstPlayer() {
        for (int i = 0; i < 4; i++) {
            if (this.players[i].getFirstPlayer()) return i;
        }
        return -1;
    }

    public HashMap<String, Integer> getPoints() {
        HashMap<String, Integer> players = new HashMap<>();
        for (Player player : this.players) {
            players.put(player.getPlayerName(), player.getScore());
        }
        return players;
    }

    public void printPoints() {
        for (Player player : this.players) {
            System.out.println("Player: " + player.getPlayerName() + "have: " + player.getScore() + " points.");
        }
    }

    public Player getPlayer(String name) {
        for (Player player : this.players) {
            if (player.getPlayerName().equals(name)) return player;
        }
        return null;
    }

    public Deck getDiscardedDeck() {
        return this.discardedDeck;
    }

    public void playGame(OutcomeFunction function) throws NotEnoughCardsInDeck {
        startingDeck.shuffle();
        this.dealCards();

        for (Player player : this.players) {
            player.playCard(0);
        }
        for (Player player : this.players) {
            discardedDeck.putOnDeck(player.getLastPlayedCard());
        }
        while (!this.determinePoints(function)) {
            for (Player player : this.players) {
                player.playCard(0);
            }

            for (Player player : this.players) {
                discardedDeck.putOnDeck(player.getLastPlayedCard());
            }
            this.printPoints();
        }
        this.printPoints();
        discardedDeck.restoreDeck();
        startingDeck.restoreDeck();
        for (Player player : this.players) {
            player.clearHand();
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
