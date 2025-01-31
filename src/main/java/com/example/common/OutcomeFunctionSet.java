package com.example.common;

import javafx.scene.paint.Color;

public class OutcomeFunctionSet {
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

  public static OutcomeFunction firstDeal() {
    return (players, discardedDeck, trick, trumpColor) -> {
      Player biggestCard = findBiggestCard(players);
      biggestCard.changeScore(-20);
      if (discardedDeck.countCards() == 52)
        return true;
      return false;
    };
  }

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

  public static OutcomeFunction seventhDeal() {
    return (players, discardedDeck, trick, trumpColor) -> {
      OutcomeFunctionSet.firstDeal().detrminePoints(players, discardedDeck, trick, trumpColor);
      OutcomeFunctionSet.secondDeal().detrminePoints(players, discardedDeck, trick, trumpColor);
      OutcomeFunctionSet.thirdDeal().detrminePoints(players, discardedDeck, trick, trumpColor);
      OutcomeFunctionSet.fourthDeal().detrminePoints(players, discardedDeck, trick, trumpColor);
      OutcomeFunctionSet.fifthDeal().detrminePoints(players, discardedDeck, trick, trumpColor);
      Boolean last = OutcomeFunctionSet.sixthDeal().detrminePoints(players, discardedDeck, trick, trumpColor);
      return last;
    };
  }

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
