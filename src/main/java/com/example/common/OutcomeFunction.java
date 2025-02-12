package com.example.common;

import java.io.Serializable;

/**
 * Interface of function that will count points in card game.
 */
public interface OutcomeFunction extends Serializable {
    /**
     * Method that will determine how many points will be given to players.
     *
     * @param players        Array of players participationg in game.
     * @param discardedCards Cards that were played in this trick.
     * @param trick          Number of trick in the round.
     * @param trumpColor     Color that is trump in this trick.
     * @return Whether managed to determine points or not.
     */
    public Boolean determinePoints(Player[] players, Deck discardedCards, int trick, Colors trumpColor);
}
