package com.example.client.scenes;

import com.example.client.SceneManager;
import com.example.client.ScenesHanlder;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TableScene implements ScenesHanlder{
  private final String sceneName = "Gra";
  private Scene tableScene;
  private GridPane gridPane;

  public TableScene(SceneManager sceneManager){
    this.initScene(sceneManager);    
  }

  public void initScene(SceneManager sceneManager){
    this.gridPane = new GridPane();
    gridPane.add(new Label("SZCZESC BOZE WNIOSEK FORMALNY"), 1, 1, 1,1);
    this.gridPane.setMinSize(500, 500);
    this.tableScene = new Scene(this.gridPane);
  }
    
  public String getSceneName(){
    return this.sceneName;
  }
  
  public Scene getScene(){
    return this.tableScene;
  }
  
}
