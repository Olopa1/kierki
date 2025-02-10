package com.example.client.scenes;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.concurrent.Flow;

import com.example.client.ClientNetworkHandler;
import com.example.client.SceneManager;
import com.example.client.ScenesHanlder;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class LoginScene implements ScenesHanlder{
  
  private final String sceneName = "Logowanie";
  private Scene scene;
  private TextField loginField;
  private PasswordField passwordField;
  private TextField ipAddress;
  private TextField port;
  private Label error;
  public LoginScene(SceneManager sceneManager){
    this.initScene(sceneManager);
  }

  public void initScene(SceneManager sceneManager){
    GridPane gridPane = new GridPane();
    Button loginButton = new Button("Zaloguj");
    this.error = new Label("");
    loginButton.setOnAction(value->{
      try{
        if(sceneManager.getNetworkHandler() == null){
          sceneManager.setNetworkHandler(new ClientNetworkHandler(this.ipAddress.getText(), Integer.parseInt(this.port.getText()))); 
          sceneManager.getNetworkHandler().loginUser(this.loginField.getText(), passwordField.getText());
          sceneManager.login = this.loginField.getText();
          sceneManager.displayScene("show_tables");
        }
        this.error.setText("Polaczono");
        sceneManager.displayScene("show_tables");
      }catch(NumberFormatException e){
        this.error.setText("Port nie jest liczba");
      }catch(IOException e){
        this.error.setText("Nie mozna polaczyc z serwerem");
      }catch(ClassNotFoundException e){
        this.error.setText("Zla odpowiedz od serwera");
      }
    });
    this.loginField = new TextField();
    this.ipAddress = new TextField();
    this.port = new TextField();
    this.passwordField = new PasswordField();
    this.ipAddress.setText("127.0.0.1");
    this.port.setText("12345");
    Accordion ipAccordion = new Accordion();
    VBox networkBoxOptions = new VBox(new Label("Adres IP:"), this.ipAddress, new Label("Port:"), this.port);
    TitledPane networkOptions = new TitledPane("Wiecej opcji", networkBoxOptions);
    ipAccordion.getPanes().add(networkOptions);
    gridPane.add(new Label("Zaloguj się!"), 1, 0, 1, 1); 
    gridPane.add(new Label("Login:"), 1, 1, 1, 1); 
    gridPane.add(this.loginField, 1, 2, 1, 1); 
    gridPane.add(new Label("Haslo:"), 1, 3, 1, 1); 
    gridPane.add(this.passwordField, 1, 4, 1, 1); 
    gridPane.add(loginButton, 1, 5, 1, 1); 
    gridPane.add(this.error, 1, 6, 1, 1); 
    gridPane.add(ipAccordion, 1, 7, 1, 1); 
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);
    this.scene = new Scene(gridPane,250,400); 
  } 

  public String getSceneName(){
    return this.sceneName;
  }

  public Scene getScene(){
    return this.scene;
  }
}
