package com.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RunClient extends Application{
  @Override
  public void start(Stage stage){
    SceneManager manager = new SceneManager(stage);

    stage.setOnCloseRequest((WindowEvent event) -> {
      try {
        manager.getNetworkHandler().sendMessage("disconnect");
      } catch (Exception e) {
      }
      Platform.exit();
    });

    manager.displayScene("login");
  }

  public static void main(String[] args){
    launch();
  }

}
