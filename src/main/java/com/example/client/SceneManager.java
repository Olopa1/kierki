package com.example.client;

import java.net.Socket;
import java.util.HashMap;

import com.example.client.scenes.LoginScene;

import javafx.scene.*;
import javafx.stage.Stage;

public class SceneManager {
  private HashMap<String,ScenesHanlder> sceneManger;
  private Stage stage;
  private ClientNetworkHandler handler = null;

  public SceneManager(Stage stage){
    sceneManger = new HashMap<String,ScenesHanlder>();
    this.stage = stage;
    this.initScenes();
  }


  
  private void initScenes(){
    this.sceneManger.put("login", new LoginScene(this));
  }

  public void displayScene(String sceneName){
    this.stage.setScene(sceneManger.get(sceneName).getScene());
    this.stage.setTitle(sceneManger.get(sceneName).getSceneName());
    this.stage.show();
  }
}
