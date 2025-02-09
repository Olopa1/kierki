package com.example.client.scenes;

import com.example.client.SceneManager;
import com.example.client.ScenesHanlder;

import javafx.scene.Scene;

public class ChooseRoomScene implements ScenesHanlder{
  String sceneName;
  Scene chooseRoomScene;

  public ChooseRoomScene(SceneManager sceneManager){
    
  }

  public void initScene(SceneManager sceneManager){}
  
  public String getSceneName(){
    return this.sceneName;
  }
  
  public Scene getScene(){
    return this.chooseRoomScene;
  }
   
  
}
