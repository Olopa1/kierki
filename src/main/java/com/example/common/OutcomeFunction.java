package com.example.common;

import java.io.Serializable;

public interface OutcomeFunction extends Serializable {
  public Boolean detrminePoints(Player[] players,Deck discardedCards,int trick, Colors trumpColor);
}
