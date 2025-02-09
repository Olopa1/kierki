package com.example.client.scenes;

import java.io.IOException;
import java.util.HashMap;

import com.example.client.ClientNetworkHandler;
import com.example.client.SceneManager;
import com.example.client.ScenesHanlder;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ChooseRoomScene implements ScenesHanlder{
  private final String sceneName = "Pokoje";
  private Scene tableScene;
  private HashMap<String, String> roomsFound;
  private TitledPane roomsDisplay;
  private TextField newRoomName;
  private Label error;
  private GridPane gridPane;

  public ChooseRoomScene(SceneManager sceneManager){
    this.initScene(sceneManager);    
  }

  public void initScene(SceneManager sceneManager){
    //try{
    this.roomsDisplay = new TitledPane();
    this.newRoomName = new TextField();
    this.error = new Label("");
    VBox refresh = new VBox();
    VBox add = new VBox();
      //TitledPane rooms = this.setupFoundRooms(sceneManager.getNetworkHandler());    
    HBox refreshAndAdd = new HBox(add,refresh);
    gridPane = new GridPane();
    Accordion roomsContainer = new Accordion();
    Button addNewRoom = new Button("Dodaj pokoj");
    addNewRoom.setOnAction(value->{
      try{
        if(!sceneManager.getNetworkHandler().joinRoom(this.newRoomName.getText())){
          this.error.setText("Cos poszlo nie tak przy dolaczaniu");
        }else{
          sceneManager.displayScene("game");
          this.error.setText("Dodano pokoj");
        }
          
      }catch(ClassNotFoundException e){
        this.error.setText("Nie udalo sie utworzyc pokoju");
      }catch(IOException e){
        this.error.setText("Wystapil blad");
      }
    });
    Button refreshRooms = new Button("Odsiwez pokoje");
    refreshRooms.setOnAction(value->{
      try{
        this.setupFoundRooms(sceneManager.getNetworkHandler(), sceneManager);
        this.gridPane.getChildren().remove(this.roomsDisplay);
        this.gridPane.add(this.roomsDisplay, 1, 2, 1, 1);
      }catch(IOException e){
        this.error.setText("Cos poszlo nie tak");
      }catch(ClassNotFoundException e){
        this.error.setText("Zla odpowiedz od serwera");
      }
    });
    add.getChildren().add(new Label("Dodaj nowy pokoj"));
    add.getChildren().add(this.newRoomName);
    add.getChildren().add(addNewRoom);
    refresh.getChildren().add(new Label("Odswiez pokoje"));
    refresh.getChildren().add(refreshRooms);
      
    gridPane.add(new Label("POKOJE"), 1, 0, 1, 1);
    gridPane.add(refreshAndAdd, 1, 1, 1, 1);
    gridPane.add(roomsContainer, 1, 2, 1, 1);
    gridPane.add(this.error, 1, 3, 1, 1);
    gridPane.setVgap(10);
    gridPane.setHgap(5);
    gridPane.setAlignment(Pos.CENTER);
    this.tableScene = new Scene(gridPane, 500,600);
    /*}catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }*/
  }
    
  public String getSceneName(){
    return this.sceneName;
  }
  
  public Scene getScene(){
    return this.tableScene;
  }


  public void setupFoundRooms(ClientNetworkHandler handler, SceneManager sceneManager)throws ClassNotFoundException, IOException{
    if(handler == null){
      return;
    }
    try{
      this.roomsFound = handler.getRoomData(); 
      VBox rooms = new VBox();
      for(String roomName : this.roomsFound.keySet()){
        Button joinGameButton = new Button("dolacz");
        joinGameButton.setOnAction(value->{
          //Dodaj faktycznie dzialanie
          try{
            if(!handler.joinRoom(roomName)){
              this.error.setText("Cos poszlo nie tak przy dolaczaniu");
            }else{
              sceneManager.displayScene("game");
              this.error.setText("Dolaczono do pokoju");
            }
          }catch(ClassNotFoundException e){
            this.error.setText("Nie udalo sie utworzyc pokoju");
          }catch(IOException e){
            this.error.setText("Wystapil blad");
          }
        });
        if(this.roomsFound.get(roomName).compareTo("4") == 0){
          joinGameButton.setDisable(true);
        }
        HBox hBox = new HBox(new Label(roomName), new Label("|"),new Label("Graczy: " + this.roomsFound.get(roomName) + "/4"), joinGameButton);
        rooms.getChildren().add(hBox);
        System.out.println(roomName + " " + this.roomsFound.get(roomName) + "/4");
      } 
      this.roomsDisplay.setContent(new ScrollPane(rooms));
    }catch(IOException e){
      throw e;
    }catch(ClassNotFoundException e){
      throw e;
    }
  }
} 
