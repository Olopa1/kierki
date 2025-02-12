package com.example.common;


import java.io.Serializable;

/**
 * Set of outcome functions for kierki game.
 */
public class OutcomeFunctionSet implements Serializable {
    /**
     * Finds which player played the biggest card.
     *
     * @param players Array of players.
     * @return Player with the biggest card.
     */
    private static Player findBiggestCard(Player[] players) {
        Player currentFirstPlayer = null;
        Player biggestPlayed = currentFirstPlayer;// zostawało null przez całe wywołanie
        Colors trumpCard;
        for (Player player : players) {
            if (player.getFirstPlayer()) {
                currentFirstPlayer = player;
                break;
            }
        }
        biggestPlayed = currentFirstPlayer;// dlatego tu jeszcze raz przypisuję

        trumpCard = currentFirstPlayer.getLastPlayedCard().getColor();

        for (Player player : players) {
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

    /**
     * Finds which player played the biggest card, given the trump color.
     *
     * @param players        Array of players.
     * @param trumpCardColor Trump color.
     * @return Player with the biggest card.
     */
    private static Player findBiggestCard(Player[] players, Colors trumpCardColor) {
        Player currentFirstPlayer = null;
        Player biggestPlayed = currentFirstPlayer;
        Colors trumpCard = trumpCardColor;
        for (Player player : players) {
            if (player.getFirstPlayer()) {
                currentFirstPlayer = player;
                break;
            }
        }

        for (Player player : players) {
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

    /**
     * First deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction firstDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player biggestCard = findBiggestCard(players);
            biggestCard.changeScore(-20);
            if (discardedDeck.countCards() == 52)
                return true;
            return false;
        };
    }

    /**
     * Second deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction secondDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player bigestCard = findBiggestCard(players);
            int points = 0;
            for (Player player : players) {
                if (player.getLastPlayedCard().getColor() == Colors.HEARTS)
                    points += -20;
            }
            bigestCard.changeScore(points);
            if (discardedDeck.countCards() == 52)
                return true;
            return false;
        };
    }

    /**
     * Third deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction thirdDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player bigestCard = findBiggestCard(players);
            int points = 0;
            for (Player player : players) {
                if (player.getLastPlayedCard().getValue() == 12) {
                    points += -60;
                }
            }
            bigestCard.changeScore(points);
            if (discardedDeck.occurringOfCard(12) == 4)
                return true;
            return false;
        };
    }

    /**
     * Fourth deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction fourthDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player bigestCard = findBiggestCard(players);
            int points = 0;
            for (Player player : players) {
                if (player.getLastPlayedCard().getValue() == 11 || player.getLastPlayedCard().getValue() == 13) {
                    points += -30;
                }
            }
            bigestCard.changeScore(points);
            if (discardedDeck.occurringOfCard(11) == 4 && discardedDeck.occurringOfCard(13) == 4)
                return true;
            return false;
        };
    }

    /**
     * Fifth deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction fifthDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player bigestCard = findBiggestCard(players);
            for (Player player : players) {
                if (player.getLastPlayedCard().getValue() == 13 && player.getLastPlayedCard().getColor() == Colors.HEARTS) {
                    bigestCard.changeScore(-150);
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * Sixth deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction sixthDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player bigestCard = findBiggestCard(players);
            if (trick == 7) {
                bigestCard.changeScore(-75);
            } else if (trick == 13) {
                bigestCard.changeScore(-75);
                return true;
            }
            return false;
        };
    }

    /**
     * Seventh deal of kierki.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction seventhDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            OutcomeFunctionSet.firstDeal().determinePoints(players, discardedDeck, trick, trumpColor);
            OutcomeFunctionSet.secondDeal().determinePoints(players, discardedDeck, trick, trumpColor);
            OutcomeFunctionSet.thirdDeal().determinePoints(players, discardedDeck, trick, trumpColor);
            OutcomeFunctionSet.fourthDeal().determinePoints(players, discardedDeck, trick, trumpColor);
            OutcomeFunctionSet.fifthDeal().determinePoints(players, discardedDeck, trick, trumpColor);
            Boolean last = OutcomeFunctionSet.sixthDeal().determinePoints(players, discardedDeck, trick, trumpColor);
            return last;
        };
    }

    /**
     * Odgrywka.
     *
     * @return Whether it was possible to determine points or not.
     */
    public static OutcomeFunction trumpDeal() {
        return (players, discardedDeck, trick, trumpColor) -> {
            Player bigestCard = findBiggestCard(players, trumpColor);
            bigestCard.changeScore(25);
            if (discardedDeck.countCards() == 52)
                return true;
            return false;
        };
    }

}
