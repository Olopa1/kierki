package com.example.exceptions;

public class NotEnoughCardsInDeck extends Exception{
  public NotEnoughCardsInDeck(String errorMessage){
    super(errorMessage);
  }
  
}
