package com.example.client;

import com.example.client.ClientNetworkHandler;
import com.example.client.SceneManager;
import com.example.common.Card;
import com.example.common.Colors;
import com.example.common.Table;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    @FXML
    HBox cardsArea;
    @FXML
    TextField messageArea;
    @FXML
    TextArea chatArea;

    @FXML
    Label player1;
    @FXML
    Label points1;
    @FXML
    ImageView card1;
    @FXML
    Polygon triangle1;

    @FXML
    Label player2;
    @FXML
    Label points2;
    @FXML
    ImageView card2;
    @FXML
    Polygon triangle2;

    @FXML
    Label player3;
    @FXML
    Label points3;
    @FXML
    ImageView card3;
    @FXML
    Polygon triangle3;

    @FXML
    Label player4;
    @FXML
    Label points4;
    @FXML
    ImageView card4;
    @FXML
    Polygon triangle4;

    @FXML
    Label winLabel;
    @FXML
    Label winPlayer;
    @FXML
    Label winPoints;

    Table table;
    ClientNetworkHandler networkHandler;
    SceneManager manager;

    public void hideElements(){
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        player4.setVisible(false);
        points1.setVisible(false);
        points2.setVisible(false);
        points3.setVisible(false);
        points4.setVisible(false);
        triangle1.setVisible(false);
        triangle2.setVisible(false);
        triangle3.setVisible(false);
        triangle4.setVisible(false);
        winPlayer.setVisible(false);
        winPoints.setVisible(false);
        winLabel.setVisible(false);
    }

    public void setManager(SceneManager manager){
        this.manager = manager;
    }

    public void setNetworkHandler(ClientNetworkHandler networkHandler){
        this.networkHandler = networkHandler;
    }

    public void setTable(Table table){
        this.table = table;
    }

    public void sendMessage(ActionEvent event){
        String message = messageArea.getText();
        messageArea.clear();

        chatArea.setText(chatArea.getText()+"\nty: "+message);

        try {
            this.manager.getNetworkHandler().sendChatMessage(message);
//            this.networkHandler.sendChatMessage(message);
        } catch (IOException e) {
            System.out.println("Nie udało się wysłać wiadomosci");
        }
    }

    public void receiveMessage(String message){

        chatArea.setText(chatArea.getText()+"\n"+message);

    }

    public void endGame(){
        Platform.runLater(() -> {
            triangle1.setVisible(false);
            triangle2.setVisible(false);
            triangle3.setVisible(false);
            triangle4.setVisible(false);
            cardsArea.setVisible(false);
            card1.setVisible(false);
            card2.setVisible(false);
            card3.setVisible(false);
            card4.setVisible(false);

            HashMap<String, Integer> players = table.getPoints();
            int i=0;
            int maxI=0;
            int max = (int) players.values().toArray()[0];
            for(int value : players.values()){
                if(value>max){
                    max = value;
                    maxI = i;
                }
                i++;
            }
            winPoints.setText("Punkty: "+max);
            winPlayer.setText((String) players.keySet().toArray()[maxI]);

            winLabel.setVisible(true);
            winPlayer.setVisible(true);
            winPoints.setVisible(true);
        });
    }

    public void updateTable(){
        Platform.runLater(() -> {
            winLabel.setVisible(false);
            winPlayer.setVisible(false);
            winPoints.setVisible(false);

            cardsArea.getChildren().clear();
            int numberOfCards = table.getPlayer(manager.login).getNumberOfCards();
            ArrayList<Card> cards = table.getPlayer(manager.login).getHand().getHand();

            for (Card card : cards) {
                ImageView view = new ImageView();
                Image cardImg = getCardImage(card);
                view.setImage(cardImg);
                view.setFitWidth(50);
                view.setPreserveRatio(true);
                view.setSmooth(true);

                view.setOnMouseClicked(event ->{
                    Integer index = indexOfCard(card);
                    try {
                        this.manager.getNetworkHandler().sendCard(index);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                cardsArea.getChildren().add(view);
            }

            HashMap<String, Integer> players = table.getPoints();
            int nr = 0;
            for(String name : players.keySet()){
                if(name.equals(this.manager.login))
                    break;
                nr++;
            }
            int currentPlayer = table.getCurrentPlayer();

            player1.setText(this.manager.login);
            points1.setText("Punkty: "+players.get(this.manager.login));
            triangle1.setVisible(currentPlayer == nr);
            if(isVisible(nr)) {
                Card last = table.getPlayer(this.manager.login).getLastPlayedCard();
                Image cardImg = getCardImage(last);
                card1.setImage(cardImg);
                card1.setFitWidth(50);
                card1.setPreserveRatio(true);
                card1.setSmooth(true);
                card1.setVisible(true);
            }
            else card1.setVisible(false);

            nr = (nr+1)%4;
            String name = (String) players.keySet().toArray()[nr];
            player2.setText(name);
            points2.setText("Punkty: "+players.get(name));
            triangle2.setVisible(currentPlayer == nr);
            if(isVisible(nr)) {
                Card last = table.getPlayer(name).getLastPlayedCard();
                Image cardImg = getCardImage(last);
                card2.setImage(cardImg);
                card2.setFitWidth(50);
                card2.setPreserveRatio(true);
                card2.setSmooth(true);
                card2.setVisible(true);
            }
            else card2.setVisible(false);

            nr = (nr+1)%4;
            name = (String) players.keySet().toArray()[nr];
            player3.setText(name);
            points3.setText("Punkty: "+players.get(name));
            triangle3.setVisible(currentPlayer == nr);
            if(isVisible(nr)) {
                Card last = table.getPlayer(name).getLastPlayedCard();
                Image cardImg = getCardImage(last);
                card3.setImage(cardImg);
                card3.setFitWidth(50);
                card3.setPreserveRatio(true);
                card3.setSmooth(true);
                card3.setVisible(true);
            }
            else card3.setVisible(false);

            nr = (nr+1)%4;
            name = (String) players.keySet().toArray()[nr];
            player4.setText(name);
            points4.setText("Punkty: "+players.get(name));
            triangle4.setVisible(currentPlayer == nr);
            if(isVisible(nr)) {
                Card last = table.getPlayer(name).getLastPlayedCard();
                Image cardImg = getCardImage(last);
                card4.setImage(cardImg);
                card4.setFitWidth(50);
                card4.setPreserveRatio(true);
                card4.setSmooth(true);
                card4.setVisible(true);
            }
            else card4.setVisible(false);

        });
    }

    private Integer indexOfCard(Card card){
        Integer index = 0;
        for(Card curCard : this.table.getPlayer(this.manager.login).getHand().getHand()){
            if(curCard==card) break;
            index++;
        }
        return index;
    }

    private boolean isVisible(int nr){
        if(table.getCurrentPlayer()==table.getFirstPlayer())
            return false;

        if(table.getFirstPlayer()<=table.getCurrentPlayer())
            return nr >= table.getFirstPlayer() && nr < table.getCurrentPlayer();
        else
            return nr >= table.getFirstPlayer() || nr < table.getCurrentPlayer();
    }

    private Image getCardImage(Card card){
        int value = card.getValue();
        String valueName = null;
        if(value==11) valueName = "jack";
        if(value==12) valueName = "queen";
        if(value==13) valueName = "king";
        if(value==14) valueName = "ace";

        Colors color = card.getColor();
        String colorName = "";
        if(color==Colors.DIAMONDS) colorName = "diamonds";
        if(color==Colors.HEARTS) colorName = "hearts";
        if(color==Colors.SPADES) colorName = "spades";
        if(color==Colors.CLUBS) colorName = "clubs";

        String cardName;
        if(valueName == null)
            cardName = value+"_of_"+colorName+".png";
        else
            cardName = valueName+"_of_"+colorName+".png";

        Image image = new Image(getClass().getResourceAsStream("PNG-cards\\"+cardName));

        return image;
    }

    public void initChat(){

        chatArea.setText("");

        new Thread(() -> {
            try {
                Object serverMessage;
                while ((serverMessage = manager.getNetworkHandler().receiveChatMessage()) != null) {
                    if(serverMessage instanceof Table table) {
                        this.table = table;
                        this.updateTable();
                    }
                    else if(serverMessage instanceof String message){
                        if(message.compareTo("Start") == 0) {
                            System.out.println("Gra rozpoczęta");
                        }else if(message.compareTo("ty") == 0) {
                            System.out.println("Twoja kolej");
                        }else if(message.compareTo("koniec") == 0) {
                            System.out.println("koniec gry");
                            this.endGame();
                        }else if(message.compareTo("leave") == 0) {
                            System.out.println("opuszczasz pokoj");
                            Platform.runLater(()->{
                                manager.displayScene("show_tables");
                            });
                            break;
                        }else if(message.compareTo("disconnect") == 0) {
                            System.out.println("server zamyka polaczenie");
                            Platform.exit();
                            break;
                        }else
                            this.receiveMessage(message);

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void leaveRoom(ActionEvent event) throws IOException {
        System.out.println("Leave clicked");
        manager.getNetworkHandler().sendMessage("leave");
//        manager.displayScene("show_tables");
    }

}
