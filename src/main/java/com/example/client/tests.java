package com.example.client;

import com.example.common.Deck;
import com.example.common.Hand;
import com.example.common.Player;
import com.example.exceptions.NotEnoughCardsInDeck;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import com.example.common.Table;
import com.example.common.OutcomeFunctionSet;
import com.example.common.*;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class tests extends Application{
  @Override  
  public void start(Stage stage){
    SceneManager manager = new SceneManager(stage);

    stage.setOnCloseRequest((WindowEvent event) -> {
        try {
            manager.getNetworkHandler().sendMessage("disconnect");
        } catch (Exception e) {
        }
        Platform.exit();
//        System.exit(0);
    });

    manager.displayScene("login");
  }
    
  public static void main(String[] args){
    launch(); 
  }

}
