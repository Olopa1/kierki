package com.example.client;

import com.example.common.Deck;
import com.example.common.Hand;
import com.example.common.Player;
import com.example.exceptions.NotEnoughCardsInDeck;

import javafx.application.Application;
import javafx.stage.Stage;

import com.example.common.Table;
import com.example.common.OutcomeFunctionSet;
import com.example.common.*;

public class tests extends Application{
  @Override  
  public void start(Stage stage){
    SceneManager manager = new SceneManager(stage);
    manager.displayScene("login");
  }
    
  public static void main(String[] args){
    launch(); 
  }

}
