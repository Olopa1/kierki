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

public class TableScene implements ScenesHanlder {
    private final String sceneName = "Gra";
    private Scene tableScene;
    private GridPane gridPane;
    private GameController controller;
    private Table table;

    /**
     * Initializes scene.
     *
     * @param sceneManager Manager object that will contain that scene.
     */
    public TableScene(SceneManager sceneManager) {
        this.initScene(sceneManager);
    }

    /**
     * Initializes scene.
     *
     * @param sceneManager Manager object that will contain that scene.
     */
    public void initScene(SceneManager sceneManager) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../game-view.fxml"));
            Parent root = loader.load();

            controller = loader.getController();
            controller.setTable(table);
            controller.setNetworkHandler(sceneManager.getNetworkHandler());
            controller.hideElements();
            controller.setManager(sceneManager);

            this.tableScene = new Scene(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getSceneName() {
        return this.sceneName;
    }

    public Scene getScene() {
        return this.tableScene;
    }

}
