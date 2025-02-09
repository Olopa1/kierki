package com.example.client.scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.client.ClientNetworkHandler;
import com.example.client.SceneManager;
import com.example.client.ScenesHanlder;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TableScene implements ScenesHanlder{
  private final String sceneName = "Pokoje";
  private Scene tableScene;
  private HashMap<String, String> roomsFound;
  private TitledPane roomsDisplay;
  private TextField newRoomName;
  private Label error;

  public TableScene(SceneManager sceneManager){
    this.initScene(sceneManager);    
  }

  public void initScene(SceneManager sceneManager){
    //try{
      this.roomsDisplay = new TitledPane();
      this.newRoomName = new TextField();
      this.error = new Label("");
      VBox refresh = new VBox();
      VBox add = new VBox();
      HBox refreshAndAdd = new HBox(add,refresh);
      GridPane gridPane = new GridPane();
      Accordion roomsContainer = new Accordion();
      //roomsContainer.getPanes().add(this.setupFoundRooms(sceneManager.getNetworkHandler()));
      Button addNewRoom = new Button("Dodaj pokoj");
      addNewRoom.setOnAction(value->{
        this.error.setText("Dodano pokoj");
      });
      Button refreshRooms = new Button("Odsiwez pokoje");
      refreshRooms.setOnAction(value->{
        try{
          this.setupFoundRooms(sceneManager.getNetworkHandler());
        }catch(IOException e){
          this.error.setText("Cos poszlo nie tak");
        }catch(ClassNotFoundException e){
          this.error.setText("Zla odpowiedz od serwera");
        }
      });
      add.getChildren().add(new Label("Dodaj nowy pokoj"));
      add.getChildren().add(addNewRoom);
      refresh.getChildren().add(new Label("Odswiez pokoje"));
      refresh.getChildren().add(refreshRooms);
      gridPane.add(new Label("POKOJE"), 1, 0, 1, 1);
      gridPane.add(refreshAndAdd, 1, 1, 1, 1);
      gridPane.add(roomsContainer, 1, 2, 1, 1);
      gridPane.setVgap(10);
      gridPane.setAlignment(Pos.CENTER);
      this.tableScene = new Scene(gridPane, 500,600);
    /*}catch(ClassNotFoundException e){
      this.error.setText("Zla odpowiedz od serwera");
    }catch(IOException e){
      this.error.setText("Cos poszlo nie tak");
    }*/

  }
    
  public String getSceneName(){
    return this.sceneName;
  }
  
  public Scene getScene(){
    return this.tableScene;
  }

  private TitledPane setupFoundRooms(ClientNetworkHandler handler)throws ClassNotFoundException, IOException{
    try{
      this.roomsFound = handler.getRoomData(); 
      VBox rooms = new VBox();
      for(String roomName : this.roomsFound.keySet()){
        Button joinGameButton = new Button("dolacz");
        joinGameButton.setOnAction(value->{
          //Dodaj faktycznie dzialanie
          System.out.println("Dolaczono do " + roomName);
        });
        if(this.roomsFound.get(roomName).compareTo("4") == 0){
          joinGameButton.setDisable(true);
        }
        HBox hBox = new HBox(new Label(roomName), new Label("|"),new Label("Graczy: " + this.roomsFound.get(roomName) + "/4"), joinGameButton);
        rooms.getChildren().add(hBox);
      } 
      this.roomsDisplay.setContent(new ScrollPane(rooms));
      return roomsDisplay;
    }catch(IOException e){
      throw e;
    }catch(ClassNotFoundException e){
      throw e;
    }
  }
  
}
