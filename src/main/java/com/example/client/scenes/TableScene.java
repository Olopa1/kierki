package com.example.client.scenes;

import com.example.client.GameController;
import com.example.client.SceneManager;
import com.example.client.ScenesHanlder;

import com.example.common.Table;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TableScene implements ScenesHanlder{
  private final String sceneName = "Gra";
  private Scene tableScene;
  private GridPane gridPane;
  private GameController controller;
  private Table table;

  public TableScene(SceneManager sceneManager){
    this.initScene(sceneManager);    
  }

  public void initScene(SceneManager sceneManager){
//    this.gridPane = new GridPane();
//    gridPane.add(new Label("SZCZESC BOZE WNIOSEK FORMALNY"), 1, 1, 1,1);
//    this.gridPane.setMinSize(500, 500);
//    this.tableScene = new Scene(this.gridPane);
    if(sceneManager.getNetworkHandler()==null)
      System.out.println("Handler is null!!!!!!!!!");
    else
      System.out.println("Handler is not null!!!!!!!!!");

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../game-view.fxml"));
      Parent root = loader.load();

      controller = loader.getController();
      controller.setTable(table);
      controller.setNetworkHandler(sceneManager.getNetworkHandler());
      controller.setManager(sceneManager);

      this.tableScene = new Scene(root);

    } catch (IOException e) {
        throw new RuntimeException(e);
    }

  }
    
  public String getSceneName(){
    return this.sceneName;
  }
  
  public Scene getScene(){
    return this.tableScene;
  }
  
}
